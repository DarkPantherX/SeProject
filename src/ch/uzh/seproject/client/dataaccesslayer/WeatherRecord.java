package ch.uzh.seproject.client.dataaccesslayer;

// serializable
import java.io.Serializable;
// general
import java.util.Date;
// objectify
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

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
	 * Serializable doesn't allow a constructor !!
	 */
	
	// e.g. 1849-01-01
	private Date date;
	// e.g. 26.704
    private Double averageTermperature;
    // e.g. 1.435
    private Double averageTemperatureUncertainty;
    // e.g. Abidjan
    private String city;
    // e.g. Côte D'Ivoire
    private String country;
    // e.g. 5.63N
    private Double Latitude;
    // e.g. 3.23W
    private Double Longitude;
    
    
    // get/set date
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	// get/set average temperature
	public Double getAverageTermperature() {
		return averageTermperature;
	}
	public void setAverageTermperature(Double averageTermperature) {
		this.averageTermperature = averageTermperature;
	}
	
	// get/set average temperature unvertainty
	public Double getAverageTemperatureUncertainty() {
		return averageTemperatureUncertainty;
	}
	public void setAverageTemperatureUncertainty(Double averageTemperatureUncertainty) {
		this.averageTemperatureUncertainty = averageTemperatureUncertainty;
	}
	
	// get/set city
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	// get/set country
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	// get/set latitude
	public Double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	
	// get/set longitude
	public Double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}  
	
	// set id
	public void setID(long id) {
		this.id = id;
	}    
}