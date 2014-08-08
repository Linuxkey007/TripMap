package com.tripmap.mainfragment;

import com.tripmap.view.LocationMySelfActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class SecondFragment extends Fragment {

	// 加载进度条
	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 加载布局文件
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.second_tab_main, container,
				false);
		ImageButton button = (ImageButton) view
				.findViewById(R.id.imgbtn_location);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(view.getContext(),
						LocationMySelfActivity.class);
				startActivity(intent);

			}
		});
		return view;

	}

}
