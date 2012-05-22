package edu.eltech.mobview.client.mvc.view;

import java.util.HashMap;
import java.util.Map;

import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.data.PointOnMap.PointType;
import edu.eltech.mobview.client.mvc.model.Model;

public class CollectionViewDispatcher extends BaseCollectionView<PointOnMap> {
	
	private Map<PointType, BaseCollectionView<PointOnMap>> views = 
			new HashMap<PointType, BaseCollectionView<PointOnMap>>(); 

	@Override
	public void onAdd(Model<PointOnMap> property) {
		for (PointType type : views.keySet()) {
			if (type == property.getProperty().getType()) {
				views.get(type).onAdd(property);
			}
		}
	}

	@Override
	public void onRemove(Model<PointOnMap> property) {
		for (PointType type : views.keySet()) {
			if (type == property.getProperty().getType()) {
				views.get(type).onRemove(property);
			}
		}	
	}

	@Override
	public void onUpdate(Model<PointOnMap> property) {
		for (PointType type : views.keySet()) {
			if (type == property.getProperty().getType()) {
				views.get(type).onUpdate(property);
			}
		}	
	}
	
	public void registerView(PointType type, BaseCollectionView<PointOnMap> view) {
		views.put(type, view);
	}
}
