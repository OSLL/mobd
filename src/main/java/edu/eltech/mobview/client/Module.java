package edu.eltech.mobview.client;

import java.util.HashSet;
import org.gwtopenmaps.openlayers.client.LonLat;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.Timer;
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
import edu.eltech.mobview.client.data.track.ModelUpdaterV2;
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
	
	private ClockWidget cw;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		initModel();
		final BaseMapWidget mapWidget = new BaseMapWidget("100%", "100%", model);		
		
		DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
		PointInfo pointInfoWidget = new PointInfo();
		
		pointInfoWidget.attachSelectionModel(mapWidget.getSelectionModel());
		
		p.addNorth(new HTML("<h1 class='header'>Mobview</h1>"), 5);
		AbsolutePanel ap = new AbsolutePanel();
		
//		p.add
		

		ap.add(mapWidget);
		ap.add(cw, -1, -1);
//		RootLayoutPanel.get().add(cw);
		RootLayoutPanel.get().add(p);
		p.add(ap);
//		RootLayoutPanel.get().setWidgetLeftRight(cw
//				, 100, Unit.PX, 200, Unit.PX);
//		
//		RootLayoutPanel.get().setWidgetTopBottom(cw
//				, 80, Unit.PX, 20, Unit.PX);

	}
	
	/**
	 * Для отладки.
	 */
	private void initModel() {
		LonLat spb = new LonLat(30.301666, 59.93816);
		
		final Model<PointOnMap> circle = 
				new Model<PointOnMap>(new ColorCircle(spb.lon(), spb.lat(), "Point", 50));
		
		model.add(circle);
		
		final ModelUpdaterV2 updater2 = new ModelUpdaterV2();
		final TrackV2 track = new TrackV2(1);
		updater2.put(circle, track);
		
		cw = new ClockWidget(new ClockListener() {
			
			@Override
			public void onTick(int ts) {
				updater2.setTime(ts);
			}
		});
		
		track.init(new InitCallback() {
			
			@Override
			public void onInit() {
				cw.setTime(track.getTimeBegin());
				cw.setStartTime(track.getTimeBegin());
				cw.setStopTime(track.getTimeEnd());
				cw.start();
			}
		});
		
//		LonLat spb1 = new LonLat(30.311666, 59.93816);
//		LonLat spb2 = new LonLat(30.311666, 59.94316);
//		
//		spb.transform("EPSG:4326", "EPSG:900913");
//		spb1.transform("EPSG:4326", "EPSG:900913");
//		spb2.transform("EPSG:4326", "EPSG:900913");
//		
//		ArrayList<TrackPoint> trackPoints1 = 
//				new ArrayList<TrackPoint>();
//		trackPoints1.add(new TrackPoint(spb));
//		trackPoints1.add(new TrackPoint(spb1));
//		trackPoints1.add(new TrackPoint(spb2));
//		trackPoints1.add(new TrackPoint(spb));
//		
//		ArrayList<TrackPoint> trackPoints2 = 
//				new ArrayList<TrackPoint>();		
//		trackPoints2.add(new TrackPoint(spb1));
//		trackPoints2.add(new TrackPoint(spb2));
//		trackPoints2.add(new TrackPoint(spb));
//		trackPoints2.add(new TrackPoint(spb1));
//		
//		ArrayList<TrackPoint> trackPoints3 = 
//				new ArrayList<TrackPoint>();		
//		trackPoints3.add(new TrackPoint(spb2));
//		trackPoints3.add(new TrackPoint(spb));
//		trackPoints3.add(new TrackPoint(spb1));
//		trackPoints3.add(new TrackPoint(spb2));
//		
//		Track track1 = new Track(trackPoints1, 0.5);
//		Track track2 = new Track(trackPoints2, 0.6);
//		Track track3 = new Track(trackPoints3, 0.7);
//		
//		Model<PointOnMap> circle = 
//				new Model<PointOnMap>(new ColorCircle(spb.lon(), spb.lat(), "Point", 50));
//		
//		Model<PointOnMap> mobile =
//				new Model<PointOnMap>(new Mobile(spb1.lon(), spb1.lat(), "Mobile"));
//		
//		Model<PointOnMap> house =
//				new Model<PointOnMap>(new House(spb2.lon(), spb2.lat(), "House"));
//		
//		model.add(circle);
//		model.add(mobile);
//		model.add(house);
//		
//		ModelUpdater updater = new ModelUpdater();
//		updater.put(circle, track1);
//		updater.put(mobile, track2);
//		updater.put(house, track3);
//		updater.start();
	}
}