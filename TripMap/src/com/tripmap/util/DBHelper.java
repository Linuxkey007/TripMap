package com.tripmap.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_DBNAME = "tripphoto.db"; // 数据库名称
	public static final int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_DBNAME, null, VERSION);
	}

	/**
	 * 首次创建
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table photo(_id integer primary key autoincrement not null,path text(50) not null,latitude text,longitude text,time date)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
