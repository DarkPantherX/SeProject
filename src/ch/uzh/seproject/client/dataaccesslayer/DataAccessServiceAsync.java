package ch.uzh.seproject.client.dataaccesslayer;

import java.util.Date;
import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DataAccessServiceAsync {
	void getWeatherData(AsyncCallback<List<WeatherRecord>> callback);
	void getWeatherData(Date dateFrom, Date dateTo, AsyncCallback<List<WeatherRecord>> callback);
	void setWeatherData(List<WeatherRecord> weatherData, AsyncCallback<Void> asyncCallback);
	void getWeatherData(List<Filter> filters, String order, Integer limit, AsyncCallback<List<WeatherRecord>> callback);
}
