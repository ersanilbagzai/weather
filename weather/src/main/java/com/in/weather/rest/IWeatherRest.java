package com.in.weather.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ResponseBody
public interface IWeatherRest {

	@GetMapping(path = "detail")
	public ResponseEntity weatherDetail(@RequestParam(required = true, name = "city") String city) throws JsonMappingException, JsonProcessingException;
}
