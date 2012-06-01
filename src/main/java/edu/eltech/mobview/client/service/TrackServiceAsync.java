package edu.eltech.mobview.client.service;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.eltech.mobview.client.data.LonLatDTO;
import edu.eltech.mobview.client.data.TrackDTO;
import edu.eltech.mobview.client.data.track.TrackPointV2;

public interface TrackServiceAsync {

	void getAllPlaces(
			AsyncCallback<Map<Integer, Map<Integer, LonLatDTO>>> callback);

	void getAllTrackPoints(
			AsyncCallback<Map<Integer, List<TrackPointV2>>> callback);

	void getAllTracks(AsyncCallback<Map<Integer, TrackDTO>> callback);

	void getPlaces(int userid, AsyncCallback<Map<Integer, LonLatDTO>> callback);

	void getTrackPoints(int userid, AsyncCallback<List<TrackPointV2>> callback);

}
