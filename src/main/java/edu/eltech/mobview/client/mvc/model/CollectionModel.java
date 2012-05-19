package edu.eltech.mobview.client.mvc.model;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionModel<P> implements IModelSubscriber<P> {
	private Collection<Model<P>> collection;
	
	private final Collection<ICollectionModelSubscriber<Model<P>>> subscribers =
		new ArrayList<ICollectionModelSubscriber<Model<P>>>();
	
	public CollectionModel(Collection<Model<P>> collection) {
		this.collection = collection;		
	}
	
	protected void notifyAdd(Model<P> property) {
		for (ICollectionModelSubscriber<Model<P>> subscriber: subscribers) {
			subscriber.onAdd(property);			
		}
	}
	
	protected void notifyRemove(Model<P> property) {
		for (ICollectionModelSubscriber<Model<P>> subscriber: subscribers) {
			subscriber.onRemove(property);			
		}		
	}
	
	public void add(Model<P> property) {
		collection.add(property);
		notifyAdd(property);
		property.subscribe(this);
	}
	
	public void remove(Model<P> property) {
		notifyRemove(property);
		property.unsubscribe(this);
		collection.remove(property);
	}
	
	public void subscribe(ICollectionModelSubscriber<Model<P>> subscriber) {
		if (subscriber == null) {
			throw new NullPointerException("re-subscribe");
		}
		
		if (subscribers.contains(subscriber)) {
			throw new NullPointerException("re-subscribe");
		}
		subscribers.add(subscriber);
		
		for (Model<P> property : collection) {
			subscriber.onAdd(property);
		}
	}
	
	public void unsubscribe(ICollectionModelSubscriber<Model<P>> subscriber) {
		if (subscriber == null) {
			throw new NullPointerException("null subscriber");
		}
		
		if (!subscribers.contains(subscriber)) {
			throw new IllegalArgumentException("unknown subscriber");
		}
		
		subscribers.remove(subscriber);
	}
	
	@Override
	public void modelChanged(Model<P> model) {
		for (ICollectionModelSubscriber<Model<P>> subscriber: subscribers) {
			subscriber.onUpdate(model);			
		}
	}
}
