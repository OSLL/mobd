package edu.eltech.mobview.client.data.track;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.gwtopenmaps.openlayers.client.LonLat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.eltech.mobview.client.service.TrackService;
import edu.eltech.mobview.client.service.TrackServiceAsync;
import edu.eltech.mobview.client.util.InitCallback;

public class TrackV2 {
	private final TreeMap<Integer, LonLat> places =
			new TreeMap<Integer, LonLat>();
	
	private ArrayList<TrackPointV2> points =
			new ArrayList<TrackPointV2>();
	
	public TrackV2(int userid, final InitCallback initCb) {
		initCb.setCounter(2);
		
		final TrackServiceAsync trackService = GWT
				.create(TrackService.class);
		
		trackService.getPlaces(userid, new AsyncCallback<List<Place>>() {
			
			@Override
			public void onSuccess(List<Place> result) {
				for (Place p : result) {
					places.put(p.getPlaceid(), 
						new LonLat(p.getLon(), p.getLat()));					
				}
				
				initCb.inc();
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
				
				initCb.inc();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
			}
		});
	}
	
	public int getTimeBegin() {
		return points.get(0).getTime();		
	}
	
	public int getTimeEnd() {
		return points.get(points.size() - 1).getTime();
	}
	
	public LonLat getPos(int time) {
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
		
		double vlon = (t2 - t1) / (places.get(p2.getPlaceid()).lon() - places.get(p1.getPlaceid()).lon());
		double vlat = (t2 - t1) / (places.get(p2.getPlaceid()).lat() - places.get(p1.getPlaceid()).lat());
		
		return new LonLat(vlon * time, vlat * time);
	}
}
