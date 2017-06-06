package com.ryanair.RyanTest.entities;

public class Route {
	
	private String airportFrom;
	private String airportTo;
	private String connectingAirport;
	private boolean isSeasonalRoute;
	private boolean isNewRoute;
	
	public Route(){super();}
	
	public Route(String airportFrom, String airportTo, String connectingAirport,boolean isSeasonalRoute, boolean isNewRoute) {
		super();
		this.airportFrom = airportFrom;
		this.airportTo = airportTo;
		this.connectingAirport=connectingAirport;
		this.isSeasonalRoute = isSeasonalRoute;
		this.isNewRoute = isNewRoute;
	}

	public String getAirportFrom() {
		return airportFrom;
	}

	public String getAirportTo() {
		return airportTo;
	}

	public String getConnectingAirport() {
		return connectingAirport;
	}

	public boolean isSeasonalRoute() {
		return isSeasonalRoute;
	}

	public boolean isNewRoute() {
		return isNewRoute;
	}
	
	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}
	
	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}
	
	public void setSeasonalRoute(boolean isSeasonalRoute) {
		this.isSeasonalRoute = isSeasonalRoute;
	}

	public void setNewRoute(boolean isNewRoute) {
		this.isNewRoute = isNewRoute;
	}

	public void setConnectingAirport(String connectingAirport) {
		this.connectingAirport = connectingAirport;
	}

	@Override
	public String toString() {
		return "Route [airportFrom=" + airportFrom + ", airportTo=" + airportTo + ", connectingAirport="
				+ connectingAirport + ", isSeasonalRoute=" + isSeasonalRoute + ", isNewRoute=" + isNewRoute + "]";
	}

	
}
