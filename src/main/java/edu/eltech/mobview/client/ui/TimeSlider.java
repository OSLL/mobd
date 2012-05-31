package edu.eltech.mobview.client.ui;

import com.sencha.gxt.widget.core.client.Slider;

public class TimeSlider extends Slider {
	public TimeSlider() {
		super(new TimeSliderCell());
		setStyleName("timeSlider");
	}
}
