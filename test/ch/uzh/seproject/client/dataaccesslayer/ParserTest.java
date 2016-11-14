package ch.uzh.seproject.client.dataaccesslayer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;

import ch.uzh.seproject.server.dataaccesslayer.Parser;


//We test the behavior of the parser with a fake .csv file, to check if everything runs smoothly
public class ParserTest {

	@Test
	public void testFileReader() {
		
		//Null Buffered Readerobject
		BufferedReader br = null;
		//Load File into the buffered Reader
		br= new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("globtest.csv")));
		//New Parser Object to parse the file
		Parser p = new Parser();
		//reading the data from the file into a List
		List<WeatherRecord> wR =p.readFile(br);
		//Check if the City of the first object ist the correct one
		assertEquals(wR.get(0).getCity(), "Abidjan");
		//Check if the file size is right
		assertTrue(wR.size()==3);
		
	}

	@Test
	public void testFileReaderWithNull() {
		
		//New Parser Object to parse the file
		Parser p = new Parser();
		//reading the data from the file into a List
		try{
		//Try reading null buffred reader
		p.readFile(null);
		fail();
		}catch(NullPointerException ex){}
		
	}
	
	@Test
	public void testFileReaderWithWrongDate() {
		
		//Null Buffered Readerobject
		BufferedReader br = null;
		//Load File into the buffered Reader with wrong date format, catched
		br= new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("globtest_fail.csv")));
		//New Parser Object to parse the file
		Parser p = new Parser();
		//reading the data from the file into a List
		List<WeatherRecord> wR =p.readFile(br);
		//Check if the City of the first object ist the correct one
		assertEquals(wR.get(0).getCity(), "Abidjan");
		//Check if date is not in
		assertTrue(wR.get(0).getDate()==null);
	}
	
	@Test
	public void testFileReaderWithWrongTooLittleInput() {
		
		//Null Buffered Readerobject
		BufferedReader br = null;
		//Load File into the buffered Reader with wrong date format, catched
		br= new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("globtest_fail_lessarguments.csv")));
		//New Parser Object to parse the file
		Parser p = new Parser();
		//reading the data from the file into a List
		List<WeatherRecord> wR = null;
		try{
		 wR =p.readFile(br);
		 fail();
		}catch(ArrayIndexOutOfBoundsException ex){
			assertTrue(wR==null);
		}
		//Check if the file size is right
		
	}
}
