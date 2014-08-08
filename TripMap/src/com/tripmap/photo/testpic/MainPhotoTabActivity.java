package com.tripmap.photo.testpic;

import com.tripmap.mainfragment.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class MainPhotoTabActivity extends TabActivity {

	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tabHost = getTabHost();
		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator("本机照片",
						getResources().getDrawable(R.drawable.modilphone))
				.setContent(
						new Intent(MainPhotoTabActivity.this,
								TestPicActivity.class)));
		tabHost.addTab(tabHost
				.newTabSpec("tab2")
				.setIndicator("游图照片",
						getResources().getDrawable(R.drawable.youtuphone))
				.setContent(
						new Intent(MainPhotoTabActivity.this,
								TripMapPhotoActivity.class)));

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tab) {
				Toast.makeText(MainPhotoTabActivity.this, tab, 2000).show();

			}
		});

	}
}
