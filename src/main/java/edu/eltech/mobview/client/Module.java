package edu.eltech.mobview.client;

import java.util.HashSet;

import org.gwtopenmaps.openlayers.client.LonLat;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import edu.eltech.mobview.client.data.ColorCircle;
import edu.eltech.mobview.client.data.Mobile;
import edu.eltech.mobview.client.data.Place;
import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.data.PointOnMap.PointType;
import edu.eltech.mobview.client.mvc.model.CollectionModel;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.ui.PointInfo;
import edu.eltech.mobview.client.ui.map.BaseMapWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Module implements EntryPoint {

	private final Messages messages = GWT.create(Messages.class);
	
	private final CollectionModel<PointOnMap> model = 
			new CollectionModel<PointOnMap>(new HashSet<Model<PointOnMap>>());

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
		p.addEast(pointInfoWidget, 20);
		p.add(mapWidget);
		
		RootLayoutPanel.get().add(p);
	}
	
	/**
	 * Для отладки.
	 */
	private void initModel() {
		LonLat spb  = new LonLat(30.301666, 59.93816);
		LonLat spb1 = new LonLat(30.311666, 59.93816);
		LonLat spb2 = new LonLat(30.311666, 59.94316);
		
		spb.transform("EPSG:4326", "EPSG:900913");
		spb1.transform("EPSG:4326", "EPSG:900913");
		spb2.transform("EPSG:4326", "EPSG:900913");
		
		model.add(new Model<PointOnMap>(new ColorCircle(spb.lon(), spb.lat(), "Точка 1", PointType.MOBILE, 50)));
		model.add(new Model<PointOnMap>(new Mobile(spb1.lon(), spb1.lat(), "Точка 2", PointType.HOUSE)));
		model.add(new Model<PointOnMap>(new Place(spb2.lon(), spb2.lat(), "Точка 3", PointType.HOUSE)));
	}
}