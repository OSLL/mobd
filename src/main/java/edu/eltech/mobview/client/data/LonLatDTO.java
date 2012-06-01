package edu.eltech.mobview.client.data;

import java.io.Serializable;

public class LonLatDTO implements Serializable {
	private double lat;
	private double lon;
	
	public LonLatDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public LonLatDTO(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}

}
