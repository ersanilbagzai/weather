package com.in.weather.parser;

import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.wrapper.main.WeatherData;

/**
 * The Interface IWeatherDataParser.
 */
public interface IWeatherDataParser {

	/**
	 * Parses the.
	 *
	 * @param data the data
	 * @return the weather data
	 * @throws ParseDataException the parse data exception
	 */
	WeatherData parse(String data) throws ParseDataException;
}
