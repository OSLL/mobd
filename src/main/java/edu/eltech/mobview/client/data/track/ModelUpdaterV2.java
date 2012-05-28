package edu.eltech.mobview.client.data.track;

import java.util.ArrayList;

import org.gwtopenmaps.openlayers.client.LonLat;

import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.mvc.model.Model;

public class ModelUpdaterV2 {
	private ArrayList<Model<PointOnMap>> models = 
			new ArrayList<Model<PointOnMap>>();
	
	private ArrayList<TrackV2> tracks = 
			new ArrayList<TrackV2>();
	
	public void put(Model<PointOnMap> model, TrackV2 track) {
		models.add(model);
		tracks.add(track);
	}
	
	public void setTime(int time) {
		for (int i = 0; i != tracks.size(); ++i) {
			TrackV2 track = tracks.get(i);
			Model<PointOnMap> model = models.get(i);
			
			LonLat newPos = track.getPos(time);
			PointOnMap point = model.getProperty();
			point.setLon(newPos.lon());
			point.setLat(newPos.lat());
			model.setProperty(point);			
		}
	}
}
