package ch.uzh.seproject.client.presentationlayer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.thirdparty.javascript.rhino.head.ast.Label;

import ch.uzh.seproject.client.businesslogiclayer.BusinessLogicLayer;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;
import ch.uzh.seproject.client.dataaccesslayer.DataAccessLayer;
import ch.uzh.seproject.client.dataaccesslayer.Filter;

/**
 * This is the main inteface for the Presentation-Layer.
 */
public class PresentationLayer implements EntryPoint {
	// business-logic-layer
	private BusinessLogicLayer bll = new BusinessLogicLayer();
	private Button tableButton = Button.wrap(Document.get().getElementById("tableButton"));
	private Button worldmapButton = Button.wrap(Document.get().getElementById("worldmapButton"));
	private Button submitbutton = Button.wrap(Document.get().getElementById("submitbutton"));
    private static final List<WeatherRecord> WEATHERRECORDS = Arrays.asList();
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Date from = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-01-01 00:00:00");
		Date to = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2013-01-01 00:00:00");
		
		bll.getWeatherData(from, to, new AsyncCallback<List<WeatherRecord>>() {
			@Override 
			public void onFailure(Throwable caught) { }
			@Override 
			public void onSuccess(List<WeatherRecord> result) { 
				// result is the list you want 
				// this function is executed when the data is ready 
				// print every result from 2012 to 2013
				for (WeatherRecord tmp : result) {
					HTML date = new HTML("" + tmp.getDate());
					HTML city = new HTML("" + tmp.getCity());
					HTML country = new HTML("" + tmp.getCountry());
					HTML latitude = new HTML("" + tmp.getLongitude());
					HTML longitude = new HTML("" + tmp.getLatitude());
					HTML avgtemperature = new HTML("" + tmp.getAverageTermperature());
					HTML avguncertainty = new HTML("" + tmp.getAverageTemperatureUncertainty());
					RootPanel.get("date").add(date);
					RootPanel.get("city").add(city);
					RootPanel.get("country").add(country);
					RootPanel.get("latitude").add(latitude);
					RootPanel.get("longitude").add(longitude);
					RootPanel.get("avgtemperature").add(avgtemperature);
					RootPanel.get("avguncertainty").add(avguncertainty);
					
			}
		}});
		
		
		CellTable<WeatherRecord> table = new CellTable<WeatherRecord>();
	    table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	 // Add a text column to show the name.
	    System.out.println(WEATHERRECORDS);
	    
	      TextColumn<WeatherRecord> cityColumn = new TextColumn<WeatherRecord>() {
	         @Override
	         public String getValue(WeatherRecord object) {
	            return object.getCity();
	         }
	      };
	      table.addColumn(cityColumn, "City");
	      
	      TextColumn<WeatherRecord> countryColumn = new TextColumn<WeatherRecord>() {
		         @Override
		         public String getValue(WeatherRecord object) {
		            return object.getCountry();
		         }
		      };
		      table.addColumn(countryColumn, "Country");

		this.worldmapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				DOM.getElementById("worldmap").getStyle().setDisplay(Display.BLOCK);
				DOM.getElementById("table").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("worldmapButton").addClassName("active");
				DOM.getElementById("tableButton").removeClassName("active");
			}
		});
		
		this.submitbutton.addClickHandler(new ClickHandler() {
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
			
		
		});  /*  
	      // Push the data into the widget.
	      table.setRowData(0, WEATHERRECORDS);


	      // Add the widgets to the root panel.
	      RootPanel.get("tableData").add(table);
	      /*
		 * Example to get weather data from 2012 to 2013
		 */
		
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


