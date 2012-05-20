package edu.eltech.mobview.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.eltech.mobview.client.data.PointOnMap;
import edu.eltech.mobview.client.mvc.model.CollectionModel;
import edu.eltech.mobview.client.mvc.model.Model;
import edu.eltech.mobview.client.mvc.view.BaseCollectionView;

public class PointInfo extends VerticalPanel {
	private TextBox tfRadius = new TextBox();
	private TextArea taDescription = new TextArea();
	private Button btSave = new Button("Сохранить");
	
	private Model<PointOnMap> data;
	
	public void clear() {
		tfRadius.setText("");
		taDescription.setText("");
	}
	
	public PointInfo() {
		VerticalPanel vPanel = this;//new VerticalPanel();
		vPanel.setSpacing(5);
//		vPanel.add(new HTML("<b>Информация</b><br/><br/>"));		
		
//		HorizontalPanel hPanelRadius = new HorizontalPanel();
//		hPanelRadius.setSpacing(5);
		vPanel.add(new Label("Радиус"));
		vPanel.add(tfRadius);
		//vPanel.add(hPanelRadius);
		
		
		HorizontalPanel hPanelButtons = new HorizontalPanel();
		hPanelButtons.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		hPanelButtons.setWidth("100%");
		hPanelButtons.add(btSave);
		
		btSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (data == null) {
					// TODO
					throw new Error("Not implemented yet");
				} else {
					PointOnMap p = data.getProperty();
					p.setDescription(taDescription.getText());
					data.setProperty(p);
				}
			}
		});
		
		Label lDescription = new HTML("Описание");
	
		vPanel.add(lDescription);		
		vPanel.add(taDescription);
		vPanel.add(hPanelButtons);

		//setWidget(vPanel);
	}

	public void attachSelectionModel(CollectionModel<PointOnMap> selectionModel) {		
		selectionModel.subscribe(new BaseCollectionView<PointOnMap>() {

			@Override
			public void onAdd(Model<PointOnMap> model) {
				setEnabled(true);
				data = model;
				taDescription.setText(model.getProperty().getDescription());
			}

			@Override
			public void onRemove(Model<PointOnMap> property) {
				data = null;
				clear();
				setEnabled(false);
			}

			@Override
			public void onUpdate(Model<PointOnMap> property) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void setEnabled(boolean enabled) {
		taDescription.setEnabled(enabled);
		tfRadius.setEnabled(enabled);
		btSave.setEnabled(enabled);
	}
}
