package edu.eltech.mobview.client.ui.map;

import java.util.HashSet;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.PanZoomBar;
import org.gwtopenmaps.openlayers.client.control.SelectFeature;
import org.gwtopenmaps.openlayers.client.control.SelectFeatureOptions;
import org.gwtopenmaps.openlayers.client.event.FeatureHighlightedListener;
import org.gwtopenmaps.openlayers.client.event.FeatureUnhighlightedListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;

import com.google.gwt.core.client.GWT;

import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.mvc.model.CollectionModel;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.view.BaseCollectionView;
import edu.eltech.mobview.client.util.BiMap;

/**
 * Карта с объектами
 */
public class BaseMapWidget extends MapWidget {
	private final BaseCollectionView<PointOnMap> collectionView;
	
	private final CollectionModel<PointOnMap> selectionModel = 
			new CollectionModel<PointOnMap>(new HashSet<Model<PointOnMap>>());
	
	private final BiMap<String, Model<PointOnMap>> featureModelBimap = 
			new BiMap<String, Model<PointOnMap>>();
	
	private final Vector vectorLayer;
	
	public BaseMapWidget(String width, String height, CollectionModel<PointOnMap> model) {
		super(width, height, new MapOptions());
		
		OSM openStreetMap = OSM.CycleMap("Base map");
		openStreetMap.setIsBaseLayer(true);
		
		vectorLayer = new Vector("vector");
		
		getMap().addLayer(openStreetMap);
		getMap().addLayer(vectorLayer);
		
		getMap().addControl(new PanZoomBar());
		
		collectionView = new BaseCollectionView<PointOnMap>() {

			@Override
			public void onAdd(Model<PointOnMap> model) {
				PointOnMap pointOnMap = model.getProperty();
				
				Style style = new Style();
				style.setPointRadius(pointOnMap.getRadius());
				style.setFillColor(pointOnMap.getColor());
				Point point = new Point(pointOnMap.getLon(), pointOnMap.getLat());
				VectorFeature vf = new VectorFeature(point);
				vf.setStyle(style);
				
				featureModelBimap.put(vf.getFeatureId(), model);
				vectorLayer.addFeature(vf);
			}

			@Override
			public void onRemove(Model<PointOnMap> model) {
				
			}

			@Override
			public void onUpdate(Model<PointOnMap> model) {
				GWT.log("onUpdate");
				String vfId = featureModelBimap.findFirst(model);
				VectorFeature vf = vectorLayer.getFeatureById(vfId);
				PointOnMap pointOnMap = model.getProperty();
				Style style = vf.getStyle();
				style.setPointRadius(pointOnMap.getRadius());
				style.setFillColor(pointOnMap.getColor());
				vf.redrawParent();
			}
		};
		addSelectFeature();
		collectionView.setModel(model);
		
		// -- для отладки		
		LonLat spb = new LonLat(30.301666, 59.93816);
		spb.transform("EPSG:4326", "EPSG:900913");
		
		getMap().setCenter(spb, 15);
	}
	
	private void addSelectFeature() {
		SelectFeatureOptions selectFeatureOptions = new SelectFeatureOptions();
		
		final SelectFeature selectFeature = new SelectFeature(vectorLayer, selectFeatureOptions);
			
		selectFeature.addFeatureHighlightedListener(new FeatureHighlightedListener() {
			
			@Override
			public void onFeatureHighlighted(VectorFeature vectorFeature) {
				Model<PointOnMap> model = 
						featureModelBimap.findSecond(vectorFeature.getFeatureId());
				selectionModel.add(model);
				Style style = vectorFeature.getStyle();
				style.setStrokeWidth(4);
				
				vectorLayer.drawFeature(vectorFeature, style);
			}
		});
		
		selectFeature.addFeatureUnhighlightedListener(new FeatureUnhighlightedListener() {
			
			@Override
			public void onFeatureUnhighlighted(VectorFeature vectorFeature) {
				Model<PointOnMap> model = featureModelBimap.findSecond(vectorFeature.getFeatureId());
				selectionModel.remove(model);
				vectorFeature.getStyle().setStrokeWidth(1);
				vectorLayer.drawFeature(vectorFeature, vectorFeature.getStyle());
			}
		});
		
		getMap().addControl(selectFeature);
		selectFeature.activate();
	}
	
	public CollectionModel<PointOnMap> getSelectionModel() {
		return selectionModel;
	}
}
