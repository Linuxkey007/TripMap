package com.tripmap.photo.testpic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.tripmap.entity.TripMaphoto;
import com.tripmap.mainfragment.R;
import com.tripmap.photo.testpic.TripMapGridAdapter.TextCallback;
import com.tripmap.util.TripMapDAO;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class TripMapPhotoActivity extends Activity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	List<TripMaphoto> dataList;
	GridView gridView;
	TripMapGridAdapter adapter;
	AlbumHelper helper;
	Button bt;
	ImageView img_return;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(TripMapPhotoActivity.this, "最多选择9张图片", 400)
						.show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tripmap_image_grid);

		helper = AlbumHelper.getHelper();

		helper.init(getApplicationContext());

		dataList = new TripMapDAO(getApplicationContext()).findAll();
		initView();
		bt = (Button) findViewById(R.id.bt);

		img_return = (ImageView) findViewById(R.id.imgbtn_return);
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				if (Bimp.act_bool) {
					Intent intent = new Intent(TripMapPhotoActivity.this,
							TripMapPublishedActivity.class);
					startActivity(intent);
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					if (Bimp.drr.size() < 9) {
						Bimp.drr.add(list.get(i));
					}
				}
				finish();
			}

		});
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new TripMapGridAdapter(TripMapPhotoActivity.this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.notifyDataSetChanged();
			}

		});

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	// 返回键的监听事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent myIntent = new Intent();
			myIntent = new Intent(TripMapPhotoActivity.this,
					MainPhotoTabActivity.class);
			startActivity(myIntent);
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}