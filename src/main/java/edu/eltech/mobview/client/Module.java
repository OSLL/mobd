package edu.eltech.mobview.client;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.gwtopenmaps.openlayers.client.LonLat;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

import edu.eltech.mobview.client.data.ColorCircle;
import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.data.TrackDTO;
import edu.eltech.mobview.client.data.track.ModelUpdaterV2;
import edu.eltech.mobview.client.data.track.TrackPointV2;
import edu.eltech.mobview.client.data.track.TrackV2;
import edu.eltech.mobview.client.mvc.model.CollectionModel;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.service.TrackService;
import edu.eltech.mobview.client.service.TrackServiceAsync;
import edu.eltech.mobview.client.ui.ClockWidget;
import edu.eltech.mobview.client.ui.ClockWidget.ClockListener;
import edu.eltech.mobview.client.ui.PointInfo;
import edu.eltech.mobview.client.ui.map.BaseMapWidget;
import edu.eltech.mobview.client.util.InitCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Module implements EntryPoint {

	private final Messages messages = GWT.create(Messages.class);
	
	private final TrackServiceAsync trackService = GWT
			.create(TrackService.class);	
	
	private final CollectionModel<PointOnMap> model = 
			new CollectionModel<PointOnMap>(new HashSet<Model<PointOnMap>>());
	
	private final CollectionModel<TrackV2> trackModel =
			new CollectionModel<TrackV2>(new HashSet<Model<TrackV2>>());
	
	private final LonLat spb = new LonLat(30.301666, 59.93816);
	
	private final ModelUpdaterV2 updater2 = new ModelUpdaterV2();
	
	private ClockWidget cw;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
//		initModel();
		cw = new ClockWidget(new ClockListener() {
			
			@Override
			public void onTick(int ts) {
				updater2.setTime(ts);
			}
		});
		initTrackModel();
		
		final BaseMapWidget mapWidget = new BaseMapWidget("100%", "100%", model);		
		
		DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
		PointInfo pointInfoWidget = new PointInfo();
		
		pointInfoWidget.attachSelectionModel(mapWidget.getSelectionModel());
		
		p.addNorth(new HTML("<h1 class='header'>Mobview</h1>"), 5);
		AbsolutePanel ap = new AbsolutePanel();
		ap.add(mapWidget);		
		ap.add(cw, -1, -1);
		RootLayoutPanel.get().add(p);
		p.add(ap);
		
	}
	
	private void initTrackModel() {
		trackService.getAllTracks(new AsyncCallback<Map<Integer,TrackDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Failure!");
			}

			@Override
			public void onSuccess(Map<Integer, TrackDTO> result) {
				for (int userid : result.keySet()) {
					TrackV2 track = new TrackV2(userid, result.get(userid));
					trackModel.add(new Model<TrackV2>(track));
				
					final Model<PointOnMap> circle = 
							new Model<PointOnMap>(new ColorCircle(userid, spb.lon(), spb.lat(), "Point", 50));
				
					model.add(circle);
					updater2.put(circle, track);
					
					cw.setTime(track.getTimeBegin());
					cw.setStartTime(track.getTimeBegin());
					cw.setStopTime(track.getTimeEnd());
				}				
				
				cw.start();
			}
		});
	}
	
	/**
	 * Для отладки.
	 */
//	private void initModel() {
//		final Model<PointOnMap> circle = 
//				new Model<PointOnMap>(new ColorCircle(spb.lon(), spb.lat(), "Point", 50));
//		
//		model.add(circle);
		

//		final TrackV2 track = new TrackV2(1);
//		updater2.put(circle, track);
		

		
//		track.init(new InitCallback() {
//			
//			@Override
//			public void onInit() {
//				cw.setTime(track.getTimeBegin());
//				cw.setStartTime(track.getTimeBegin());
//				cw.setStopTime(track.getTimeEnd());
//				cw.start();
//			}
//		});
//	}
}