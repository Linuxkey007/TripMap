package com.tripmap.entity;

import com.baidu.mapapi.map.Marker;

public class Pic {
	private Marker marker;
	private TripMaphoto maphoto;

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public TripMaphoto getMaphoto() {
		return maphoto;
	}

	public void setMaphoto(TripMaphoto maphoto) {
		this.maphoto = maphoto;
	}

	public Pic(Marker marker, TripMaphoto maphoto) {
		super();
		this.marker = marker;
		this.maphoto = maphoto;
	}

	public Pic() {
		super();
		// TODO Auto-generated constructor stub
	}

}
