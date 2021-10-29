package com.in.weather.rest.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.rest.IWeatherRest;
import com.in.weather.rule.exceptions.RuleOperationKeyNotValidException;
import com.in.weather.rule.exceptions.RuleOperationNotValidException;
import com.in.weather.service.IWeatherService;
import com.in.weather.service.exceptions.ParamTypeNotValidException;

/**
 * The Class WeatherRestImpl.
 */
@Primary
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "weather")
@CrossOrigin
public class WeatherRestImpl implements IWeatherRest {

	/** The i weather service. */
	@Autowired
	private IWeatherService iWeatherService;

	/** The env. */
	@Autowired
	private Environment env;

	/**
	 * Weather detail.
	 *
	 * @param city the city
	 * @return the response entity
	 * @throws ParseDataException                the parse data exception
	 * @throws ParseException                    the parse exception
	 * @throws RuleOperationKeyNotValidException the rule operation key not valid
	 *                                           exception
	 * @throws RuleOperationNotValidException    the rule operation not valid
	 *                                           exception
	 * @throws ParamTypeNotValidException        the param type not valid exception
	 */
	@Override
	public ResponseEntity<Object> weatherDetail(String city) throws ParseDataException, ParseException,
			RuleOperationKeyNotValidException, RuleOperationNotValidException, ParamTypeNotValidException {
		return new ResponseEntity(iWeatherService.getWeatherDetail(city), HttpStatus.OK);
	}

	/**
	 * Weather detail.
	 *
	 * @param city the city
	 * @param date the date
	 * @return the response entity
	 * @throws ParseDataException         the parse data exception
	 * @throws ParamTypeNotValidException the param type not valid exception
	 */
	@Override
	public ResponseEntity<Object> weatherDetail(String city, Long date)
			throws ParseDataException, ParamTypeNotValidException {
		return new ResponseEntity(iWeatherService.getWeatherHourlyDetail(city, date), HttpStatus.OK);
	}
}
