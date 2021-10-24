package com.in.weather.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.in.weather.rest.IWeatherRest;
import com.in.weather.service.IWeatherService;

@Primary
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "weather")
@CrossOrigin
public class WeatherRestImpl implements IWeatherRest {

	@Autowired
	private IWeatherService iWeatherService;

	@Autowired
	private Environment env;

	@Override
	public ResponseEntity weatherDetail(String city) throws JsonMappingException, JsonProcessingException {
		return new ResponseEntity(iWeatherService.getWeatherDetail(city), HttpStatus.OK);
	}

}
