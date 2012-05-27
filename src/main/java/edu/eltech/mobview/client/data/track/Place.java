package edu.eltech.mobview.client.data.track;

import java.io.Serializable;

public class Place implements Serializable {
	private int placeid;
	private double lon;
	private double lat;
	
	public int getPlaceid() {
		return placeid;
	}

	public void setPlaceid(int placeid) {
		this.placeid = placeid;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public Place() {
		// TODO Auto-generated constructor stub
	}
	
	public Place(int placeid, double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
		this.placeid = placeid;
	}

}
