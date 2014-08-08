package com.tripmap.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.tripmap.constant.Constant;
import com.tripmap.getlocation.BaseActivity;
import com.tripmap.mainfragment.R;

public class LocationMySelfActivity extends BaseActivity {
	private SDKReceiver mReceiver;
	private ToggleButton model_change;
	private ToggleButton roadCond;
	private float curlat;
	private float curLon;
	GeoCoder mSearch = null;
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;

	MapView mMapView;
	BaiduMap mBaiduMap;

	// UI相关
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	private InfoWindow mInfoWindow;
	Marker locationmyself;
	private View viewCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		requestLocButton = (Button) findViewById(R.id.button1);
		model_change = (ToggleButton) this.findViewById(R.id.model_change);
		roadCond = (ToggleButton) this.findViewById(R.id.road_cond);
		roadCond.setOnCheckedChangeListener(new RoadCondListener());
		model_change.setOnCheckedChangeListener(new ModelChangeListener());
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(new ResultCode());
		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("普通");
		OnClickListener btnClickListener = new OnClickListener() {
			public void onClick(View v) {
				switch (mCurrentMode) {
				case NORMAL:
					requestLocButton.setText("跟随");
					mCurrentMode = LocationMode.FOLLOWING;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfigeration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case COMPASS:
					requestLocButton.setText("普通");
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfigeration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case FOLLOWING:
					requestLocButton.setText("罗盘");
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfigeration(
									mCurrentMode, true, mCurrentMarker));
					break;
				}
			}
		};
		requestLocButton.setOnClickListener(btnClickListener);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfigeration(
				mCurrentMode, true, null));

		// 自定义的图标
		mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.location_arrows);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfigeration(
				mCurrentMode, true, null));
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setTrafficEnabled(false);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		mBaiduMap.setBuildingsEnabled(true); // 设置地图是否响应点击事件
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		mBaiduMap.setOnMarkerClickListener(new Markers());

		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
	}

	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				DisplayToast("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				DisplayToast("网络出错");
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			curlat = (float) location.getLatitude();
			curLon = (float) location.getLongitude();
			LatLng ptCenter = new LatLng(curlat, curLon);
			// 反Geo搜索
			mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(ptCenter));
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				// 添加定位图层
				OverlayOptions ooA = new MarkerOptions().position(ll)
						.icon(mCurrentMarker).zIndex(MODE_MULTI_PROCESS);
				locationmyself = (Marker) mBaiduMap.addOverlay(ooA);
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	public class ModelChangeListener implements OnCheckedChangeListener {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			} else {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			}
		}

	}

	// 是否开启实时路况
	class RoadCondListener implements OnCheckedChangeListener {

		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				mBaiduMap.setTrafficEnabled(true);
				DisplayToast("实时路况已开启");
			} else {
				mBaiduMap.setTrafficEnabled(false);
				DisplayToast("实时路况已关闭");
			}
		}

	}

	class ResultCode implements OnGetGeoCoderResultListener {
		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			new Constant().address = result.getAddressDetail().city;
			new Constant().weizhi = result.getAddress();
		}

	}

	class Markers implements OnMarkerClickListener {
		@Override
		public boolean onMarkerClick(final Marker marker) {
			Button button = new Button(getApplicationContext());
			button.setBackgroundResource(R.drawable.location_tips);
			final LatLng ll = marker.getPosition();
			Point p = mBaiduMap.getProjection().toScreenLocation(ll);
			p.y -= 47;
			LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
			OnInfoWindowClickListener listener = null;
			if (marker == locationmyself) {
				button.setText("[我的位置]\n" + new Constant().weizhi);

				listener = new OnInfoWindowClickListener() {
					public void onInfoWindowClick() {
						LatLng llNew = new LatLng(ll.latitude + 0.005,
								ll.longitude + 0.005);
						marker.setPosition(llNew);
						mBaiduMap.hideInfoWindow();
					}
				};
			}
			mInfoWindow = new InfoWindow(button, llInfo, listener);
			mBaiduMap.showInfoWindow(mInfoWindow);
			return true;
		}

	}

}
