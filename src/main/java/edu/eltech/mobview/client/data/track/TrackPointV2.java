package edu.eltech.mobview.client.data.track;

import java.io.Serializable;

public class TrackPointV2 implements Serializable {
	private int time;
	private int placeid;
	
	public TrackPointV2() {
		time = 0;
		placeid = 0;
	}
	
	public TrackPointV2(int time, int placeid) {
		
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getPlaceid() {
		return placeid;
	}
	public void setPlaceid(int placeid) {
		this.placeid = placeid;
	}
	
	
}
