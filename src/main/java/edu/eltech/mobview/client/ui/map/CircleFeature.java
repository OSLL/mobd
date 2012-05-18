package edu.eltech.mobview.client.ui.map;

import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.feature.VectorFeatureImpl;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.util.JSObject;


public class CircleFeature extends VectorFeature {
	private static JSObject initElement(ColorPoint cp) {
		Style style = new Style();
		style.setFillColor(cp.getColor());
		style.setStrokeColor(cp.getStrokeColor());
		style.setPointRadius(cp.getRadius());
		
		Point point = new Point(cp.getPos().lon(), cp.getPos().lat());

		return VectorFeatureImpl.create(point.getJSObject(), style.getJSObject());
	}
	
	public CircleFeature(ColorPoint cp) {
		super(initElement(cp));
	}
}
