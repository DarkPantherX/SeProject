package ch.uzh.seproject.client.presentationlayer;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;

import ch.uzh.seproject.client.businesslogiclayer.BusinessLogicLayer;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;
import ch.uzh.seproject.client.businesslogiclayer.BusinessLogicLayer;

/**
 * This is the main inteface for the Presentation-Layer.
 */
public class PresentationLayer implements EntryPoint {
	// business-logic-layer
	private BusinessLogicLayer bll = new BusinessLogicLayer();
	private Button tableButton = Button.wrap(Document.get().getElementById("tableButton"));
	private Button worldmapButton = Button.wrap(Document.get().getElementById("worldmapButton"));
	//private Button uploadButton = Button.wrap(Document.get().getElementById("UploadButton"));
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		this.worldmapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				DOM.getElementById("worldmap").getStyle().setDisplay(Display.BLOCK);
				DOM.getElementById("table").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("worldmapButton").addClassName("active");
				DOM.getElementById("tableButton").removeClassName("active");
			}
		});
		
		
		this.tableButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				DOM.getElementById("worldmap").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("table").getStyle().setDisplay(Display.BLOCK);
				DOM.getElementById("tableButton").addClassName("active");
				DOM.getElementById("worldmapButton").removeClassName("active");
			}
		});    
		/**this.uploadButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
			}
		});*/
		
		
		/*
		 * Example to get weather data from 2012 to 2013
		 */
		Date from = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-01-01 00:00:00");
		Date to = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2013-01-01 00:00:00");
		
		bll.getWeatherData(from, to, new AsyncCallback<List<WeatherRecord>>() {
			@Override 
			public void onFailure(Throwable caught) { }
		
			@Override 
			public void onSuccess(List<WeatherRecord> result) { 
				// result is the list you want 
				// this function is executed when the datais ready (asynchronous) 
				
				// create logger to print to webbrowser (developer mode)
				Logger logger = Logger.getLogger("NameOfYourLogger");
			
				// print every result from 2012 to 2013
				for (WeatherRecord tmp : result) {
					logger.log(Level.SEVERE, tmp.getCity() + "  " + tmp.getDate().toString());
			}
		}});
		
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


