package edu.eltech.mobview.client.data.track;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;

import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.mvc.model.Model;

public class ModelUpdater {
	private static int TICK_TIME = 50;
    private Timer timer;
      
	private ArrayList<Model<PointOnMap>> models = 
			new ArrayList<Model<PointOnMap>>();
	
	private ArrayList<Track> tracks = 
			new ArrayList<Track>();
	
	public void put(Model<PointOnMap> model, Track track) {
		models.add(model);
		tracks.add(track);
	}
	
	public void start() {
		 timer = new Timer() {
		        public void run() {
		          tick(TICK_TIME);
		        }
		      };
		timer.scheduleRepeating(TICK_TIME);		
	}

	protected void tick(double time) {
		for (int i = 0; i != tracks.size(); ++i) {
			Track track = tracks.get(i);
			Model<PointOnMap> model = models.get(i);
			
			PointOnMap point = model.getProperty();			
			
			track.tick(time);
			point.setLat(track.getCurrent().getPos().lat());
			point.setLon(track.getCurrent().getPos().lon());
			
			model.setProperty(point);
		}
	}
}