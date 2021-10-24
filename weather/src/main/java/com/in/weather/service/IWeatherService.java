package com.in.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.in.weather.wrapper.main.WeatherData;

public interface IWeatherService {

	WeatherData getWeatherDetail(String city) throws JsonMappingException, JsonProcessingException;

}
