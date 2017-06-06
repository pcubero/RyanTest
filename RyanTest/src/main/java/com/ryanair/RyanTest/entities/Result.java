package com.ryanair.RyanTest.entities;

import java.util.List;

public class Result {
	
	private int stops;
	private List<Leg> legs;
	
	public Result(){super();}
	
	public Result(int stops, List<Leg> legs) {
		super();
		this.stops = stops;
		this.legs = legs;
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public List<Leg> getLegs() {
		return legs;
	}

	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}

	@Override
	public String toString() {
		return "Result [stops=" + stops + ", legs=" + legs + "]";
	}
	
	
	

}
