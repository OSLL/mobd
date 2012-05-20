package edu.eltech.mobview.client.ui.map;

import java.util.HashSet;

import org.gwtopenmaps.openlayers.client.Icon;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Marker;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.PanZoomBar;
import org.gwtopenmaps.openlayers.client.control.SelectFeature;
import org.gwtopenmaps.openlayers.client.control.SelectFeatureOptions;
import org.gwtopenmaps.openlayers.client.event.EventHandler;
import org.gwtopenmaps.openlayers.client.event.EventObject;
import org.gwtopenmaps.openlayers.client.event.FeatureHighlightedListener;
import org.gwtopenmaps.openlayers.client.event.FeatureUnhighlightedListener;
import org.gwtopenmaps.openlayers.client.event.MarkerBrowserEventListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.Markers;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;

import com.google.gwt.core.client.GWT;

import edu.eltech.mobview.client.Icons;
import edu.eltech.mobview.client.Icons.Image;
import edu.eltech.mobview.client.data.ColorCircle;
import edu.eltech.mobview.client.data.Mobile;
import edu.eltech.mobview.client.data.Place;
import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.mvc.model.CollectionModel;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.view.BaseCollectionView;
import edu.eltech.mobview.client.util.BiMap;

/**
 * Карта с объектами
 */
public class BaseMapWidget extends MapWidget implements FeatureHighlightedListener, FeatureUnhighlightedListener {
	private final BaseCollectionView<PointOnMap> collectionView;
	
	private final CollectionModel<PointOnMap> selectionModel = 
			new CollectionModel<PointOnMap>(new HashSet<Model<PointOnMap>>());
	
	private final BiMap<String, Model<PointOnMap>> featureModelBimap = 
			new BiMap<String, Model<PointOnMap>>();
	
	private final Vector vectorLayer;
	private final Markers markersLayer;
	
	public BaseMapWidget(String width, String height, CollectionModel<PointOnMap> model) {
		super(width, height, new MapOptions());
		
		OSM openStreetMap = OSM.CycleMap("Base map");
		openStreetMap.setIsBaseLayer(true);
		
		vectorLayer  = new Vector("vector");
		markersLayer = new Markers("markers");  
		
		getMap().addLayer(openStreetMap);
		getMap().addLayer(vectorLayer);
		getMap().addLayer(markersLayer);
		
		getMap().addControl(new PanZoomBar());
		
		collectionView = new BaseCollectionView<PointOnMap>() {

			@Override
			public void onAdd(Model<PointOnMap> model) {				
				PointOnMap pointOnMap = model.getProperty();
				LonLat pos = new LonLat(pointOnMap.getLon(), pointOnMap.getLat());
				
				if (pointOnMap instanceof Mobile) {
					Icon icon = Icons.getIcon(Image.MOBILE);
					final Marker marker = new Marker(pos, icon);
					
					marker.getEvents().register("mousedown event", marker, new EventHandler() {
						
						@Override
						public void onHandle(EventObject eventObject) {
							GWT.log("marker click!");
							marker.getEvents().stop(eventObject);
						}
					});
					
					marker.addBrowserEventListener("mousedown browserevent", new MarkerBrowserEventListener() {
						
						@Override
						public void onBrowserEvent(MarkerBrowserEvent markerBrowserEvent) {
							GWT.log("marker click");
						}
					});
					
					markersLayer.addMarker(marker);
				} else if (pointOnMap instanceof Place) {
					Icon icon = Icons.getIcon(Image.HOUSE);
					markersLayer.addMarker(new Marker(pos, icon));				
				} else if (pointOnMap instanceof ColorCircle) {
					Style style = new Style();
					Point point = new Point(pos.lon(), pos.lat());
					VectorFeature vf = new VectorFeature(point);
					vf.setStyle(style);
					featureModelBimap.put(vf.getFeatureId(), model);
					vectorLayer.addFeature(vf);
				} else {
					 throw new Error("unknown point type");
				}
			}

			@Override
			public void onRemove(Model<PointOnMap> model) {
				// TODO
			}

			@Override
			public void onUpdate(Model<PointOnMap> model) {
				PointOnMap pointOnMap = model.getProperty();
				if (pointOnMap instanceof ColorCircle) {
					String vfId = featureModelBimap.findFirst(model);
					VectorFeature vf = vectorLayer.getFeatureById(vfId);
					Style style = vf.getStyle();
					vf.redrawParent();
				} else {
					GWT.log("onUpdate: not implemented yet");
				}
			}
		};
		addSelectFeature();
		collectionView.setModel(model);
		
		// -- для отладки		
		LonLat spb = new LonLat(30.301666, 59.93816);
		spb.transform("EPSG:4326", "EPSG:900913");
		
		getMap().setCenter(spb, 13);
	}
	
	private void addSelectFeature() {
		SelectFeatureOptions selectFeatureOptions = new SelectFeatureOptions();
		final SelectFeature selectFeature = new SelectFeature(vectorLayer, selectFeatureOptions);
		
		selectFeature.addFeatureHighlightedListener(this);
		selectFeature.addFeatureUnhighlightedListener(this);
		
		getMap().addControl(selectFeature);
		selectFeature.activate();
	}
	
	public CollectionModel<PointOnMap> getSelectionModel() {
		return selectionModel;
	}

	@Override
	public void onFeatureHighlighted(VectorFeature vectorFeature) {
		Model<PointOnMap> model = featureModelBimap.findSecond(vectorFeature
				.getFeatureId());
		selectionModel.add(model);
		Style style = vectorFeature.getStyle();
		style.setStrokeWidth(4);

		vectorLayer.drawFeature(vectorFeature, style);
	}

	@Override
	public void onFeatureUnhighlighted(VectorFeature vectorFeature) {
		Model<PointOnMap> model = featureModelBimap.findSecond(vectorFeature
				.getFeatureId());
		selectionModel.remove(model);
		vectorFeature.getStyle().setStrokeWidth(1);
		vectorLayer.drawFeature(vectorFeature, vectorFeature.getStyle());
	}
}
