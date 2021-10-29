package com.in.weather.rest;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.rule.exceptions.RuleOperationKeyNotValidException;
import com.in.weather.rule.exceptions.RuleOperationNotValidException;
import com.in.weather.service.exceptions.ParamTypeNotValidException;
import com.in.weather.wrapper.main.ChartRecord;
import com.in.weather.wrapper.main.WeatherData;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Interface IWeatherRest.
 */
@ResponseBody
public interface IWeatherRest {

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
	@ApiOperation(value = "Get Weather Detail By City", response = WeatherData.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorised"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(path = "detail")
	public ResponseEntity<Object> weatherDetail(@RequestParam(required = true, name = "city") String city)
			throws ParseDataException, ParseException, RuleOperationKeyNotValidException,
			RuleOperationNotValidException, ParamTypeNotValidException;

	/**
	 * Weather detail.
	 *
	 * @param city the city
	 * @param date the date
	 * @return the response entity
	 * @throws ParseDataException         the parse data exception
	 * @throws ParamTypeNotValidException the param type not valid exception
	 */
	@ApiOperation(value = "Get Weather Temperature & Date Mapping, at an interval of 3 hours", response = ChartRecord.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorised"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(path = "detail/qhourly")
	ResponseEntity<Object> weatherDetail(@RequestParam(required = true, name = "city") String city,
			@RequestParam(required = true, name = "date") Long date)
			throws ParseDataException, ParamTypeNotValidException;
}
