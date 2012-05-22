package edu.eltech.mobview.client.ui.map.view;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.util.JSObject;

import com.google.gwt.core.client.GWT;

import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.view.BaseCollectionView;
import edu.eltech.mobview.client.util.BiMap;

public class CircleView extends BaseCollectionView<PointOnMap> {
	
	private final BiMap<JSObject, Model<PointOnMap>> jsoModelBimap;	
	private final Vector vectorLayer;
	
	public CircleView(BiMap<JSObject, Model<PointOnMap>> jsoModelBimap, 
			Vector vectorLayer) {
		this.jsoModelBimap = jsoModelBimap;
		this.vectorLayer = vectorLayer;
	}

	@Override
	public void onAdd(Model<PointOnMap> model) {
		PointOnMap pointOnMap = model.getProperty();
		LonLat pos = new LonLat(pointOnMap.getLon(), pointOnMap.getLat());
		
		Style style = new Style();
		Point point = new Point(pos.lon(), pos.lat());
		VectorFeature vf = new VectorFeature(point);
		vf.setStyle(style);
		jsoModelBimap.put(vf.getJSObject(), model);
		vectorLayer.addFeature(vf);				
	}


	@Override
	public void onRemove(Model<PointOnMap> property) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(Model<PointOnMap> model) {
		VectorFeature vf = 
				VectorFeature.narrowToVectorFeature(jsoModelBimap.findFirst(model));
		
		vectorLayer.removeFeature(vf);
		jsoModelBimap.removePair(jsoModelBimap.findFirst(model), model);
		
		PointOnMap pointOnMap = model.getProperty();
		LonLat pos = new LonLat(pointOnMap.getLon(), pointOnMap.getLat());
		
		Style style = new Style();
		Point point = new Point(pos.lon(), pos.lat());
		vf = new VectorFeature(point);
		vf.setStyle(style);
		jsoModelBimap.put(vf.getJSObject(), model);
		vectorLayer.addFeature(vf);		
	}
}
