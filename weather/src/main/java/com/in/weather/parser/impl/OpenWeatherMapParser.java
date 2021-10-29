package com.in.weather.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in.weather.parser.IWeatherDataParser;
import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.wrapper.main.WeatherData;
import com.in.weather.wrapper.openweather.WeatherDataOpenWeather;

/**
 * The Class OpenWeatherMapParser.
 */
public class OpenWeatherMapParser implements IWeatherDataParser {

	/**
	 * Parses the.
	 *
	 * @param data the data
	 * @return the weather data
	 * @throws ParseDataException the parse data exception
	 */
	@Override
	public WeatherData parse(String data) throws ParseDataException {
		WeatherDataOpenWeather weather = new WeatherDataOpenWeather();

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			weather = objectMapper.readValue(data, new TypeReference<WeatherDataOpenWeather>() {
			});
		} catch (JsonProcessingException e) {
			throw new ParseDataException();
		}

		return WeatherDataOpenWeather.getGenericWeatherData(weather);
	}

}
