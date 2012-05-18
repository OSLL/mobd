package edu.eltech.mobview.client.mvc.view;

import edu.eltech.mobview.client.mvc.controller.Controller;
import edu.eltech.mobview.client.mvc.model.Model;

/**
 * Представление
 * @param <P> свойство модели
 */
public abstract class View<P> extends BaseView<Model<P>, P> {
	private final Controller<P> controller = new Controller<P>();

	/**
	 * Редактировать модель
	 * @param property свойство модели
	 */
	protected void edit(P property) {
		controller.execute(Controller.O.EDIT, getModel(), property);
	}
}