package com.in.weather.parser;

import com.in.weather.parser.impl.OpenWeatherMapParser;

/**
 * A factory for creating Parse objects.
 */
public class ParseFactory {

	/**
	 * Gets the parses the factory.
	 *
	 * @param parser the parser
	 * @return the parses the factory
	 */
	public static IWeatherDataParser getParseFactory(String parser) {
		if (parser.equalsIgnoreCase("openweathermap")) {
			return new OpenWeatherMapParser();
		} else {
			System.out.println("parser not supported");
		}
		return null;
	}
}
