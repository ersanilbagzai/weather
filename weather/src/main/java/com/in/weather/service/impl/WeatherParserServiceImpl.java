package com.in.weather.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.in.weather.parser.IWeatherDataParser;
import com.in.weather.parser.ParseFactory;
import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.service.IWeatherParser;
import com.in.weather.wrapper.main.WeatherData;

/**
 * The Class WeatherParserServiceImpl.
 */
@Service
public class WeatherParserServiceImpl implements IWeatherParser {

	Logger logger = LoggerFactory.getLogger(WeatherParserServiceImpl.class);
	
	/**
	 * Parses the.
	 *
	 * @param parseType the parse type
	 * @param data      the data
	 * @return the weather data
	 * @throws ParseDataException the parse data exception
	 */
	@Override
	public WeatherData parse(String parseType, String data) throws ParseDataException {
		logger.trace("Parsing data, parseType - " + parseType);
		IWeatherDataParser parseFactory = ParseFactory.getParseFactory(parseType);
		WeatherData parse = parseFactory.parse(data);
		return parse;
	}
}
