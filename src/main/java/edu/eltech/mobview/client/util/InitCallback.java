package edu.eltech.mobview.client.util;

public abstract class InitCallback {
	private boolean isInitialized = false;
	private int value = 0;
	private int counter;
	
	public void setCounter(int counter) {
		if (!isInitialized) {
			this.counter = counter;
			isInitialized = true;
		} else {
			throw new IllegalStateException("already initialized");
		}
	}
	
	public void inc() {
		if (!isInitialized) {
			throw new IllegalStateException("counter not initialized");
		}
		++value;
		
		if (value == counter) {
			onInit();
		}
	}
	
	public abstract void onInit();
}
