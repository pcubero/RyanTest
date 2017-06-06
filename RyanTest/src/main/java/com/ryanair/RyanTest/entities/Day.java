package com.ryanair.RyanTest.entities;

import java.util.List;

public class Day {
	
	private int day;
	private List<Flight> flights;
	public Day(){super();}
	public Day(int day, List<Flight> flights) {
		super();
		this.day = day;
		this.flights = flights;
	}
	
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public List<Flight> getFlights() {
		return flights;
	}
	
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
	@Override
	public String toString() {
		return "Day [day=" + day + ", flights=" + flights + "]";
	}

	

}
