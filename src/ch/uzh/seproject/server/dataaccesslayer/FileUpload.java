package ch.uzh.seproject.server.dataaccesslayer;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import org.apache.commons.fileupload.FileItemIterator; 
import org.apache.commons.fileupload.FileItemStream; 
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ch.uzh.seproject.client.dataaccesslayer.ServerException;
import ch.uzh.seproject.client.dataaccesslayer.WeatherRecord; 


//Code taken form Stackoverflow http://stackoverflow.com/questions/1111130/basic-file-upload-in-gwt and modified to meet our needs

public class FileUpload extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        ServletFileUpload upload = new ServletFileUpload();

        try{
            FileItemIterator iter = upload.getItemIterator(request);

            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                InputStream stream = item.openStream();

            	BufferedReader br = null;
        		br= new BufferedReader(new InputStreamReader(stream));
        		
        		Parser p = new Parser();
        		List<WeatherRecord> wR =p.readFile(br);
        	
        		
    			setObjectId(wR);
        		
        	}
 
                
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

	private void setObjectId(List<WeatherRecord> wR) throws ServerException {
		for (WeatherRecord tmp : wR) {
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
				throw new ServerException("Mistake reading file");
			}
		}
	}
        
}