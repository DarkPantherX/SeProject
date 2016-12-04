package ch.uzh.seproject.client.presentationlayer;

import java.util.ArrayList;
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
	private Button worldmapButton = Button.wrap(Document.get().getElementById("worldmapButton"));
	private Button submitbutton = Button.wrap(Document.get().getElementById("submitbutton"));
    private static final List<WeatherRecord> WEATHERRECORDS = Arrays.asList();
	/**
	 * This is the entry point method.
	 */
    /**
     * Mapstuff
     */
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
		// Prepare the data
		Object[][] data = new Object[][] { { "Country", "Population" }, { "China", "China: 1,363,800,000" },
				{ "India", "India: 1,242,620,000" }, { "US", "US: 317,842,000" },
				{ "Indonesia", "Indonesia: 247,424,598" }, { "Brazil", "Brazil: 201,032,714" },
				{ "Pakistan", "Pakistan: 186,134,000" }, { "Nigeria", "Nigeria: 173,615,000" },
				{ "Bangladesh", "Bangladesh: 152,518,015" }, { "Russia", "Russia: 146,019,512" },
				{ "Japan", "Japan: 127,120,000" } };
		DataTable dataTable = ChartHelper.arrayToDataTable(data);

		// Set options
		MapOptions options = MapOptions.create();
		options.setShowTip(true);

		// Draw the chart
		chart.draw(dataTable, options);
	}
	
	public void onModuleLoad() {	
	}
	
	public void drawTable()
	{
		// dates to filter
		Date from = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("1849-01-01 00:00:00");
		Date to = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("1850-01-01 00:00:00");

		// filters
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("city ==", "Abidjan"));
		filters.add(new Filter("date >=", from));
		filters.add(new Filter("date <=", to));

		// order by date desc ("date" if desc is needed, note the "-")
		String order = "-date";

		// limit by 5 returns
		Integer limit = 5;

		// call function
		bll.getWeatherData(filters, order, limit, new AsyncCallback<List<WeatherRecord>>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<WeatherRecord> result) {
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


