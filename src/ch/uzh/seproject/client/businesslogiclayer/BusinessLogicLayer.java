package ch.uzh.seproject.client.businesslogiclayer;

import com.google.gwt.logging.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.uzh.seproject.client.dataaccesslayer.DataAccessLayer;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;

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
	 * example getting data
	 */
	public void exampleGetData(AsyncCallback<List<WeatherRecord>> callback) {
		// no data-manipulation yet, maybe if there is missing data, the business logic layer should fix that
		dal.getWeatherData(callback);
	}

}
