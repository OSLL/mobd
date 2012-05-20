package edu.eltech.mobview.client.data;

public class ColorCircle extends PointOnMap {

	private double radius;
	
	public ColorCircle(double lon, double lat, String description,
			PointType type, double radius) {
		super(lon, lat, description, type);
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
