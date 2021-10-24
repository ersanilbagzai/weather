package com.in.weather.wrapper.openweather;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.in.weather.wrapper.main.Weather;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherOpenWeather {

	private Long id;

	private String main;

	private String description;

	private String icon;

	public static Weather getGenericWeatherOpenWeather(WeatherOpenWeather input) {
		Weather wd = new Weather();
		wd.setId(input.getId());
		wd.setMain(input.getMain());
		wd.setDescription(input.getDescription());
		wd.setIcon(input.getIcon());
		return wd;
	}

	public static List<Weather> getGenericWeatherOpenWeather(List<WeatherOpenWeather> inputData) {
		List<Weather> weatherList = new ArrayList<>();
		for (WeatherOpenWeather input : inputData) {
			weatherList.add(getGenericWeatherOpenWeather(input));
		}
		return weatherList;
	}
}
