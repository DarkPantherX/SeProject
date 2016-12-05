package ch.uzh.seproject.client.presentationlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;

import ch.uzh.seproject.client.businesslogiclayer.BusinessLogicLayer;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;
import ch.uzh.seproject.client.dataaccesslayer.Filter;


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.map.Map;
import com.googlecode.gwt.charts.client.map.MapOptions;
import com.googlecode.gwt.charts.client.util.ChartHelper;
/**
 * This is the main inteface for the Presentation-Layer.
 */
public class PresentationLayer extends DockLayoutPanel implements EntryPoint {
	// business-logic-layer
	private BusinessLogicLayer bll = new BusinessLogicLayer();
	private Button tableButton = Button.wrap(Document.get().getElementById("tableButton"));
	private Button originButton = Button.wrap(Document.get().getElementById("originButton"));
	private Button worldmapButton = Button.wrap(Document.get().getElementById("worldmapButton"));
	private Button submitbutton = Button.wrap(Document.get().getElementById("submitbutton"));
	private Button clearButton = Button.wrap(Document.get().getElementById("clearButton"));
    private static final List<WeatherRecord> WEATHERRECORDS = Arrays.asList();
	/**
	 * This is the entry point method.
	 */
    /**
     * Mapstuff
     */
    Logger logger = Logger.getLogger("com.google.gwt.logging.Logging");
    private Map chart;
    
	public PresentationLayer() {
		super(Unit.PX);
		initialize();
	}
	
	private void initialize() {
		this.worldmapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				DOM.getElementById("worldmap").getStyle().setDisplay(Display.BLOCK);
				DOM.getElementById("table").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("dataOrigin").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("worldmapButton").addClassName("active");
				DOM.getElementById("originButton").removeClassName("active");
				DOM.getElementById("tableButton").removeClassName("active");
			}
		});
		
		this.submitbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				drawTable();
			}
		});
		
		this.clearButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				clearTable();
			}
		});
		
		this.tableButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				DOM.getElementById("worldmap").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("dataOrigin").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("table").getStyle().setDisplay(Display.BLOCK);
				DOM.getElementById("tableButton").addClassName("active");
				DOM.getElementById("originButton").removeClassName("active");
				DOM.getElementById("worldmapButton").removeClassName("active");
			}
		});
		
		this.originButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				DOM.getElementById("worldmap").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("table").getStyle().setDisplay(Display.NONE);
				DOM.getElementById("dataOrigin").getStyle().setDisplay(Display.BLOCK);
				DOM.getElementById("originButton").addClassName("active");
				DOM.getElementById("tableButton").removeClassName("active");
				DOM.getElementById("worldmapButton").removeClassName("active");
			}
		});
		
		ChartLoader chartLoader = new ChartLoader(ChartPackage.MAP);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// Create and attach the chart
				chart = new Map();
				RootPanel.get("mapBox").add(chart);
				drawMap();
			}
		});
		
		// draw table
		drawTable();
	}
	
	private void drawMap() {
		// dates to filter
		Date from = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2011-01-01 00:00:00");
		Date to = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-01-01 00:00:00");
		
		
		bll.getWeatherData(from, to, new AsyncCallback<List<WeatherRecord>>() {
			@Override
			
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<WeatherRecord> result) {
			    Object[][] data = new Object[205][2];
				data[0][0] = "Country";
				data[0][1] = "Population";
				int counter = 1;
				for (WeatherRecord tmp : result) {
					if(counter < 205){
						data[counter][0] = tmp.getCity();
						data[counter][1] = tmp.getCity() + ", avergage Temperature: " + tmp.getAverageTermperature();
						logger.log(Level.SEVERE, "" + data[counter][0]);
						counter = counter + 1;
					}
					
				}
				DataTable dataTable = ChartHelper.arrayToDataTable(data);
				MapOptions options = MapOptions.create();
				options.setShowTip(true);
				chart.draw(dataTable, options);
			}
			
			
		});
		
		// Prepare the data
		

		// Set options
		

		// Draw the chart
		
	}
	
	public void onModuleLoad() {	
	}
	
	private void clearTable() {
		RootPanel.get("date").clear();
		RootPanel.get("city").clear();
		RootPanel.get("country").clear();
		RootPanel.get("latitude").clear();
		RootPanel.get("longitude").clear();
		RootPanel.get("avgtemperature").clear();
		RootPanel.get("avguncertainty").clear();
		
	}
	
	
	public void drawTable()
	{
		clearTable();
		// dates to filter
		String endDate=((InputElement)(Element)DOM.getElementById("endDate")).getValue();
		String startDate=((InputElement)(Element)DOM.getElementById("startDate")).getValue();
		String cityFil=((InputElement)(Element)DOM.getElementById("cityField")).getValue();
		String countryFil=((InputElement)(Element)DOM.getElementById("countryField")).getValue();
		String endTemp=((InputElement)(Element)DOM.getElementById("endTemp")).getValue();
		String startTemp=((InputElement)(Element)DOM.getElementById("startTemp")).getValue();
		
		HTML date = new HTML("" + "Loading...");
		RootPanel.get("date").add(date);

		Date from = null;
		Date to = null;
		
		try{
			from = DateTimeFormat.getFormat("dd-MM-yyyy HH:mm:ss").parseStrict(startDate.replace(".","-") +" 00:00:00");
			to = DateTimeFormat.getFormat("dd-MM-yyyy HH:mm:ss").parseStrict(endDate.replace(".","-") +" 00:00:00");
		}catch(IllegalArgumentException ex){
			HTML date1 = new HTML("" + "Your date is not in the right format: dd-MM-yyyy");
			RootPanel.get("date").add(date1);
		}
		
		

		// filters
		List<Filter> filters = new ArrayList<Filter>();
		
		double tempB=0l;
		double tempE=0l;
		
		if(!startTemp.trim().equals("")&&!endTemp.trim().equals("")){
			
			try{
				tempB = Double.parseDouble(startTemp);
				tempE = Double.parseDouble(endTemp);
				filters.add(new Filter("averageTermperature >=", tempB));
				filters.add(new Filter("averageTermperature <=", tempE));
			}catch(NumberFormatException ex){
				HTML date1 = new HTML("" + "Your temprature is not in the right format: ");
				RootPanel.get("date").add(date1);
			}
		
		}
		
		if(!cityFil.trim().equals("")){
			filters.add(new Filter("city ==", cityFil));
		}
		
		if(!countryFil.trim().equals("")){
			filters.add(new Filter("country ==", countryFil));
		}
		
		

		

		filters.add(new Filter("date >=", from));
		filters.add(new Filter("date <=", to));

		// order by date desc ("date" if desc is needed, note the "-")
		String order = "-date";

		// limit by 5 returns
		Integer limit = 10000;

		// call function
		bll.getWeatherData(filters, order, limit, new AsyncCallback<List<WeatherRecord>>() {
			@Override
			public void onFailure(Throwable caught) {
				HTML date = new HTML("" + "Error: connection to server not possible");
				RootPanel.get("date").add(date);
			}

			@Override
			public void onSuccess(List<WeatherRecord> result) {
				if(result.size()>0){
					RootPanel.get("date").clear();
				}else{
					HTML date = new HTML("" + "No data found with your filters, please check date format and spelling");
					RootPanel.get("date").add(date);

				}
				
				for (WeatherRecord tmp : result) {
					DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy-MM-dd");

					HTML date = new HTML("" + formatter.format(tmp.getDate()));
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
			}
		});

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

		// Push the data into the widget.
		// table.setRowData(0, WEATHERRECORDS);
		// Add the widgets to the root panel.
		// RootPanel.get("tableData").add(table);
	}
	
}


