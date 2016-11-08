package ch.uzh.seproject.server.dataaccesslayer;

// RPC
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
// objectify
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;
// datastructures
import java.util.ArrayList;
import java.util.List;
// general
import ch.uzh.seproject.client.dataaccesslayer.DataAccessService;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DataAccessServiceImpl extends RemoteServiceServlet implements DataAccessService {
	
	/**
	 * Constructor
	 */
	public DataAccessServiceImpl() {
		
		// ever class used by objectify needs to be registered !!
		ObjectifyService.register(WeatherRecord.class);
	}
	
	/**
	 * Returns a serializable list of all WeatherRecords
	 */
	@Override
	public List<WeatherRecord> getWeatherData() {
		
		//fetched data from database
		List<WeatherRecord> fetched = ofy().load().type(WeatherRecord.class).list();
		
		// fetched data needs to be copied to a new list, because the fetched-list is not serializable
		List<WeatherRecord> result = new ArrayList<WeatherRecord>();
		
		for (WeatherRecord tmp : fetched) {
			// copy to new list
			result.add(tmp);
		}
		
		return result;
	}

	/**
	 * Save a serializable list of WeatherRecords to the database
	 * Date, City, Longitude, Latitude must be set. Those are used to generate a primary key.
	 */
	@Override
	public void setWeatherData(List<WeatherRecord> weatherData) {	
			// save to database (App Engine Datastore)
		
			for (WeatherRecord tmp : weatherData) {
				if((tmp.getDate() != null) 
						&& (tmp.getCity() != null)
						&& (tmp.getLongitude() != null)
						&& (tmp.getLatitude() != null)){
					// add every record that has a DATE and a CITY (LONGITUDE, LATITUDE)
					// DATE and LONGITUDE and LATITUDE is used as a primary key (to identify dublicates)
					
					// generate key with date, longitude and latitude
					long key = Long.valueOf(String.valueOf(tmp.getLongitude()).replaceAll(".", "")
							+ String.valueOf(tmp.getLatitude()).replaceAll(".", "")
							+String.valueOf(tmp.getDate().getTime()));
					
					// assign key to object
					tmp.setID(key);
					
					// save to database
					ofy().save().entity(tmp).now();
				}
			}
	}
}
