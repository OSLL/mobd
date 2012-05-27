package edu.eltech.mobview.client.data.track;

import org.gwtopenmaps.openlayers.client.LonLat;

public class TrackPoint {
	private int timeStart;
	private int timeEnd;
	private int placeId;
	
	private LonLat pos;
	
	public TrackPoint(LonLat pos) {
		this.pos = pos;
	}

	public LonLat getPos() {
		return pos;
	}

	public void setPos(LonLat pos) {
		this.pos = pos;
	}
	
	@Override
	public String toString() {
		return "(" + pos.lon() + ", " + pos.lat() + ")";
	}
}
