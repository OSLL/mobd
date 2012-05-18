package edu.eltech.mobview.client;

import org.gwtopenmaps.openlayers.client.LonLat;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import edu.eltech.mobview.client.data.PointMap;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.model.SetModel;
import edu.eltech.mobview.client.ui.map.BaseMapWidget;
import edu.eltech.mobview.client.ui.map.MobviewMapWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Module implements EntryPoint {

	private final Messages messages = GWT.create(Messages.class);
	
	private final SetModel<PointMap> model = new SetModel<PointMap>();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		//final MobviewMapWidget mapWidget = new MobviewMapWidget("100%", "100%");
		initModel();
		final BaseMapWidget mapWidget = new BaseMapWidget("100%", "100%", model);
		DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
		
		p.addNorth(new HTML("<h1 class='header'>Mobview</h1>"), 5);
		p.add(mapWidget);
		
		RootLayoutPanel.get().add(p);
	}
	
	/**
	 * Для отладки.
	 */
	private void initModel() {
		LonLat spb = new LonLat(30.301666, 59.93816);
		LonLat spb1 = new LonLat(30.311666, 59.93816);
		
		spb.transform("EPSG:4326", "EPSG:900913");
		spb1.transform("EPSG:4326", "EPSG:900913");
		
		model.add(new Model<PointMap>(new PointMap(spb.lon(), spb.lat(), "Точка 1")));
		model.add(new Model<PointMap>(new PointMap(spb1.lon(), spb1.lat(), "Точка 2")));
	}
}