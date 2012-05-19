package edu.eltech.mobview.client.mvc.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Модель
 *
 * @param <P> свойство модели
 */
public class Model<P> {
	private P property;
	
	private final Collection<IModelSubscriber<P>> subscribers =
			new ArrayList<IModelSubscriber<P>>();

	/**
	 * Конструктор модели
	 * @param property свойство модели
	 */
	public Model(P property) {
		if (property == null) {
			throw new NullPointerException("null property");
		}
		
		this.property = property;
	}
	
	public void setProperty(P property) {
		if (property == null) {
			throw new NullPointerException("null property");
		}
		
		this.property = property;
		notifySubscribers();
	}
	
	public P getProperty() {
		if (property == null) {
			throw new NullPointerException("null property");
		}
		return property;
	}

	/**
	 * Оповестить всех подписчиков
	 */
	protected void notifySubscribers() {
		for (final IModelSubscriber<P> subscriber : subscribers) {
			notifySubscriber(subscriber);
		}
	}

	/**
	 * Оповестить подписчика
	 * @param subscriber подписчик
	 */
	private void notifySubscriber(IModelSubscriber<P> subscriber) {
		subscriber.modelChanged(this);		
	}
	
	public void subscribe(IModelSubscriber<P> subscriber) {
		if (subscriber == null) {
			throw new NullPointerException("subscriber is null");
		}
		
		if (subscribers.contains(subscriber)) {
			throw new IllegalArgumentException("re-subscribe");
		}
		
		subscribers.add(subscriber);
		notifySubscriber(subscriber);
	}
	
	/**
	 * Отписаться
	 * 
	 * @param subscriber подписчик модели
	 */
	public void unsubscribe(IModelSubscriber<P> subscriber) {
		if (subscriber == null) {
			throw new NullPointerException("Пустой параметр");
		}
		
		if (!subscribers.contains(subscriber)) {
			throw new IllegalArgumentException("Неизвестный подписчик: " + subscriber);
		}
		
		subscribers.remove(subscriber);
	}

	@Override
	public String toString() {
		return property.toString();
	}
}