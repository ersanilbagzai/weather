package com.in.weather.wrapper.main;

import java.util.List;

import lombok.Data;

@Data
public class WeatherData {

	private List<WeatherDayData> weatherDayData;
	
	private City city;
	
}
