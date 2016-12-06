package ch.uzh.seproject.client.dataaccesslayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.junit.client.GWTTestCase;

public class TestDataAccessLayer extends GWTTestCase {
	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "ch.uzh.seproject.SeProject";
	}

	public void testSetWeatherData() {       
        
		DataAccessLayer dal = new DataAccessLayer();

		// generate three records
		Date date1 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 16:00:48");
		Date date2 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 17:00:48");
		Date date3 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse("2012-06-20 18:00:48");

		WeatherRecord rec1 = new WeatherRecord(date1, 26.704, 6.7, "new york", "usa", "40.34N", "10.34S");
		WeatherRecord rec2 = new WeatherRecord(date2, 8.43, 8.4, "zurich", "switzerland", "20.64N", "5.82S");
		WeatherRecord rec3 = new WeatherRecord(date3, 19.59, 1.9, "munich", "germany", "5.5N", "8.8S");

		// generate list
		List<WeatherRecord> list = new ArrayList<WeatherRecord>();

		// Case: null list
		dal.setWeatherData(null);
		
		// Case: empty list
		dal.setWeatherData(list);
		
		// Case: list with data
		list.add(rec1);
		list.add(rec2);
		list.add(rec3);
		dal.setWeatherData(list);
	}

}
