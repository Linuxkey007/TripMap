package com.tripmap.entity;

public class TripMaphoto {
	public String _id; // 定义图片的id
	public String path;
	public float latitude;
	public float longitude;
	public String time;
	public boolean ischecked = false;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public TripMaphoto(String _id, String path, float latitude,
			float longitude, String time, boolean ischecked) {
		super();
		this._id = _id;
		this.path = path;
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		this.ischecked = ischecked;
	}

	public TripMaphoto() {
		super();
	}

}
