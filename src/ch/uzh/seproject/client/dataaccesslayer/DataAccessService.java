package ch.uzh.seproject.client.dataaccesslayer;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("dataAccess")
public interface DataAccessService extends RemoteService {
	/**
	 * More details are available in the implementation (DataAccessServiceImpl)
	 */
	public List<WeatherRecord> getWeatherData();
	public void setWeatherData(List<WeatherRecord> weatherData) throws ServerException;
	public List<WeatherRecord> getWeatherData(Date dateFrom, Date dateTo);
	public void readServerFiles();
}
