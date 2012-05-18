package edu.eltech.mobview.client.ui.map;

import java.util.HashSet;
import java.util.Set;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.PanZoomBar;
import org.gwtopenmaps.openlayers.client.control.SelectFeature;
import org.gwtopenmaps.openlayers.client.control.SelectFeature.ClickFeatureListener;
import org.gwtopenmaps.openlayers.client.control.SelectFeature.SelectFeatureListener;
import org.gwtopenmaps.openlayers.client.control.SelectFeature.UnselectFeatureListener;
import org.gwtopenmaps.openlayers.client.control.SelectFeatureOptions;
import org.gwtopenmaps.openlayers.client.event.MapClickListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import com.google.gwt.core.client.GWT;

import edu.eltech.mobview.client.ui.map.CircleFeature;

public class MobviewMapWidget extends MapWidget {
	
	private Vector vectorLayer;
	private Vector boxLayer;
	
	private Set<ColorPoint> points = new HashSet<ColorPoint>();
	
	public MobviewMapWidget(String width, String height) {
		super(width, height, new MapOptions());
	
		vectorLayer = new Vector("vector");
		boxLayer = new Vector("Box layer");
		
		OSM openStreetMap = OSM.CycleMap("Base map");
		openStreetMap.setIsBaseLayer(true);
		
		Map map = getMap();
		
		addFeature();
		initPoints();
		
		map.addLayer(openStreetMap);
		map.addLayer(boxLayer);
		map.addLayer(vectorLayer);
				
		map.addControl(new PanZoomBar());
		
		LonLat spb = new LonLat(30.301666, 59.93816);
		spb.transform("EPSG:4326", "EPSG:900913");
		
		getMap().setCenter(spb, 15);
		
		map.addMapClickListener(new MapClickListener() {
			@Override
			public void onClick(MapClickEvent mapClickEvent) {
				GWT.log("click");
			}
		});
	}
	
	private void addFeature() {
		SelectFeatureOptions selectFeatureOptions = new SelectFeatureOptions();
		selectFeatureOptions.onSelect(new SelectFeatureListener() {
			public void onFeatureSelected(VectorFeature vectorFeature) {
				Style s = vectorFeature.getStyle();
				s.setPointRadius(s.getPointRadiusAsDouble() * 1.2);
				vectorFeature.redrawParent();
			}			
		});
		
		selectFeatureOptions.onUnSelect(new UnselectFeatureListener() {
			
			@Override
			public void onFeatureUnselected(VectorFeature vectorFeature) {
				Style s = vectorFeature.getStyle();
				s.setPointRadius(s.getPointRadiusAsDouble() / 1.2);
				vectorFeature.redrawParent();
			}
		});
		
		selectFeatureOptions.clickFeature(new ClickFeatureListener() {
			@Override
			public void onFeatureClicked(VectorFeature vectorFeature) {
				GWT.log("onClick()");
			}
		});
		
		selectFeatureOptions.setHover();

		final SelectFeature selectFeature = new SelectFeature(vectorLayer, selectFeatureOptions);
		getMap().addControl(selectFeature);
		selectFeature.activate();
	}

	private void initPoints() {
		LonLat spb = new LonLat(30.301666, 59.93816);
		spb.transform("EPSG:4326", "EPSG:900913");
		
		putPoint(new ColorPoint("red", "green", 10.0, spb));
		putPoint(new ColorPoint("green", "blue", 10.0, new LonLat(spb.lon() + 1000, spb.lat())));
		putPoint(new ColorPoint("pink", "grey", 10.0, new LonLat(spb.lon() + 2000, spb.lat())));
		putPoint(new ColorPoint("red", "green", 10.0, new LonLat(spb.lon() + 3000, spb.lat())));
		putPoint(new ColorPoint("brown", "black", 10.0, new LonLat(spb.lon() + 4000, spb.lat())));
	}

	private void putPoint(ColorPoint cp) {
		points.add(cp);
		vectorLayer.addFeature(new CircleFeature(cp));
	}
}