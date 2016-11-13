package ch.uzh.seproject.server.dataaccesslayer;

import com.google.gwt.i18n.client.DateTimeFormat;
// RPC
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
// objectify
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
// datastructures
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public void readServerFiles() {
		BufferedReader br = null;
		try {
			br= new BufferedReader(new FileReader(getServletContext().getRealPath("res/glob.csv")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Parser p = new Parser();
		List<WeatherRecord> wR =p.readFile(br);
		try {
			setWeatherData(wR);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
