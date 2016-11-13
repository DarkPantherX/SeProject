package ch.uzh.seproject.client.dataaccesslayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the main inteface for the Data-Access-Layer. It handles the
 * communication with the database (App Engine Datastore)
 */
public class DataAccessLayer {
	// connection to server-side service.
	private final DataAccessServiceAsync server = GWT.create(DataAccessService.class);

	/**
	 * Constructor
	 */
	public DataAccessLayer() {
		/*
		server.readServerFiles(new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// if there is a error
			}

			@Override
			public void onSuccess(Void result) {
				// nothing to do
			}
		});
		*/
		
		// generate three records
		Date date1 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 16:00:48");
		Date date2 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 17:00:48");
		Date date3 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 18:00:48");

		WeatherRecord rec1 = new WeatherRecord(date1, 26.704, 6.7, "new york", "usa", 40.34, 10.34);
		WeatherRecord rec2 = new WeatherRecord(date2, 8.43, 8.4, "zurich", "switzerland", 20.64, 5.82);
		WeatherRecord rec3 = new WeatherRecord(date3, 19.59, 1.9, "munich", "germany", 5.5, 8.8);

		// generate list
		List<WeatherRecord> list = new ArrayList<WeatherRecord>();
		
		list.add(rec1);
		list.add(rec2);
		list.add(rec3);
		setWeatherData(list);
	}

	/**
	 * This function returns every WeatherRecord available (in a list).
	 * 
	 * One (1) WeatherRecord is a abstraction for 1 row of the .csv file.
	 * 
	 * 
	 * example:
	 * 
	 * getWeatherData(new AsyncCallback<List<WeatherRecord>>() {
	 * 		@Override 
	 * 		public void onFailure(Throwable caught) 
	 * 		{ 
	 * 		}
	 * 
	 * 		@Override 
	 * 		public void onSuccess(List<WeatherRecord> result) { 
	 * 			// result is the list you want 
	 * 			// this function is executed when the datais ready (asynchronous) 
	 * 	}});
	 */
	public void getWeatherData(AsyncCallback<List<WeatherRecord>> callback) {
		// asynchronous call to server
		server.getWeatherData(callback);
	}
	
	
	/**
	 * Same as getWeatherData() but with filter options for dateFrom and dateTo
	 * 
	 * dateFrom	 |	dateTo	 |	return
	 *----------------------------------
	 *    null	 |	 null	 |	eveything
	 *    set	 |	 null	 |	return-date >= dateFrom
	 *    null	 |	 set	 |	return-date <= dateTo
	 *    set	 |	 set	 |  dateFrom <= return-date <= dateFrom
	 */
	public void getWeatherData(Date dateFrom, Date dateTo, AsyncCallback<List<WeatherRecord>> callback) {
		// asynchronous call to server
		server.getWeatherData(dateFrom, dateTo, callback);
	}

	/**
	 * This function adds a list of WeatherRecord to the database. Duplicates
	 * are updated.
	 * 
	 * A WeatherRecord needs to have a Date, City, Longitude and Latitude,
	 * otherwise it won't be added.
	 */
	public void setWeatherData(List<WeatherRecord> weatherData) throws RuntimeException{
		// asynchronous call to server
		
		server.setWeatherData(weatherData, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// if there is a error
				throw new RuntimeException(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				// nothing to do
			}
		});
	}
}
