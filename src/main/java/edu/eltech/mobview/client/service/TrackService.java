package edu.eltech.mobview.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.eltech.mobview.client.data.track.Place;
import edu.eltech.mobview.client.data.track.TrackPointV2;

@RemoteServiceRelativePath("track")
public interface TrackService extends RemoteService {
	public List<TrackPointV2> getTrackPoints(int userid);
	public List<Place> getPlaces(int userid);
}
