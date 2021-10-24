package com.in.weather.wrapper.main;

import java.util.List;

import lombok.Data;

@Data
public class WeatherDayData {

	private Long date;
	
	private WeatherInfo weatherInfo;
	
	private List<Weather> weather;
	
	private Clouds clouds;
	
	private Wind wind;
	
	private Integer visibility;
	
}
