package com.in.weather.wrapper.openweather;

import com.in.weather.wrapper.main.Coordinates;

import lombok.Data;

@Data
public class CoordinatesOpenWeather {

	private Double lat;
	
	private Double lon;
	
	public static Coordinates getGenericCoordinatesOpenWeather(CoordinatesOpenWeather input) {
		Coordinates coord = new Coordinates();
		coord.setLat(input.getLat());
		coord.setLon(input.getLon());
		return coord;
	}
}
