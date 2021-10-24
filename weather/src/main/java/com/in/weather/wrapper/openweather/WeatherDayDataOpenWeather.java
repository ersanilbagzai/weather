package com.in.weather.wrapper.openweather;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.in.weather.wrapper.main.WeatherDayData;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDayDataOpenWeather {

	private Long dt;

	private WeatherInfoOpenWeather main;

	private List<WeatherOpenWeather> weather;

	private CloudsOpenWeather clouds;

	private WindOpenWeather wind;

	private Integer visibility;

	public static WeatherDayData getGenericWeatherDayDataOpenWeather(WeatherDayDataOpenWeather input) {
		WeatherDayData wd = new WeatherDayData();
		wd.setDate(input.getDt());
		wd.setWeatherInfo(WeatherInfoOpenWeather.getGenericWeatherInfoOpenWeather(input.getMain()));
		wd.setWeather(WeatherOpenWeather.getGenericWeatherOpenWeather(input.getWeather()));
		wd.setClouds(CloudsOpenWeather.getGenericCloudsOpenWeather(input.getClouds()));
		wd.setWind(WindOpenWeather.getGenericWindOpenWeather(input.getWind()));
		wd.setVisibility(input.getVisibility());
		return wd;
	}

	public static List<WeatherDayData> getGenericWeatherDayDataOpenWeather(List<WeatherDayDataOpenWeather> inputData) {
		List<WeatherDayData> wd = new ArrayList<WeatherDayData>();
		for (WeatherDayDataOpenWeather input : inputData) {
			wd.add(getGenericWeatherDayDataOpenWeather(input));
		}
		return wd;
	}

}
