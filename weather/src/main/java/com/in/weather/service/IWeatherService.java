package com.in.weather.service;

import java.text.ParseException;
import java.util.List;

import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.rule.exceptions.RuleOperationKeyNotValidException;
import com.in.weather.rule.exceptions.RuleOperationNotValidException;
import com.in.weather.service.exceptions.ParamTypeNotValidException;
import com.in.weather.wrapper.main.ChartRecord;
import com.in.weather.wrapper.main.WeatherData;

/**
 * The Interface IWeatherService.
 */
public interface IWeatherService {

	/**
	 * Gets the weather detail.
	 *
	 * @param city the city
	 * @return the weather detail
	 * @throws ParseDataException                the parse data exception
	 * @throws ParseException                    the parse exception
	 * @throws RuleOperationKeyNotValidException the rule operation key not valid
	 *                                           exception
	 * @throws RuleOperationNotValidException    the rule operation not valid
	 *                                           exception
	 * @throws ParamTypeNotValidException        the param type not valid exception
	 */
	WeatherData getWeatherDetail(String city) throws ParseDataException, ParseException,
			RuleOperationKeyNotValidException, RuleOperationNotValidException, ParamTypeNotValidException;

	/**
	 * Gets the weather hourly detail.
	 *
	 * @param city the city
	 * @param date the date
	 * @return the weather hourly detail
	 * @throws ParseDataException         the parse data exception
	 * @throws ParamTypeNotValidException the param type not valid exception
	 */
	List<ChartRecord> getWeatherHourlyDetail(String city, Long date)
			throws ParseDataException, ParamTypeNotValidException;

}
