package edu.eltech.mobview.client.data;

public class Mobile extends PointOnMap {
	private String number;

	public Mobile(int id, double lon, double lat, String description) {
		super(id, lon, lat, description);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public PointType getType() {
		return PointType.MOBILE;
	}
}
