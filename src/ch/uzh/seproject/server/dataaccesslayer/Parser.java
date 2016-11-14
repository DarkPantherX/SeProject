package ch.uzh.seproject.server.dataaccesslayer;


import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord;


public class Parser {
public List<WeatherRecord> datas = new ArrayList<WeatherRecord>();
	
	

	
	/*
	 * @pre br!=null
	 * @post !datas.empty()
	 * 
	 */
	public List<WeatherRecord> readFile(BufferedReader br){
	String line;
		boolean read=false;
		try {
			while ((line = br.readLine()) != null) {
				if(read){
					if(!line.trim().equals("")){
						String[] data=line.split(",");
						
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date date = null;
						try {
							date = (Date) df.parse(data[0]);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							System.out.println("Date has wrong format, date is empty");
						}
						Double avgTemp = Double.parseDouble(data[1]);
						Double avgTempUnc = (Double.parseDouble(data[2]));
						String city = data[3];
						String country = data[4];
						Double latitude = 1.0d;
						Double longitude = 1.0d;
				
						WeatherRecord d = new WeatherRecord(date, avgTemp, avgTempUnc, city, country, latitude, longitude);
	
						datas.add(d);
				}
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
