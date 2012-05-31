package edu.eltech.mobview.client.ui;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.sencha.gxt.cell.core.client.SliderCell;
import com.sencha.gxt.cell.core.client.SliderCell.SliderAppearance;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.util.BaseEventPreview;
import com.sencha.gxt.core.client.util.Point;

public class TimeSliderCell extends SliderCell {

	private SliderAppearance appearance;

	public TimeSliderCell() {
		this.appearance = GWT.<SliderAppearance> create(SliderAppearance.class);
	}

	@Override
	protected void onMouseDown(com.google.gwt.cell.client.Cell.Context context,
			Element parent, NativeEvent event,
			ValueUpdater<Integer> valueUpdater) {

		Element target = Element.as(event.getEventTarget());
		if (!appearance.getThumb(parent).isOrHasChild(target)) {
			int value = appearance.getClickedValue(context, parent, event);
			value = reverseValue(parent.<XElement> cast(), value);
			value = normalizeValue(value);

			valueUpdater.update(value);

			int pos = translateValue(parent.<XElement> cast(), value);
			appearance.setThumbPosition(parent, pos);

			return;
		}

		BaseEventPreview preview = new TimeDragPreview(context, parent,
				valueUpdater, event);
		appearance.onMouseDown(context, parent, event);
		preview.add();
	}

	private class TimeDragPreview extends BaseEventPreview {

		private ValueUpdater<Integer> valueUpdater;
		private Context context;
		private Element parent;
		private int thumbWidth;
		private int thumbHeight;

		public TimeDragPreview(com.google.gwt.cell.client.Cell.Context context,
				Element parent, ValueUpdater<Integer> valueUpdater,
				NativeEvent e) {
			this.valueUpdater = valueUpdater;
			this.context = context;
			this.parent = parent;
			XElement t = appearance.getThumb(parent).cast();
			thumbWidth = t.getOffsetWidth();
			thumbHeight = t.getOffsetHeight();
		}

		@Override
		protected boolean onPreview(NativePreviewEvent event) {

			switch (event.getTypeInt()) {
			case Event.ONMOUSEMOVE: {
				positionTip(event.getNativeEvent());
				break;
			}
			case Event.ONMOUSEUP:
				this.remove();
				XElement p = XElement.as(parent);
				int v = setValue(
						p,
						reverseValue(
								p,
								appearance.getClickedValue(context, p,
										event.getNativeEvent())));
				valueUpdater.update(v);
				appearance.onMouseUp(context, parent, event.getNativeEvent());
				appearance.onMouseOut(context, parent, event.getNativeEvent());
				break;
			}

			return true;
		}

		private int setValue(Element parent, int value) {
			value = normalizeValue(value);
			int left = translateValue(parent.<XElement> cast(), value);

			appearance.setThumbPosition(parent, left);

			return value;
		}

		private void positionTip(NativeEvent e) {
			Point thumbPosition = appearance.getThumb(parent).<XElement> cast()
					.getPosition(false);

			int x = thumbPosition.getX();
			int y = thumbPosition.getY();

			boolean top = y > 35;
			int w = 10;
			int h = 10;

			if (top) {
				thumbPosition.setX(x - (w / 2) + (thumbWidth / 2));
				thumbPosition.setY(y - h - 5);

			} else {
				thumbPosition.setX(x - (w / 2) + (thumbWidth / 2));
				thumbPosition.setY(y + thumbHeight + 5);
			}
		}
	}
}
