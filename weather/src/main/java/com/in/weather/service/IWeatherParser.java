package com.in.weather.service;

import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.wrapper.main.WeatherData;

/**
 * The Interface IWeatherParser.
 */
public interface IWeatherParser {

	/**
	 * Parses the.
	 *
	 * @param parseType the parse type
	 * @param data      the data
	 * @return the weather data
	 * @throws ParseDataException the parse data exception
	 */
	WeatherData parse(String parseType, String data) throws ParseDataException;

}
