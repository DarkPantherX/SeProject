package ch.uzh.seproject.client.dataaccesslayer;

//serializable
import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Filter implements Serializable{
	// prevent stored objects from being deserialized on fetch
	private static final long serialVersionUID = 1L;
	// @Id needed, used for generating database-key
	private @Id Long id;
	
	private String query;
	private Serializable value;
	
	/**
	 * GWT needs a default (empty) constructor !!
	 */
	public Filter(){
	}
	
	/**
	 * Use this constructor!
	 */
	public Filter(String query, Serializable value)
	{
		this.query = query;
		this.value = value;
	}

	/**
	 * getters and setters
	 */
	public String getQuery() {
		return query;
	}
	public Serializable getValue() {
		return value;
	}
}
