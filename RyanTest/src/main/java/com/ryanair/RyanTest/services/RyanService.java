package com.ryanair.RyanTest.services;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanair.RyanTest.entities.Route;
import com.ryanair.RyanTest.entities.Schedule;

@Service
public class RyanService implements IRyanService {

	private static final String urlRoute = "https://api.ryanair.com/core/3/routes/";
	private static final String urlSchedule = "https://api.ryanair.com/timetable/3/schedules/%1$s/%2$s/years/%3$d/months/%4$d";

	@Override
	public List<Route> getListRoute() throws IOException {

		HttpURLConnection connection;
		URL url;
		CharArrayWriter buffer;
		CharArrayReader reader;
		TypeReference typeLista;
		ObjectMapper mapper;
		BufferedReader br;
		String content;
		List<Route> listRoute = null;

		url = new URL(urlRoute);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.addRequestProperty("Content-Type", "application/json");
		connection.setDoInput(true);
		buffer = new CharArrayWriter();
		if (connection.getResponseCode() == 200) {
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((content = br.readLine()) != null) {
				buffer.write(content);
			}
			br.close();

			typeLista = new TypeReference<List<Route>>() {};
			mapper = new ObjectMapper();
			reader = new CharArrayReader(buffer.toCharArray());
			listRoute = mapper.readValue(reader, typeLista);

		}

		return listRoute;
	}

	@Override
	public Schedule getSchedule(String departure, String arrival, int year, int month) throws IOException{
		HttpURLConnection connection;
		URL url;
		CharArrayWriter buffer;
		CharArrayReader reader;
		ObjectMapper mapper;
		Schedule schedule = null;
		BufferedReader br;
		String content;
		url = new URL(String.format(urlSchedule, departure, arrival, year, month));
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.addRequestProperty("Content-Type", "application/json");
		connection.setDoInput(true);
		if (connection.getResponseCode() == 200) {
			buffer = new CharArrayWriter();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((content = br.readLine()) != null) {
				buffer.write(content);
			}
			br.close();

			mapper = new ObjectMapper();
			reader = new CharArrayReader(buffer.toCharArray());
			schedule = mapper.readValue(reader, Schedule.class);
		}

		return schedule;
	}
	
}
