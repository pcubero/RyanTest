package com.ryanair.RyanTest.controllers;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanair.RyanTest.entities.Day;
import com.ryanair.RyanTest.entities.Flight;
import com.ryanair.RyanTest.entities.Leg;
import com.ryanair.RyanTest.entities.Result;
import com.ryanair.RyanTest.entities.Route;
import com.ryanair.RyanTest.entities.Schedule;
import com.ryanair.RyanTest.services.IRyanService;

@Controller
@RequestMapping("RyanTest")
public class ControllerFlights {

	@Autowired
	private IRyanService rservice;

	@RequestMapping(value = "/interconnections", method = RequestMethod.GET)
	@ResponseBody
	private String getFlights(@RequestParam("departure") String departure, @RequestParam("arrival") String arrival,
			@RequestParam("departureDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime departureDateTime,
			@RequestParam("arrivalDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime arrivalDateTime) {

		List<Result> listResult = new ArrayList<Result>();
		List<Route> listAll;
		CharArrayWriter buffer = new CharArrayWriter();
		ObjectMapper mapper = new ObjectMapper();

		try {

			if (departureDateTime.compareTo(arrivalDateTime) < 0) {

				listAll = rservice.getListRoute();

				// DIRECT FLIGHTS
				listResult.addAll(getDirectFlights(listAll, departure, arrival, departureDateTime, arrivalDateTime));

				// INTERCONNECTED FLIGHTS
				listResult.addAll(
						getInterconnectedFlights(listAll, departure, arrival, departureDateTime, arrivalDateTime));
			}

			mapper.writeValue(buffer, listResult);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toString();

	}

	/**
	 * Get a list of direct flights
	 * 
	 * @param listAll
	 * @param departure
	 * @param arrival
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @return List<Result>
	 * @throws IOException
	 */

	private List<Result> getDirectFlights(List<Route> listAll, String departure, String arrival,
			LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) throws IOException {

		Schedule schedule;
		Day dayFlight;
		List<Result> listResult = new ArrayList<>();

		Route routeDirect = listAll.stream()
				.filter(route -> route.getAirportFrom().equals(departure) && route.getAirportTo().equals(arrival))
				.findFirst().orElse(null);

		if (routeDirect != null) {
			listAll.remove(routeDirect);

			for (LocalDate ini = departureDateTime.toLocalDate(); ini
					.compareTo(arrivalDateTime.toLocalDate()) <= 0; ini = ini.plusDays(1)) {

				LocalDate day = ini;
				schedule = rservice.getSchedule(departure, arrival, ini.getYear(), ini.getMonthValue());

				if (schedule != null) {
					dayFlight = schedule.getDays().stream().filter(d -> d.getDay() == day.getDayOfMonth()).findFirst()
							.orElse(null);

					if (dayFlight != null) {

						if (dayFlight.getDay() > departureDateTime.getDayOfMonth()
								&& dayFlight.getDay() < arrivalDateTime.getDayOfMonth()) {

						}

						List<Flight> listFlights = dayFlight.getFlights().stream()
								.filter(f -> f.getDepartureDateTime(day).compareTo(departureDateTime) >= 0
										&& f.getArrivalDateTime(day).compareTo(arrivalDateTime) <= 0)
								.collect(Collectors.toList());

						listResult.addAll(addFlightsDirect(listFlights, departure, arrival, ini));

					}
				}

			}
		}
		return listResult;

	}

	/**
	 * Get a list of flights with interconnection
	 * 
	 * @param listAll
	 * @param departure
	 * @param arrival
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @return List<Result>
	 * @throws IOException
	 */
	private List<Result> getInterconnectedFlights(List<Route> listAll, String departure, String arrival,
			LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) throws IOException {

		Route routeDirect = listAll.stream()
				.filter(route -> route.getAirportFrom().equals(departure) && route.getAirportTo().equals(arrival))
				.findFirst().orElse(null);

		if (routeDirect != null) {
			listAll.remove(routeDirect);
		}

		List<Route> listWithStop = listAll.stream().filter(route -> route.getAirportFrom().equals(departure))
				.collect(Collectors.toList());

		Route routeDest;
		Schedule scheDep = null, scheArr = null;
		Day dayArr, dayDep;
		List<Result> listResult = new ArrayList<>();

		Leg legDep, legArr;
		String airportIntercon;
		List<Flight> flightsArr, flightsDep;

		int monthOld;

		for (Route routeIni : listWithStop) {
			routeDest = listAll.stream()
					.filter(a -> a.getAirportFrom().equals(routeIni.getAirportTo()) && a.getAirportTo().equals(arrival))
					.findFirst().orElse(null);
			if (routeDest != null) {

				airportIntercon = routeIni.getAirportTo();

				monthOld = 0;
				for (LocalDate ini = departureDateTime.toLocalDate(); ini
						.compareTo(arrivalDateTime.toLocalDate()) <= 0; ini = ini.plusDays(1)) {

					LocalDate day = ini;
					if (day.getMonthValue() != monthOld) {
						monthOld = day.getMonthValue();
						scheDep = rservice.getSchedule(departure, airportIntercon, day.getYear(), day.getMonthValue());
						scheArr = rservice.getSchedule(airportIntercon, arrival, day.getYear(), day.getMonthValue());
					}

					if (scheDep != null && scheArr != null) {

						dayDep = scheDep.getDays().stream().filter(d -> d.getDay() == day.getDayOfMonth()).findFirst()
								.orElse(null);

						dayArr = scheArr.getDays().stream().filter(d -> d.getDay() == day.getDayOfMonth()).findFirst()
								.orElse(null);

						if (dayDep != null && dayArr != null) {

							flightsDep = dayDep.getFlights().stream()
									.filter(f -> f.getDepartureDateTime(day).compareTo(departureDateTime) >= 0
											&& f.getArrivalDateTime(day).compareTo(arrivalDateTime) < 0)
									.collect(Collectors.toList());

							for (Flight flightDep : flightsDep) {

								legDep = new Leg(departure, airportIntercon, flightDep.getDepartureDateTime(day),
										flightDep.getArrivalDateTime(day));

								// I have assumed that departure flight and
								// arrival flight are the same day
								flightsArr = dayArr.getFlights().stream()
										.filter(f -> f.getDepartureDateTime(day).compareTo(flightDep.getArrivalDateTime(day).plusHours(2)) >= 0
												  && f.getArrivalDateTime(day).compareTo(arrivalDateTime) <= 0)
										.collect(Collectors.toList());

								for (Flight flightArr : flightsArr) {
									legArr = new Leg(airportIntercon, arrival, flightArr.getDepartureDateTime(day),
											flightArr.getArrivalDateTime(day));
									listResult.add(new Result(1, Arrays.asList(legDep, legArr)));
								}
							}
						}

					}

				}
			}
		}

		return listResult;

	}

	private List<Result> addFlightsDirect(List<Flight> listFlight, String departure, String arrival, LocalDate date) {
		LocalDateTime dateFlightD, dateFlightA;
		List<Result> listRes = new ArrayList<>();

		for (Flight flight : listFlight) {
			dateFlightD = flight.getDepartureDateTime(date);
			dateFlightA = flight.getArrivalDateTime(date);
			listRes.add(new Result(0, Arrays.asList(new Leg(departure, arrival, dateFlightD, dateFlightA))));
		}
		return listRes;
	}

}