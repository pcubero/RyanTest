package com.ryanair.RyanTest.services;

import java.io.IOException;
import java.util.List;

import com.ryanair.RyanTest.entities.Route;
import com.ryanair.RyanTest.entities.Schedule;

public interface IRyanService {
	List<Route> getListRoute() throws IOException;
	Schedule getSchedule(String departure, String arrival, int year, int month) throws IOException;
}
