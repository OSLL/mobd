package edu.eltech.mobview.client.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;
import com.sencha.gxt.widget.core.client.Slider;

public class ClockWidget extends HorizontalPanel {
	private final static int DELAY = 50;
	private int speed = 1;
	private int direction = 0;
	private final Timer timer;

	private Label lblTime;
	private double time;
	private Slider slider;
		
	private ToggleButton forwardButton = new ToggleButton(">");
	private ToggleButton backwardButton = new ToggleButton("<");
	
	private List<ToggleButton> speedButtons = new ArrayList<ToggleButton>(); 
	
	private int stopTime;
	private int startTime;
	
	public ClockWidget(final ClockListener cl) {
		
		slider = new TimeSlider();
		slider.setIncrement(1);
		slider.setWidth(300);
		slider.hideToolTip();
		
		this.timer = new Timer() {
			@Override
			public void run() {
				time += DELAY / 1000.0 * direction * speed;
				Date date = new Date((long) (1000L * time));
				lblTime.setText(DateTimeFormat.getFormat("yyyy.MM.dd HH:mm:ss").format(date));
				slider.setValue((int) time);
				cl.onTick((int) time);
				
				if (time < startTime) {
					time = startTime;
					backwardButton.setValue(false);
				}
				
				if (time > stopTime) {
					time = stopTime;
					forwardButton.setValue(false);
				}
			}
		};
		setSize("450px", "30");
		setVerticalAlignment(ALIGN_MIDDLE);
		lblTime = new Label("Loading ...");
		lblTime.setWidth("225px");
		lblTime.setStyleName("lblTime");
		add(lblTime);

		backwardButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				Boolean value = event.getValue();
				if (value) {
					direction = -1;
					forwardButton.setValue(false);
				} else {
					direction = 0;
				}
			}
		});
		
		forwardButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				Boolean value = event.getValue();
				if (value) {
					direction = 1;
					backwardButton.setValue(false);
				} else {
					direction = 0;
				}
			}
		});
		
		add(backwardButton);
		add(forwardButton);
		
		slider.addValueChangeHandler(new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				time = event.getValue();
			}
		});
		
		for (final int n : new int[] {1, 100, 500, 1000, 10000, 50000}) {
			final ToggleButton bt = new ToggleButton("x" + n);
			bt.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) {
						setSpeed(n);
						bt.setEnabled(false);
						
						for (ToggleButton b : speedButtons) {
							if (!b.equals(bt)) {
								b.setValue(false);
								b.setEnabled(true);
							}
						}
					} 
				}
			});
			
			speedButtons.add(bt);
			add(bt);
		}
		
		speedButtons.get(0).setValue(true, true);
		forwardButton.setValue(true, true);
		
		add(slider);
		
		setStylePrimaryName("clockWidget");
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
		slider.setMinValue(startTime);
	}
	
	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
		slider.setMaxValue(stopTime);
	}
	
	public void setTime(int ts) {
		this.time = ts;
	}
	
	public interface ClockListener {
		public void onTick(int ts);
	}
	
	public void start() {
		timer.scheduleRepeating(DELAY);
	}
	
	private void setSpeed(int speed) {
		this.speed = speed;
	}
}

