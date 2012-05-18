package edu.eltech.mobview.client.data;

/**
 * Точка на карте
 *
 */
public class PointMap {
	private double lon;
	private double lat;
	private String description;
	
	public PointMap(double lon, double lat, String description) {
		this.setLon(lon);
		this.setLat(lat);
		this.setDescription(description);
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
}
