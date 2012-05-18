package edu.eltech.mobview.client.ui.map;

import org.gwtopenmaps.openlayers.client.LonLat;

public class ColorPoint {
	private LonLat pos;
	private String strokeColor;
	private String color;
	private double radius;

	public ColorPoint(String color, String strokeColor, double radius, LonLat pos) {
		this.setColor(color);
		this.setStrokeColor(strokeColor);
		this.setPos(pos);
		this.setRadius(radius);
	}

	public LonLat getPos() {
		return pos;
	}

	public void setPos(LonLat pos) {
		this.pos = pos;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
}
