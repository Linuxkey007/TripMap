package com.tripmap.util;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class TripMapApplication extends Application {
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(this);
	}
}
