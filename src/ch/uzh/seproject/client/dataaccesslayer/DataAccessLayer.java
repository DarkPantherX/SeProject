package ch.uzh.seproject.client.dataaccesslayer;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
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
	 * - filters: add filters if needed, e.g. to filter for a specific city or year.
	 * 				"null" is interpreted as "no filter".
	 * - order: order by means that the data is returned in a speficif order, 
	 * 			e.g. newest date first. "null" is interpreted as "order doesn't metter".
	 * - limit: limit number of returns, e.g. limit 10 means only 10 results are returned. 
	 * 			Used to increase performance. "null" is interpreted as "no limit".
	 * 
	 * 	example:
	 * 
	 * 	// dates to filter
	 * 	Date from = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("1849-01-01 00:00:00");
	 *	Date to = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("1850-01-01 00:00:00");
	 *
	 *	// filters
	 *	List<Filter> filters = new ArrayList<Filter>();
	 *	filters.add(new Filter("city ==", "Abidjan"));
	 *	filters.add(new Filter("date >=", from));
	 *	filters.add(new Filter("date <=", to));
	 *
	 *	// order by date desc ("date" if desc is needed, note the "-")
	 *	String order = "-date";
	 *	
	 *	// limit by 5 returns
	 *	Integer limit = 5;
	 *	// call function
	 *
	 *	getWeatherData(filters, order, limit, new AsyncCallback<List<WeatherRecord>>() {
	 *		@Override 
	 *		public void onFailure(Throwable caught) { }
	 *		@Override 
	 *		public void onSuccess(List<WeatherRecord> result) { 
	 *			for(WeatherRecord tmp : result) {
	 *				Logger logger = Logger.getLogger("NameOfYourLogger");
	 *				logger.log(Level.SEVERE,"Date: " + tmp.getDate().toString() + "  City: " + tmp.getCity());
	 *		}
	 *	}});
	 * 
	 */
	public void getWeatherData(List<Filter> filters, String order, Integer limit, AsyncCallback<List<WeatherRecord>> callback) {
		// asynchronous call to server
		server.getWeatherData(filters, order, limit, callback);
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
				//throw new RuntimeException(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				// nothing to do
			}
		});
	}
}
