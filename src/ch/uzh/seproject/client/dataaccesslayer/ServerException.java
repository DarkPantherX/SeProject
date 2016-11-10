package ch.uzh.seproject.client.dataaccesslayer;

import java.io.Serializable;

public class ServerException extends Exception implements Serializable {
	// prevent stored objects from being deserialized on fetch
	private static final long serialVersionUID = 1L;
	
	public ServerException(){
	}
	
	public ServerException(String message){
	      super(message);
	}
}