package com.ryanair.RyanTest.entities;

import java.time.LocalDateTime;

public class Leg {
	
	private String departureAirport;
	private String arrivalAirport;
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;
	
	public Leg(){}
	
	public Leg(String departureAirport, String arrivalAirport, String departureDateTime,
			String arrivalDateTime) {
		
		this(departureAirport,arrivalAirport,LocalDateTime.parse(departureDateTime),LocalDateTime.parse(arrivalDateTime));
	}
	
	public Leg(String departureAirport, String arrivalAirport, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime) {
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public String getDepartureDateTime() {
		return departureDateTime.toString();
	}

	public void setDepartureDateTime(LocalDateTime departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public String getArrivalDateTime() {
		return arrivalDateTime.toString();
	}

	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	@Override
	public String toString() {
		return "Leg [departureAirport=" + departureAirport + ", arrivalAirport=" + arrivalAirport
				+ ", departureDateTime=" + departureDateTime + ", arrivalDateTime=" + arrivalDateTime + "]";
	}

	

	
	
}
