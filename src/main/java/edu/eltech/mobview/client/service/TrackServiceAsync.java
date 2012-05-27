package edu.eltech.mobview.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.eltech.mobview.client.data.track.Place;
import edu.eltech.mobview.client.data.track.TrackPointV2;

public interface TrackServiceAsync {

	void getTrackPoints(int userid, AsyncCallback<List<TrackPointV2>> callback);

	void getPlaces(int userid, AsyncCallback<List<Place>> callback);

}
