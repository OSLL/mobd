package edu.eltech.mobview.client.mvc.view;

import edu.eltech.mobview.client.mvc.model.IModelSubscriber;
import edu.eltech.mobview.client.mvc.model.Model;

/**
 * Базовое представление
 * @param <M> модель
 * @param
 * <P>
 * свойство модели
 */
public abstract class BaseView<M extends Model<P>, P> implements
		IModelSubscriber<P> {
	private M model;

	/**
	 * Получить модель
	 * @return модель
	 */
	protected M getModel() {
		return model;
	}

	/**
	 * Установить модель
	 * @param model модель
	 */
	public void setModel(M model) {
		unsubscribe();
		this.model = model;
		subscribe();
	}

	/**
	 * Подписаться на события модели
	 */
	private void subscribe() {
		if (model != null) {
			model.subscribe(this);
		}
	}

	/**
	 * Снять подписку с модели
	 */
	private void unsubscribe() {
		if (model != null) {
			model.unsubscribe(this);
		}
	}

	/**
	 * Завершающие действия
	 */
	public void dispose() {
		unsubscribe();
	}
}