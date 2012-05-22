package edu.eltech.mobview.client.data;

public class House extends PointOnMap {

	public House(double lon, double lat, String description) {
		super(lon, lat, description);
		// TODO Auto-generated constructor stub
	}

	public PointType getType() {
		// TODO Auto-generated method stub
		return PointType.HOUSE;
	}
}
