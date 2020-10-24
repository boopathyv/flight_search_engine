package com.flight.searchengine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name= "FLIGHT_DETAILS") 
public class FlightDetails {

	@Id
	@Column
	private String id;
		
	@Column(name= "flight_name") 
    private String flightName;
	
	@Column
	private String source;
	
	@Column
	private String destination;
	
	@Column
	private long departure;
	
	@Column
	private long arrival;
	
	@Column
	private String offercode;
	
	@Column
	private int stops;
	
	@Column
	private long duration;
	
	@Column
	private int price;
	
	@Column
	private String cabin;
}
