package ch.uzh.seproject.client.dataaccesslayer;

// serializable
import java.io.Serializable;
// general
import java.util.Date;
// objectify
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The class WeatherRecord is a abstraction for 1 row of the .csv file 
 */
@Entity
public class WeatherRecord implements Serializable{
	// prevent stored objects from being deserialized on fetch
	private static final long serialVersionUID = 1L;
	// @Id needed, used for generating database-key
	private @Id Long id;
	
	/**
	 * GWT needs a default (empty) constructor !!
	 */
	public WeatherRecord(){
		
	}
	
	/**
	 * second constructor !!
	 */
	public WeatherRecord(Date date, Double averageTermperature, Double averageTemperatureUncertainty, 
			String city, String country, Double latitude, Double longitude){
		// set default values
		this.date = date;
		this.averageTermperature = averageTermperature;
		this.averageTemperatureUncertainty = averageTemperatureUncertainty;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	// e.g. 1849-01-01
	private @Index Date date;
	// e.g. 26.704
    private @Index Double averageTermperature;
    // e.g. 1.435
    private @Index Double averageTemperatureUncertainty;
    // e.g. Abidjan
    private @Index String city;
    // e.g. Côte D'Ivoire
    private @Index String country;
    // e.g. 5.63N
    private @Index Double latitude;
    // e.g. 3.23W
    private @Index Double longitude;
    
    
	/**
	 * getters and setters
	 */
    
	public Date getDate() {
		return date;
	}
	public Double getAverageTermperature() {
		return averageTermperature;
	}
	public Double getAverageTemperatureUncertainty() {
		return averageTemperatureUncertainty;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public Double getLatitude() {
		return latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setID(long id) {
		this.id = id;
	}
	public long getID() {
		return id;
	}
	
	@Override
	public String toString(){
		return "Date: "+ date+ ", avg Temp: "+averageTermperature +", avg Temp uncert.: "+averageTemperatureUncertainty+", city: " + city + ", country: "+country;	
	}
}