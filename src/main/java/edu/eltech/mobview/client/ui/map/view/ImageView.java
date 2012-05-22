package edu.eltech.mobview.client.ui.map.view;

import org.gwtopenmaps.openlayers.client.Icon;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Marker;
import org.gwtopenmaps.openlayers.client.layer.Markers;
import org.gwtopenmaps.openlayers.client.util.JSObject;

import edu.eltech.mobview.client.Icons;
import edu.eltech.mobview.client.Icons.Image;
import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.view.BaseCollectionView;
import edu.eltech.mobview.client.util.BiMap;

public class ImageView extends BaseCollectionView<PointOnMap> {
	
	private final BiMap<JSObject, Model<PointOnMap>> jsoModelBimap;	
	private final Markers markersLayer;
	private final Icon icon;
	
	public ImageView(BiMap<JSObject, Model<PointOnMap>> jsoModelBimap,
			Markers makrerLayer, Icon icon) {
		this.jsoModelBimap = jsoModelBimap;
		this.markersLayer = makrerLayer;
		this.icon = icon;
	}

	@Override
	public void onAdd(Model<PointOnMap> model) {
		PointOnMap pointOnMap = model.getProperty();
		LonLat pos = new LonLat(pointOnMap.getLon(), pointOnMap.getLat());
		
		Marker marker = new Marker(pos, icon);
		jsoModelBimap.put(marker.getJSObject(), model);
		markersLayer.addMarker(marker);
	}

	@Override
	public void onRemove(Model<PointOnMap> model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(Model<PointOnMap> model) {
		
		Marker marker = Marker.narrowToMarker(jsoModelBimap.findFirst(model));
		markersLayer.removeMarker(marker);

		jsoModelBimap.removePair(jsoModelBimap.findFirst(model), model);
		
		PointOnMap pointOnMap = model.getProperty();
		LonLat pos = new LonLat(pointOnMap.getLon(), pointOnMap.getLat());
		
		marker = new Marker(pos, icon);
		jsoModelBimap.put(marker.getJSObject(), model);
		markersLayer.addMarker(marker);		
	}
}
