package com.tripmap.view;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.tripmap.entity.TripMaphoto;
import com.tripmap.getlocation.BaseActivity;
import com.tripmap.mainfragment.R;
import com.tripmap.util.TripMapDAO;

public class MapPhotoActivity extends BaseActivity {

	private List<TripMaphoto> list;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private Marker mMarkerA;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(this.getApplicationContext());
		setContentView(R.layout.tripmapphoto);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(4.7f);
		mBaiduMap.setMapStatus(msu);
		init();
		initOverlay();
	}

	private void init() {
		list = new TripMapDAO(getApplicationContext()).findAll();

	}

	private Bitmap getbitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		return bm;
	}

	public void initOverlay() {
		for (int i = 0; i < list.size(); i++) {
			View popup1 = View.inflate(MapPhotoActivity.this, R.layout.trippop,
					null);
			ImageView image = (ImageView) popup1
					.findViewById(R.id.img_tripPhoto);
			LatLng llA = new LatLng(list.get(i).getLatitude(), list.get(i)
					.getLongitude());
			image.setImageBitmap(getbitmap(list.get(i).getPath()));
			OverlayOptions ooA = new MarkerOptions().position(llA)
					.icon(BitmapDescriptorFactory.fromView(popup1)).zIndex(9);
			mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
		}

		LatLng southwest = new LatLng(32.92235, 112.380338);
		LatLng northeast = new LatLng(32.947246, 112.414977);
		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
				.include(southwest).build();
		MapStatusUpdate u = MapStatusUpdateFactory
				.newLatLng(bounds.getCenter());
		mBaiduMap.setMapStatus(u);
	}

	public Bitmap dealScale(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scaleHeight = ((float) 80) / height;
		float scaleWidht = ((float) 90) / width;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

}