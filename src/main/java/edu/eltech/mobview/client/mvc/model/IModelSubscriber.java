package edu.eltech.mobview.client.mvc.model;

/**
 * Подписчик модели
 *
 * @param <P> свойство модели
 */
public interface IModelSubscriber<P> {
	/**
	 * Событие изменения модели
	 * @param model модель
	 */
	void modelChanged(Model<P> model);
}
