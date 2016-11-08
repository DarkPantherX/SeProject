package ch.uzh.seproject.client.businesslogiclayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.uzh.seproject.client.dataaccesslayer.DataAccessLayer;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;

/**
 * This is the main inteface for the Business-Logic-Layer
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
	 * example saving data
	 */
	public void exampleSaveData() {
		// generate three records
		Date date1 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 16:00:48");
		Date date2 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 17:00:48");
		Date date3 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 18:00:48");

		WeatherRecord rec = new WeatherRecord();
		rec.setCity("new york");
		rec.setDate(date1);
		rec.setLongitude(1);
		rec.setLatitude(2);
		WeatherRecord rec2 = new WeatherRecord();
		rec2.setCity("california");
		rec2.setDate(date2);
		rec2.setLongitude(3);
		rec2.setLatitude(4);
		WeatherRecord rec3 = new WeatherRecord();
		rec3.setCity("san francisco");
		rec3.setDate(date3);
		rec3.setLongitude(3);
		rec3.setLatitude(4);
		
		// add records to list
		List<WeatherRecord> list = new ArrayList<WeatherRecord>();
		list.add(rec);
		list.add(rec2);
		list.add(rec3);
		
		// save list in database
		dal.setWeatherData(list);
	}
	
	/**
	 * example getting data
	 */
	public void exampleGetData(AsyncCallback<List<WeatherRecord>> callback) {
		// no data-manipulation yet, maybe if there is missing data, the business logic layer should fix that
		dal.getWeatherData(callback);
	}

}
