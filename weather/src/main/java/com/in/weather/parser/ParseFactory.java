package com.in.weather.parser;

import com.in.weather.parser.impl.OpenWeatherMapParser;

public class ParseFactory {

	public static IWeatherDataParser getParseFactory(String parser) {
		if(parser.equalsIgnoreCase("openweathermap")) {
			return new OpenWeatherMapParser();
		}else {
			System.out.println("parser not supported");
		}
		return null;
	}
}
