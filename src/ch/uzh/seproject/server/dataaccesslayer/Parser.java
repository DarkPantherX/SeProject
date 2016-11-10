package ch.uzh.seproject.server.dataaccesslayer;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;

import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;


public class Parser {
public List<WeatherRecord> datas = new ArrayList<WeatherRecord>();
	
	

	
	
	public List<WeatherRecord> readFile(BufferedReader br){
	String line;
		boolean read=false;
		try {
			while ((line = br.readLine()) != null) {
				if(read){
				String[] data=line.split(",");
				WeatherRecord d = new WeatherRecord();
				d.setDate((Date) DateTimeFormat.getFormat("yyyy-MM-dd").parse(data[0]));
				d.setAverageTermperature(Double.parseDouble(data[1]));
				d.setAverageTemperatureUncertainty(Double.parseDouble(data[2]));
				d.setCity(data[3]);
				d.setCountry(data[4]);
				datas.add(d);
				}else{
					read=true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;
		
	}
	


		
		


	
	
	
}
