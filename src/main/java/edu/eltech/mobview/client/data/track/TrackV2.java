package edu.eltech.mobview.client.data.track;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.gwtopenmaps.openlayers.client.LonLat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.eltech.mobview.client.data.LonLatDTO;
import edu.eltech.mobview.client.data.TrackDTO;
import edu.eltech.mobview.client.service.TrackService;
import edu.eltech.mobview.client.service.TrackServiceAsync;
import edu.eltech.mobview.client.util.InitCallback;

public class TrackV2 {
	private Map<Integer, LonLatDTO> places;
	
	private final List<TrackPointV2> points;

	private int userid;
	
	public TrackV2(int userid, Map<Integer, LonLatDTO> places, 
			List<TrackPointV2> points) {
		this.userid = userid;
		this.places = places;
		this.points = points;
	}
	
	public TrackV2(int userid, TrackDTO trackDTO) {
		this.places = trackDTO.getPlaces();
		this.points = trackDTO.getPoints();
		this.userid = userid;
	}

	public int getTimeBegin() {
		return points.get(0).getTime();		
	}
	
	public int getTimeEnd() {
		return points.get(points.size() - 1).getTime();
	}
	
	public LonLatDTO getPos(int time) {
		if (time <= getTimeBegin()) {
			return places.get(points.get(0).getPlaceid());
		}
		
		if (time >= getTimeEnd()) {
			return places.get(points.get(points.size() - 1).getPlaceid());
		}
		
		int i;
		for (i = 0; i != points.size() - 1; ++i) {
			if (points.get(i + 1).getTime() > time) {
				break;
			}
		}
		
		TrackPointV2 p2 = points.get(i + 1);
		TrackPointV2 p1 = points.get(i);

		int t1 = p1.getTime();
		int t2 = p2.getTime();
		
		double lon1 = places.get(p1.getPlaceid()).getLon();
		double lon2 = places.get(p2.getPlaceid()).getLon();
		
		double lat1 = places.get(p1.getPlaceid()).getLat();
		double lat2 = places.get(p2.getPlaceid()).getLat();
		
		double k = (double)(time - t1) / (t2 - t1);
		
		double lon = lon1 + (lon2 - lon1) * k;
		double lat = lat1 + (lat2 - lat1) * k;
		
		return new LonLatDTO(lon, lat);
	}
	
	public void init(final InitCallback initCb) {
		if (initCb != null) {
			initCb.setCounter(2);
		}
		
		final TrackServiceAsync trackService = GWT
				.create(TrackService.class);
		
		trackService.getPlaces(userid, new AsyncCallback<Map<Integer, LonLatDTO>>() {
			
			@Override
			public void onSuccess(Map<Integer, LonLatDTO> result) {
				places = result;
				if (initCb != null) {
					initCb.inc();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
			}
		});
		
		trackService.getTrackPoints(userid, new AsyncCallback<List<TrackPointV2>>() {
			
			@Override
			public void onSuccess(List<TrackPointV2> result) {
				points.addAll(result);
				if (initCb != null) {
					initCb.inc();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
			}
		});
	}
}
