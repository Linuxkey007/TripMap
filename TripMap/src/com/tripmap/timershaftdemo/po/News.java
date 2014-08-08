package com.tripmap.timershaftdemo.po;

import android.graphics.Bitmap;

public class News {
	private String time;
	private int icon;
	private Bitmap pic;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public Bitmap getPic() {
		return pic;
	}

	public void setPic(Bitmap pic) {
		this.pic = pic;
	}

	public News(String time, int icon, Bitmap pic) {
		super();
		this.time = time;
		this.icon = icon;
		this.pic = pic;
	}

	public News() {
		super();
	}

}
