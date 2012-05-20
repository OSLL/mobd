package edu.eltech.mobview.client.data;

public class Mobile extends PointOnMap {
	private String number;

	public Mobile(double lon, double lat, String description, PointType type) {
		super(lon, lat, description, type);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
