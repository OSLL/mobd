package edu.eltech.mobview.client.data;

/**
 * Точка на карте
 *
 */
public abstract class PointOnMap {
//	public enum PointType {
//		MOBILE, HOUSE
//	}
	
	public enum PointType {
		COLOR_CIRCLE, MOBILE, HOUSE
	}
	
	private final int id;
	private double lon;
	private double lat;
//	private PointType type;
	private String description;
	
	public PointOnMap(int id, double lon, double lat, String description) {//, PointType type) {
		this.setLon(lon);
		this.setLat(lat);
		this.setDescription(description);
		this.id = id;
//		this.setType(type);
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
	
	public abstract PointType getType();
//
//	public PointType getType() {
//		return type;
//	}
//
//	public void setType(PointType type) {
//		this.type = type;
//	}

	public int getId() {
		return id;
	}

}
