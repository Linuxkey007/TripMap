package com.tripmap.timershaftdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tripmap.entity.TripMaphoto;
import com.tripmap.mainfragment.R;
import com.tripmap.photo.testpic.Bimp;
import com.tripmap.timershaftdemo.po.News;
import com.tripmap.timershaftdemo.view.XListView;
import com.tripmap.timershaftdemo.view.XListView.IXListViewListener;
import com.tripmap.util.TripMapDAO;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TimerShaftMainActivity extends Activity implements
		IXListViewListener {
	private XListView sListView;
	private List<News> sNewsList;
	private Handler sHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private NewsAdapter sNewsAdapter;
	private Context sContext;
	private Map<String, List<TripMaphoto>> map;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_shaft_main);
		sContext = TimerShaftMainActivity.this;
		sNewsList = new ArrayList<News>();
		geneItems();
		sListView = (XListView) findViewById(R.id.xListView);
		sListView.setPullLoadEnable(true);
		sNewsAdapter = new NewsAdapter();
		sListView.setAdapter(sNewsAdapter);
		sListView.setXListViewListener(this);
		sHandler = new Handler();
	}

	private void geneItems() {
		map = new TripMapDAO(getApplicationContext()).BytimeDesc();
		Set<Map.Entry<String, List<TripMaphoto>>> set = map.entrySet();
		Iterator<Map.Entry<String, List<TripMaphoto>>> it = set.iterator();
		while (it.hasNext()) {
			Log.i("我的需要的时间轴的值", "aaaaaaaaaaaaaaaaa");
			Map.Entry<String, List<TripMaphoto>> entry = it.next();
			List<TripMaphoto> value = entry.getValue();
			++start;
			for (TripMaphoto tm : value) {
				Log.i("我的需要的时间轴的值", tm.getTime() + "和" + tm.getPath());
				News news = new News();
				news.setTime(tm.getTime());
				news.setIcon(R.drawable.img_icon);
				String path = tm.getPath();
				Bitmap imgbit = null;
				try {
					imgbit = Bimp.revitionImageSize(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
				news.setPic(imgbit);
				sNewsList.add(news);
			}
		}
	}

	private void onLoad() {
		sListView.stopRefresh();
		sListView.stopLoadMore();
		Date date = new Date();
		String time = date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds();
		sListView.setRefreshTime(time);
	}

	@Override
	public void onRefresh() {
		sHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = refreshCnt;
				// items.clear();
				// geneItems();
				sNewsAdapter = new NewsAdapter();
				sListView.setAdapter(sNewsAdapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		sHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// geneItems();
				sNewsAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	private class NewsAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public NewsAdapter() {
			mInflater = LayoutInflater.from(sContext);
		}

		@Override
		public int getCount() {
			return sNewsList.size();
		}

		@Override
		public Object getItem(int position) {
			return sNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder h = null;
			if (convertView == null) {
				h = new Holder();
				convertView = mInflater.inflate(R.layout.list_item, null);
				h.tv = (TextView) convertView.findViewById(R.id.tv_time);
				h.iv = (ImageView) convertView.findViewById(R.id.iv_icon);
				h.content = (ImageView) convertView
						.findViewById(R.id.tv_content);
				convertView.setTag(h);
			} else {
				h = (Holder) convertView.getTag();
			}
			News news = sNewsList.get(position);
			String time = news.getTime().substring(0, 10);
			int icon = news.getIcon();
			Bitmap img = news.getPic();
			h.tv.setText(time);
			h.iv.setBackgroundResource(icon);
			h.content.setImageBitmap(img);
			return convertView;
		}

		private class Holder {
			public TextView tv;
			public ImageView iv;
			public ImageView content;
		}
	}
}