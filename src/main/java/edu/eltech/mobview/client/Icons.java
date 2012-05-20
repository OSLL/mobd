package edu.eltech.mobview.client;

import java.util.Map;
import java.util.TreeMap;

import org.gwtopenmaps.openlayers.client.Icon;
import org.gwtopenmaps.openlayers.client.Size;

import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.data.PointOnMap.PointType;

public class Icons {
	private final static Size SIZE_16 = new Size(16, 16);
	private final static Map<Image, Icon> icons = 
		new TreeMap<Image, Icon>();
	
	public enum Image {
		HOUSE, MOBILE
	}
	
	static {
		icons.put(Image.HOUSE, new Icon("icons/house.png", SIZE_16));
		icons.put(Image.MOBILE, new Icon("icons/mobile_phone.png", SIZE_16));
	}
	
	public static Icon getIcon(Image img) {
		return icons.get(img);
	}
}
