package edu.eltech.mobview.client.service;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.eltech.mobview.client.data.LonLatDTO;
import edu.eltech.mobview.client.data.TrackDTO;
import edu.eltech.mobview.client.data.track.TrackPointV2;

@RemoteServiceRelativePath("track")
public interface TrackService extends RemoteService {
	public List<TrackPointV2> getTrackPoints(int userid);
	public Map<Integer, LonLatDTO> getPlaces(int userid);
	
	public Map<Integer, List<TrackPointV2>> getAllTrackPoints();
	public Map<Integer, Map<Integer, LonLatDTO>> getAllPlaces();
	
	public Map<Integer, TrackDTO> getAllTracks();
}
