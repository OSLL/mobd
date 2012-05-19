package edu.eltech.mobview.client.data;

/**
 * Точка на карте
 *
 */
public class PointOnMap {
	private double lon;
	private double lat;
	private double radius;
	private String color;
	private String description;
	
	public PointOnMap(double lon, double lat, double radius, String description, String color) {
		this.setLon(lon);
		this.setLat(lat);
		this.setRadius(radius);
		this.setDescription(description);
		this.setColor(color);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
