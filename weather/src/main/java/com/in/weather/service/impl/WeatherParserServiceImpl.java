package com.in.weather.service.impl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.in.weather.parser.IWeatherDataParser;
import com.in.weather.parser.ParseFactory;
import com.in.weather.service.IWeatherParser;
import com.in.weather.wrapper.main.WeatherData;

@Service
public class WeatherParserServiceImpl implements IWeatherParser {

	@Override
	public WeatherData parse(String parseType, String data) throws JsonMappingException, JsonProcessingException {
		IWeatherDataParser parseFactory = ParseFactory.getParseFactory(parseType);
		WeatherData parse = parseFactory.parse(data);
		return parse;
	}
}
