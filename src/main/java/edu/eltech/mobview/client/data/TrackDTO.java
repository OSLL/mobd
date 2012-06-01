package edu.eltech.mobview.client.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import edu.eltech.mobview.client.data.track.TrackPointV2;

public class TrackDTO implements Serializable {
	private int userid;
	private Map<Integer, LonLatDTO> places;
	private List<TrackPointV2> points;
	
	public List<TrackPointV2> getPoints() {
		return points;
	}

	public void setPoints(List<TrackPointV2> points) {
		this.points = points;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public TrackDTO() {
		// TODO Auto-generated constructor stub
	}

	public Map<Integer, LonLatDTO> getPlaces() {
		return places;
	}

	public void setPlaces(Map<Integer, LonLatDTO> places) {
		this.places = places;
	}


}
