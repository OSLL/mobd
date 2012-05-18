package edu.eltech.mobview.client.ui.map;

import java.util.Collection;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.PanZoomBar;
import org.gwtopenmaps.openlayers.client.control.SelectFeature;
import org.gwtopenmaps.openlayers.client.control.SelectFeatureOptions;
import org.gwtopenmaps.openlayers.client.control.SelectFeature.ClickFeatureListener;
import org.gwtopenmaps.openlayers.client.control.SelectFeature.SelectFeatureListener;
import org.gwtopenmaps.openlayers.client.control.SelectFeature.UnselectFeatureListener;
import org.gwtopenmaps.openlayers.client.event.MapClickListener;
import org.gwtopenmaps.openlayers.client.event.MapClickListener.MapClickEvent;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;

import com.google.gwt.core.client.GWT;

import edu.eltech.mobview.client.data.PointMap;
import edu.eltech.mobview.client.mvc.controller.SetController;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.model.SetModel;
import edu.eltech.mobview.client.mvc.view.BaseView;

/**
 * Карта с объектами
 */
public class BaseMapWidget extends MapWidget {
	BaseView<SetModel<PointMap>, Collection<Model<PointMap>>> view;
	SetModel<PointMap> model;
	
	private Vector vectorLayer;
	private Vector boxLayer;
	
	public BaseMapWidget(String width, String height, SetModel<PointMap> model) {
		super(width, height, new MapOptions());		
		
		OSM openStreetMap = OSM.CycleMap("Base map");
		openStreetMap.setIsBaseLayer(true);
		
		vectorLayer = new Vector("vector");
		boxLayer = new Vector("Box layer");
		
		getMap().addLayer(openStreetMap);
		getMap().addLayer(boxLayer);
		getMap().addLayer(vectorLayer);
		
		getMap().addControl(new PanZoomBar());
		
		view = new BaseView<SetModel<PointMap>, Collection<Model<PointMap>>>() {
			
			@Override
			public void modelChanged(Model<Collection<Model<PointMap>>> model) {
				if (vectorLayer.getFeatures() != null) {
					vectorLayer.destroyFeatures();
				}
				
				for (Model<PointMap> pointModel : model.getProperty()) {
					PointMap pointMap = pointModel.getProperty();
					Point point = new Point(pointMap.getLon(), pointMap.getLat());
					VectorFeature vf = new VectorFeature(point);
					vectorLayer.addFeature(vf);
				}
			}
		};
		view.setModel(model);
		
		//--
		LonLat spb = new LonLat(30.301666, 59.93816);
		spb.transform("EPSG:4326", "EPSG:900913");
		
		getMap().setCenter(spb, 15);
		
		getMap().addMapClickListener(new MapClickListener() {
			@Override
			public void onClick(MapClickEvent mapClickEvent) {
				GWT.log("click");
			}
		});
	}
}
