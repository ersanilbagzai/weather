package com.in.weather.wrapper.openweather;

import com.in.weather.wrapper.main.City;
import com.in.weather.wrapper.main.WeatherData;

import lombok.Data;

@Data
public class CityOpenWeather {

	private Long id;
	
	private String name;
	
	private CoordinatesOpenWeather coord;
	
	private String country;
	
	private Integer population;
	
	private Integer timezone;
	
	private Long sunrise;
	
	private Long sunset;
	
	public static City getGenericCityOpenWeather(CityOpenWeather input) {
		City city = new City();
		city.setId(input.getId());
		city.setName(input.getName());
		city.setCoord(CoordinatesOpenWeather.getGenericCoordinatesOpenWeather(input.getCoord()));
		city.setCountry(input.getCountry());
		city.setPopulation(input.getPopulation());
		city.setTimezone(input.getTimezone());
		city.setSunriseTime(input.getSunrise());
		city.setSunsetTime(input.getSunset());
		
		return city;
	}
}
