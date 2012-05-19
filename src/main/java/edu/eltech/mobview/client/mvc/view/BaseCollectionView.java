package edu.eltech.mobview.client.mvc.view;

import edu.eltech.mobview.client.mvc.model.CollectionModel;
import edu.eltech.mobview.client.mvc.model.ICollectionModelSubscriber;
import edu.eltech.mobview.client.mvc.model.Model;

public abstract class BaseCollectionView<P> 
	implements ICollectionModelSubscriber<Model<P>> {
	
	private CollectionModel<P> collectionModel;
	
	public void setModel(CollectionModel<P> model) {
		unsubscribe();
		this.collectionModel = model;
		subscribe();
	}
	
	private void subscribe() {
		collectionModel.subscribe(this);
	}
	
	private void unsubscribe() {
		if (collectionModel != null) {
			collectionModel.unsubscribe(this);
		}
	}
}
