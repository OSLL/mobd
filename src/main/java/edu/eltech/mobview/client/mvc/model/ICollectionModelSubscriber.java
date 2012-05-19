package edu.eltech.mobview.client.mvc.model;

public interface ICollectionModelSubscriber<P> {
	
	public void onAdd(P property);
	
	public void onRemove(P property);
	
	public void onUpdate(P property);
}
