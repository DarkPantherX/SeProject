package ch.uzh.seproject.client.businesslogiclayer;
import ch.uzh.seproject.client.dataaccesslayer.DataAccessLayer;
import ch.uzh.seproject.client.dataaccesslayer.Filter;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;

import java.util.Date;
import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the main inteface for the Business-Logic-Layer.
 */
public class BusinessLogicLayer {
	// data-access-layer
	private DataAccessLayer dal = new DataAccessLayer();

	/**
	 * Constructor
	 */
	public BusinessLogicLayer() {
		
	}
	
	/**
	 * see dataaccesslayer
	 */
	public void getWeatherData(Date dateFrom, Date dateTo, AsyncCallback<List<WeatherRecord>> callback) {
		// no data-manipulation yet, maybe if there is missing data, the business logic layer should fix that
		dal.getWeatherData(dateFrom, dateTo, callback);
	}
	
	/**
	 * see dataaccesslayer
	 */
	public void getWeatherData(List<Filter> filters, String order, Integer limit, AsyncCallback<List<WeatherRecord>> callback) {
		// no data-manipulation yet, maybe if there is missing data, the business logic layer should fix that
		dal.getWeatherData(filters, order, limit, callback);
	}
}
