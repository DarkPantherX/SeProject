package ch.uzh.seproject.server.dataaccesslayer;

// RPC
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
// objectify
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

// datastructures
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// general
import ch.uzh.seproject.client.dataaccesslayer.DataAccessService;
import ch.uzh.seproject.client.dataaccesslayer.ServerException;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;
import java.util.Date;

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
	 * Returns a serializable list of all WeatherRecords ordered by date (newest first)
	 */
	@Override
	public List<WeatherRecord> getWeatherData() {
		
		//fetched data from database
		List<WeatherRecord> fetched = ofy().load().type(WeatherRecord.class).order("date").list();
		Logger logger = Logger.getLogger("");
		logger.log(Level.SEVERE, Integer.toString(fetched.size()));
		// fetched data needs to be copied to a new list, because the fetched-list is not serializable
		List<WeatherRecord> result = new ArrayList<WeatherRecord>();
		
		for (WeatherRecord tmp : fetched) {
			// copy to new list
			result.add(tmp);
		}
		
		return result;
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
	@Override
	public List<WeatherRecord> getWeatherData(Date dateFrom, Date dateTo) {
		List<WeatherRecord> fetched = null;
		
		// if no date is set, return whole dataset
		if((dateFrom == null) && (dateTo == null)) return getWeatherData();
		else if((dateFrom != null) && (dateTo == null)) {
			//fetched data from database with only dateFrom
			fetched = ofy().load().type(WeatherRecord.class).filter("date >=", dateFrom).order("date").list();
		}else if((dateFrom == null) && (dateTo != null)) 
		{
			//fetched data from database with only dateTo
			fetched = ofy().load().type(WeatherRecord.class).filter("date <=", dateTo).order("date").list();
		}else if((dateFrom != null) && (dateTo != null)) 
		{
			//fetched data from database both dates set
			fetched = ofy().load().type(WeatherRecord.class).filter("date >=", dateFrom).filter("date <=", dateTo).order("date").list();
		}
		
		// fetched data needs to be copied to a new list, because the fetched-list is not serializable
		List<WeatherRecord> result = new ArrayList<WeatherRecord>();
		
		if(fetched != null)
		{
			for (WeatherRecord tmp : fetched) {
				// copy to new list
				result.add(tmp);
			}
		}
		
		return result;
	}
	

	/**
	 * Save a serializable list of WeatherRecords to the database
	 * Date, City, Longitude, Latitude must be set. Those are used to generate a primary key.
	 */
	@Override
	public void setWeatherData(List<WeatherRecord> weatherData) throws ServerException {		
		String exceptionText = "Because some input-data is invalid, not all records could be saved in the database!";
		
		if(weatherData == null) throw new ServerException(exceptionText);
		else
		{
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
				}else
				{
					throw new ServerException(exceptionText);
				}
			}
		}
	}
}
