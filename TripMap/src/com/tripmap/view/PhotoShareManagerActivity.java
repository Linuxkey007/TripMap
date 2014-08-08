package com.tripmap.view;

import com.tripmap.mainfragment.R;
import com.tripmap.photo.testpic.MainPhotoTabActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoShareManagerActivity extends Activity implements
		OnClickListener {
	private Button img_photo_wall; // 照片墙
	private Button img_map_photo; // 地图相册
	private ImageView imageView_return;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_menu);
		img_photo_wall = (Button) findViewById(R.id.map_photo);
		img_map_photo = (Button) findViewById(R.id.img_photoWall);
		imageView_return = (ImageView) findViewById(R.id.imgbtn_return);
		imageView_return.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		img_photo_wall.setOnClickListener(this);
		img_map_photo.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_photoWall:
			Toast.makeText(PhotoShareManagerActivity.this, "点击照片墙", 2000)
					.show();
			Intent intent = new Intent(PhotoShareManagerActivity.this,
					MainPhotoTabActivity.class);
			startActivity(intent);
			break;
		case R.id.map_photo:
			Toast.makeText(PhotoShareManagerActivity.this, "点击了地图相册", 2000)
					.show();
			Intent intent2 = new Intent(PhotoShareManagerActivity.this,
					MapPhotoActivity.class);
			startActivity(intent2);
			break;
		}

	}
}
