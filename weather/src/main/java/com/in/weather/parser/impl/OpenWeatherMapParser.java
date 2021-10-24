package com.in.weather.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in.weather.parser.IWeatherDataParser;
import com.in.weather.wrapper.main.WeatherData;
import com.in.weather.wrapper.openweather.WeatherDataOpenWeather;

public class OpenWeatherMapParser implements IWeatherDataParser {

	@Override
	public WeatherData parse(String data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		WeatherDataOpenWeather weather = objectMapper.readValue(data, new TypeReference<WeatherDataOpenWeather>() {
		});
		return WeatherDataOpenWeather.getGenericWeatherData(weather);
	}

	
}
