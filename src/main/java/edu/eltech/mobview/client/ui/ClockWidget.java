package edu.eltech.mobview.client.ui;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;

public class ClockWidget extends HorizontalPanel {
	private final static int DELAY = 50;
	private int step = 1;
	private final Timer timer;

	private Label lblTime;
	private ClockListener clockListener;
	private int time;
	
	private ToggleButton forwardButton = new ToggleButton(">");
	private ToggleButton backwardButton = new ToggleButton("<");
	
	private static final Map<Integer, Integer> SCALE 
		= new TreeMap<Integer, Integer>();
	
	static {
		SCALE.put(0, 0);
		SCALE.put(1, 1);
		SCALE.put(2, 60);
		SCALE.put(3, 3600);
	}
	
	
	public ClockWidget(final ClockListener cl) {
		this.clockListener = cl;
		this.timer = new Timer() {
			@Override
			public void run() {
				time += DELAY / 1000.0 * (step >= 0 ? SCALE.get(step) : SCALE.get(-step));
				Date date = new Date(1000L * time);
				lblTime.setText(DateTimeFormat.getFormat("yyyy.MM.dd HH:mm:ss").format(date));
				
				cl.onTick(time);
			}
		};
		setSize("450px", "30");
		setVerticalAlignment(ALIGN_MIDDLE);
		lblTime = new Label("13.12.2009 13:13:13");
		lblTime.setWidth("400px");
		lblTime.setStyleName("lblTime");
		add(lblTime);
//		forwardButton.set
		forwardButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				backwardButton.setValue(false);
				step++;				
			}
		});
		backwardButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				forwardButton.setValue(false);
				step--;
			}
		});
		add(backwardButton);
		add(forwardButton);
//		add(new HTML("<h3>x1</h3>"));
		
		setStylePrimaryName("clockWidget");
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
}
