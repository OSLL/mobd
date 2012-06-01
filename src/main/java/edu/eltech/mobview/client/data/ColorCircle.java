package edu.eltech.mobview.client.data;

public class ColorCircle extends PointOnMap {

	private double radius;
	
	public ColorCircle(int id,
			double lon, double lat, String description, double radius) {
		
		super(id, lon, lat, description);
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public PointType getType() {
		return PointType.COLOR_CIRCLE;
	}

}
