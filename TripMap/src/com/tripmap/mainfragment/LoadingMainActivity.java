package com.tripmap.mainfragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 百度游图的引导加载页面
 * @author xuhao
 */
public class LoadingMainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_main);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent intent = new Intent(LoadingMainActivity.this,
							MainFragmentTabPager.class);
					startActivity(intent);
				}
			}
		}).start();
	}

}
