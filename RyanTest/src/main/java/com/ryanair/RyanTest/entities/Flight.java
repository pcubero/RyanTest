package com.ryanair.RyanTest.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Flight {
	
	private int number;
	private LocalTime departureTime;
	private LocalTime arrivalTime;

	public Flight() {super();}

	public Flight(int number, String departureTime, String arrivalTime) {
		super();
		this.number = number;
		this.departureTime = LocalTime.parse(departureTime) ;
		this.arrivalTime = LocalTime.parse(arrivalTime);
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = LocalTime.parse(departureTime);
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = LocalTime.parse(arrivalTime);
	}
	
	public LocalDateTime getDepartureDateTime(LocalDate ld){
		return LocalDateTime.of(ld,departureTime);
	}
	
	public LocalDateTime getArrivalDateTime(LocalDate ld){
		return LocalDateTime.of(ld,arrivalTime);
	}

	
	@Override
	public String toString() {
		return "Flight [number=" + number + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + "]";
	}


}
