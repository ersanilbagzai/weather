package com.in.weather.wrapper.main;

import lombok.Data;

@Data
public class WeatherInfo {

	private Double temp;

	private Double feelsLike;

	private Double tempMin;

	private Double tempMax;

	private Double pressure;

	private Double seaLevel;

	private Double grndLevel;

	private Double humidity;

	private Double tempKf;
	
	private String suggestion;

}
