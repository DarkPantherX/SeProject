package ch.uzh.seproject.client.presentationlayer;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import ch.uzh.seproject.client.businesslogiclayer.BusinessLogicLayer;

/**
 * This is the main inteface for the Presentation-Layer.
 */
public class PresentationLayer implements EntryPoint{
	// business-logic-layer
	private BusinessLogicLayer bll = new BusinessLogicLayer();
	private Button tableButton = Button.wrap(Document.get().getElementById("tableButton"));
	private Button worldmapButton = Button.wrap(Document.get().getElementById("worldmapButton"));
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		this.tableButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				DOM.getElementById("table").getStyle().setDisplay(Display.BLOCK);
				DOM.getElementById("worldmap").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("tableButton").addClassName("active");
				DOM.getElementById("worldmapButton").removeClassName("active");
			}
		});
		this.worldmapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				DOM.getElementById("table").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("worldmap").getStyle().setDisplay(Display.BLOCK);
				DOM.getElementById("worldmapButton").addClassName("active");
				DOM.getElementById("tableButton").removeClassName("active");
			}
		});
		
	}
	
	

	public void addTable(){
	}
	/**
	 * Constructor
	 */
	public PresentationLayer() {
		
	}
	public void drawUI() {
		/** create dialogbox
		final DialogBox out = new DialogBox();
		out.setText("Hello World");
		out.center();
		
		// send some data in database
		bll.exampleSaveData();
		// get data back
		bll.exampleGetData(new AsyncCallback<List<WeatherRecord>>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<WeatherRecord> result) {
				for (WeatherRecord tmp : result) {
					// show data in dialogbox
					out.setText("city:  " + tmp.getCity());
				}
			}
		});
		*/


	}

}
