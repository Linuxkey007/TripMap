package com.tripmap.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tripmap.entity.TripMaphoto;
import com.tripmap.util.DBHelper;

public class TripMapDAO {

	private DBHelper dbManager;

	public TripMapDAO(Context context) {
		this.dbManager = new DBHelper(context);
	}

	// 添加图片到数据库
	public void save(TripMaphoto maphoto) {
		SQLiteDatabase db = dbManager.getWritableDatabase();
		db.execSQL(
				"insert into photo(path,latitude,longitude,time)values(?,?,?,?)",
				new Object[] { maphoto.getPath(), maphoto.getLatitude(),
						maphoto.getLongitude(), maphoto.getTime() });
		db.close();
	}

	/**
	 * 查询所有照片
	 * 
	 * @return
	 */
	public List<TripMaphoto> findAll() {
		List<TripMaphoto> mphotolist = new ArrayList<TripMaphoto>();
		SQLiteDatabase db = dbManager.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from photo", new String[] {});
		while (cursor.moveToNext()) {
			TripMaphoto mphoto = new TripMaphoto();
			mphoto.set_id(cursor.getString(cursor.getColumnIndex("_id")));
			mphoto.setPath(cursor.getString(cursor.getColumnIndex("path")));
			mphoto.setLatitude(cursor.getFloat(cursor
					.getColumnIndex("latitude")));
			mphoto.setLongitude(cursor.getFloat(cursor
					.getColumnIndex("longitude")));
			mphoto.setTime(cursor.getString(cursor.getColumnIndex("time")));
			mphotolist.add(mphoto);
		}

		db.close();
		cursor.close();
		return mphotolist;
	}

	// 根据时间来查询照片信息
	public List<TripMaphoto> findBytime(String time) {
		List<TripMaphoto> mphotolist = new ArrayList<TripMaphoto>();
		SQLiteDatabase db = dbManager.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from photo where time=?",
				new String[] { time + "" });
		while (cursor.moveToNext()) {
			TripMaphoto mphoto = new TripMaphoto();
			mphoto.set_id(cursor.getString(cursor.getColumnIndex("_id")));
			mphoto.setPath(cursor.getString(cursor.getColumnIndex("path")));
			mphoto.setLatitude(cursor.getFloat(cursor
					.getColumnIndex("latitude")));
			mphoto.setLongitude(cursor.getFloat(cursor
					.getColumnIndex("longitude")));
			mphoto.setTime(cursor.getString(cursor.getColumnIndex("time")));

			mphotolist.add(mphoto);
		}
		db.close();
		cursor.close();
		return mphotolist;
	}

	public Map<String, List<TripMaphoto>> BytimeDesc() {
		List<TripMaphoto> list;
		List<TripMaphoto> am;
		String subtime = "";
		Map<String, List<TripMaphoto>> maplst = new HashMap<String, List<TripMaphoto>>();
		SQLiteDatabase db = dbManager.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from photo order by time desc",
				new String[] {});
		while (cursor.moveToNext()) {
			list = new ArrayList<TripMaphoto>();
			TripMaphoto mphoto = new TripMaphoto();
			mphoto.set_id(cursor.getString(cursor.getColumnIndex("_id")));
			mphoto.setPath(cursor.getString(cursor.getColumnIndex("path")));
			mphoto.setLatitude(cursor.getFloat(cursor
					.getColumnIndex("latitude")));
			mphoto.setLongitude(cursor.getFloat(cursor
					.getColumnIndex("longitude")));
			String testtime = cursor.getString(cursor.getColumnIndex("time"));
			Log.i("时间轴的时间weiquc-key--", testtime+"testtime");
			mphoto.setTime(testtime);
			if (!maplst.isEmpty()) {
				Collection<List<TripMaphoto>> collection = maplst.values();
				Iterator<List<TripMaphoto>> iterator = collection.iterator();
				am = new ArrayList<TripMaphoto>();
				while (iterator.hasNext()) {
					List<TripMaphoto> value = iterator.next();
					String timeli = "";
					for (TripMaphoto tm : value) {
						timeli = tm.getTime();
						Log.i("时间轴的时间weiquc-key--", timeli);
					}
					subtime = timeli.substring(0, 10).trim();
					if (subtime.equals(testtime.substring(0, 10).trim())) {
						List<TripMaphoto> m = maplst.get(timeli);
						am = m;
						Iterator iterat = maplst.keySet().iterator();
						while (iterat.hasNext()) {
							String key = (String) iterat.next();
							if (timeli.equals(key)) {
								maplst.remove(key);
							}
						}
					}
				}
				am.add(mphoto);
				maplst.put(testtime, am);
			} else {
				list.add(mphoto);
				maplst.put(testtime, list);
			}
		}
		return maplst;
	}
}
