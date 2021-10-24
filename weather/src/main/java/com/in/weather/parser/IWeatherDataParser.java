package com.in.weather.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.in.weather.wrapper.main.WeatherData;

public interface IWeatherDataParser {

	WeatherData parse(String data) throws JsonMappingException, JsonProcessingException;
}
