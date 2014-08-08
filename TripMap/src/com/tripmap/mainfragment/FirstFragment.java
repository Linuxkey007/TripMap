package com.tripmap.mainfragment;

import com.tripmap.getlocation.GetLocationActivity;
import com.tripmap.timershaftdemo.TimerShaftMainActivity;
import com.tripmap.util.MyImageView;
import com.tripmap.view.PhotoShareManagerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 加载首页的菜单Activity
 * 
 * @author xuhao
 */
public class FirstFragment extends Fragment {
	/**
	 * 定义游图分类模块控件
	 */
	MyImageView c_map, c_photo, c_share, c_weather;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/**
		 * 加载首页的布局文件
		 */
		final View parentView = inflater.inflate(R.layout.first_tab_main,
				container, false);
		/**
		 * 地图、照片、分享、天气
		 */
		c_map = (MyImageView) parentView.findViewById(R.id.c_map);
		c_photo = (MyImageView) parentView.findViewById(R.id.c_photo);
		c_share = (MyImageView) parentView.findViewById(R.id.c_share);
		c_weather = (MyImageView) parentView.findViewById(R.id.c_weather);

		/**
		 * 地图模块的点击事件
		 */
		c_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("ditumokuai", "点击了地图模块。");
				Intent intent = new Intent(parentView.getContext(),
						GetLocationActivity.class);
				startActivity(intent);
			}
		});
		/**
		 * 时间轴的点击事件
		 */
		c_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(parentView.getContext(), "点击了时间轴模块。", 2000)
						.show();
				Intent intent = new Intent(parentView.getContext(),
						TimerShaftMainActivity.class);
				startActivity(intent);
			}

		});
		/**
		 * 照片分享的点击事件
		 */
		c_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(parentView.getContext(),
						PhotoShareManagerActivity.class);
				startActivity(intent);
			}
		});

		/**
		 * 天气模块的点击事件
		 */
		c_weather.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(parentView.getContext(), "点击了天气模块。", 2000)
						.show();
			}
		});

		return parentView;
	}
}
