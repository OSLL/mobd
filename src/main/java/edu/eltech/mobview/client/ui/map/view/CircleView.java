package edu.eltech.mobview.client.ui.map.view;

import java.util.Map;
import java.util.TreeMap;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.Control;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.util.JSObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;

import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.view.BaseCollectionView;
import edu.eltech.mobview.client.util.BiMap;

public class CircleView extends BaseCollectionView<PointOnMap> {
	
	private final BiMap<JSObject, Model<PointOnMap>> jsoModelBimap;	
	private final Vector vectorLayer;
	private final Map<Integer, Point> points = new TreeMap<Integer, Point>();
	
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
		style.setFillColor(getColor());
		style.setFillOpacity(1);
		Point point = new Point(pos.lon(), pos.lat());
		points.put(pointOnMap.getId(), point);
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
		PointOnMap pointOnMap = model.getProperty();
		VectorFeature vf = 
				VectorFeature.narrowToVectorFeature(jsoModelBimap.findFirst(model));
		Point point = Point.narrowToPoint(vf.getGeometry().getJSObject());
		point.setXY(pointOnMap.getLon(), pointOnMap.getLat());
		point.calculateBounds();
//		vectorLayer.redraw();
//		VectorFeature vf = 
//				VectorFeature.narrowToVectorFeature(jsoModelBimap.findFirst(model));
//		
//		vectorLayer.removeFeature(vf);
//		jsoModelBimap.removePair(jsoModelBimap.findFirst(model), model);
//		
//		
//		Style style = new Style();
//		style.setFillColor("yellow");
//		style.setStrokeColor("black");
//		style.setFillOpacity(1);
//		Point point = new Point(pointOnMap.getLon(), pointOnMap.getLat());
//		vf = new VectorFeature(point);
//		vf.setStyle(style);
//
//		jsoModelBimap.put(vf.getJSObject(), model);
//		vectorLayer.addFeature(vf);		
	}
	
//	@Override
//	public void onUpdate(Model<PointOnMap> model) {
//		PointOnMap pointOnMap = model.getProperty();
//
//		VectorFeature vf = 
//				VectorFeature.narrowToVectorFeature(jsoModelBimap.findFirst(model));
//		
//		Point p = Point.narrowToPoint(vf.getGeometry().getJSObject());
////		vf.getGeometry().
//		p.r
//		
//		vectorLayer.removeFeature(vf);
//				
//	}
	
//	public static String hexadecimal(String input, String charsetName) throws UnsupportedEncodingException {
//	    if (input == null) throw new NullPointerException();
//	    return asHex(input.getBytes(charsetName));
//	}
//
//	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
//
//	public static String asHex(byte[] buf) {
//		char[] chars = new char[2 * buf.length];
//		for (int i = 0; i < buf.length; ++i) {
//			chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
//			chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
//		}
//		return new String(chars);
//	}
//	
	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	
	
	private String getColor() {
		StringBuilder sb = new StringBuilder("#");
		
		for (int i = 0; i != 6; ++i) {
			sb.append(HEX_CHARS[Random.nextInt(16)]);
		}
		
		return sb.toString();
	}
}
