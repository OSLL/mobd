package edu.eltech.mobview.client.ui.map;

import java.util.HashSet;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Marker;
import org.gwtopenmaps.openlayers.client.Pixel;
import org.gwtopenmaps.openlayers.client.Size;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.PanZoomBar;
import org.gwtopenmaps.openlayers.client.control.SelectFeature;
import org.gwtopenmaps.openlayers.client.control.SelectFeatureOptions;
import org.gwtopenmaps.openlayers.client.event.FeatureHighlightedListener;
import org.gwtopenmaps.openlayers.client.event.FeatureUnhighlightedListener;
import org.gwtopenmaps.openlayers.client.event.MapClickListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.layer.Markers;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.popup.Popup;
import org.gwtopenmaps.openlayers.client.util.JSObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

import edu.eltech.mobview.client.Icons;
import edu.eltech.mobview.client.Icons.Image;
import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.data.PointOnMap.PointType;
import edu.eltech.mobview.client.mvc.model.CollectionModel;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.view.CollectionViewDispatcher;
import edu.eltech.mobview.client.ui.ClockWidget;
import edu.eltech.mobview.client.ui.map.view.CircleView;
import edu.eltech.mobview.client.ui.map.view.ImageView;
import edu.eltech.mobview.client.util.BiMap;

/**
 * Карта с объектами
 */
public class BaseMapWidget extends MapWidget implements FeatureHighlightedListener, FeatureUnhighlightedListener {
	private final CollectionModel<PointOnMap> selectionModel = 
			new CollectionModel<PointOnMap>(new HashSet<Model<PointOnMap>>());
	
	private final BiMap<JSObject, Model<PointOnMap>> jsoModelBimap = 
			new BiMap<JSObject, Model<PointOnMap>>();
	
	private final Vector vectorLayer;
	private final Markers markersLayer;
	
	CollectionViewDispatcher viewDispatcher = 
			new CollectionViewDispatcher();
	
	public BaseMapWidget(String width, String height, CollectionModel<PointOnMap> model) {
		super(width, height, new MapOptions());

		OSM openStreetMap = OSM.CycleMap("Base map");
		openStreetMap.setIsBaseLayer(true);
		
		vectorLayer  = new Vector("vector");
		markersLayer = new Markers("markers");  
		
		getMap().addLayer(openStreetMap);
		getMap().addLayer(markersLayer);
		getMap().addLayer(vectorLayer);
		
		getMap().addControl(new PanZoomBar());
		
		getMap().addMapClickListener(new MapClickListener() {
			
			@Override
			public void onClick(MapClickEvent mapClickEvent) {
				// FIXME картинки могут быть различного размера
				final int SIDE = 8;
				
				GWT.log(mapClickEvent.getPixel().toString());
				
				Pixel clickpos = mapClickEvent.getPixel();
								
				JSObject[] markers = markersLayer.getMarkers();
				
				for (JSObject m : markers) {
					Marker marker = Marker.narrowToMarker(m);
					PointOnMap pointOnMap = jsoModelBimap.findSecond(marker.getJSObject()).getProperty();
					
					Pixel point = getMap().getPixelFromLonLat(marker.getLonLat());
					
					int dx = Math.abs(clickpos.x() - point.x());
					int dy = Math.abs(clickpos.y() - point.y());

					if (dx <= SIDE && dy <= SIDE) {
						Popup popup = new Popup("", marker.getLonLat(), new Size(200, 120),
								pointOnMap.getDescription(), true);
						
						getMap().addPopup(popup);
					}
				}
			}
		});
		
		viewDispatcher.registerView(PointType.COLOR_CIRCLE, 
				new CircleView(jsoModelBimap, vectorLayer));
		
		viewDispatcher.registerView(PointType.MOBILE, 
				new ImageView(jsoModelBimap, markersLayer, Icons.getIcon(Image.MOBILE)));
		
		viewDispatcher.registerView(PointType.HOUSE, 
				new ImageView(jsoModelBimap, markersLayer, Icons.getIcon(Image.HOUSE)));
		
		addSelectFeature();
		//collectionView.setModel(model);
		viewDispatcher.setModel(model);
		
		// -- для отладки		
		LonLat spb = new LonLat(30.301666, 59.93816);
		spb.transform("EPSG:4326", "EPSG:900913");
		
		getMap().setCenter(spb, 15);
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
		Model<PointOnMap> model = jsoModelBimap.findSecond(vectorFeature.getJSObject());
		selectionModel.add(model);
		Style style = vectorFeature.getStyle();
		style.setStrokeWidth(4);

		vectorLayer.drawFeature(vectorFeature, style);
		
		Popup popup = new Popup("", vectorFeature.getCenterLonLat(), new Size(200, 120),
				model.getProperty().getDescription(), true);
		
		getMap().addPopup(popup);
	}

	@Override
	public void onFeatureUnhighlighted(VectorFeature vectorFeature) {
		Model<PointOnMap> model = jsoModelBimap.findSecond(vectorFeature.getJSObject());
		selectionModel.remove(model);
		vectorFeature.getStyle().setStrokeWidth(1);
		vectorLayer.drawFeature(vectorFeature, vectorFeature.getStyle());
	}
}
