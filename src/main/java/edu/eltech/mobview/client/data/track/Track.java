package edu.eltech.mobview.client.data.track;

import java.util.ArrayList;

import org.gwtopenmaps.openlayers.client.LonLat;

public class Track {
	private boolean isRepeat;
	private final ArrayList<TrackPoint> trackPoints;
	private final double velocity;
	
	int linkNumber;
	double linkPos;
	Vector2D link;
	
	TrackPoint current;
	
	public Track(ArrayList<TrackPoint> trackPoints, double velocity) {
		this.trackPoints = trackPoints;
		this.velocity = velocity;
		this.isRepeat = isRepeat;
		
		assert trackPoints.size() > 1;
		
		restart();
		
	}
	
	public void restart() {
		linkPos = 0;
		linkNumber = 0;
		link = new Vector2D(trackPoints.get(linkNumber), 
				trackPoints.get(linkNumber + 1));
		current = new TrackPoint(trackPoints.get(0).getPos());
	}
	
	public void start() {
		restart();
	}
	
	public void tick(double time) {
		double s = velocity * time;
		
		double tail = link.length() - linkPos; 
		
		while (s > tail) {
			s -= tail;
			time -= tail / velocity;
			
			linkNumber++;
			linkNumber %= trackPoints.size() - 1;
			
			link = new Vector2D(trackPoints.get(linkNumber), 
					trackPoints.get(linkNumber + 1));
			linkPos = 0;
			current.setPos(trackPoints.get(linkNumber).getPos());
			
			tail = link.length() - linkPos;
		}
		
		double lon = current.getPos().lon() + s * link.getLon() / link.length();
		double lat = current.getPos().lat() + s * link.getLat() / link.length();
		linkPos += s;
		
		current.setPos(new LonLat(lon, lat));
	}
	
	private class Vector2D {
		private double lon;
		private double lat;
		
		public Vector2D(TrackPoint beg, TrackPoint end) {
			this.lon = end.getPos().lon() - beg.getPos().lon();
			this.lat = end.getPos().lat() - beg.getPos().lat();
		}
		
		public double length() {
			return Math.sqrt(lon * lon + lat * lat);
		}
		
		public double getLat() {
			return lat;
		}
		public void setLat(float dy) {
			this.lat = dy;
		}
		public double getLon() {
			return lon;
		}
		public void setLon(float dx) {
			this.lon = dx;
		}
	}

	public TrackPoint getCurrent() {
		return current;
	}
}
