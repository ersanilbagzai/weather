package com.in.weather.parser.mappers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.rule.exceptions.RuleOperationKeyNotValidException;
import com.in.weather.rule.exceptions.RuleOperationNotValidException;
import com.in.weather.service.exceptions.ParamTypeNotValidException;

/**
 * The Class WeatherExceptionMapper.
 */
@ControllerAdvice
public class WeatherExceptionMapper {

	/**
	 * Return parse data exception response.
	 *
	 * @param exception the exception
	 * @return the response entity
	 */
	@ExceptionHandler(value = ParseDataException.class)
	public ResponseEntity<Object> returnParseDataExceptionResponse(ParseDataException exception) {
		return new ResponseEntity("{\"Status\":\"Failure\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Return rule operation key not valie exception response.
	 *
	 * @param exception the exception
	 * @return the response entity
	 */
	@ExceptionHandler(value = RuleOperationKeyNotValidException.class)
	public ResponseEntity<Object> returnRuleOperationKeyNotValieExceptionResponse(ParseDataException exception) {
		return new ResponseEntity("{\"Status\":\"Failure\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Return rule operation not valie exception response.
	 *
	 * @param exception the exception
	 * @return the response entity
	 */
	@ExceptionHandler(value = RuleOperationNotValidException.class)
	public ResponseEntity<Object> returnRuleOperationNotValieExceptionResponse(ParseDataException exception) {
		return new ResponseEntity("{\"Status\":\"Failure\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Return param type not valid exception response.
	 *
	 * @param exception the exception
	 * @return the response entity
	 */
	@ExceptionHandler(value = ParamTypeNotValidException.class)
	public ResponseEntity<Object> returnParamTypeNotValidExceptionResponse(ParseDataException exception) {
		return new ResponseEntity("{\"Status\":\"Failure\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
