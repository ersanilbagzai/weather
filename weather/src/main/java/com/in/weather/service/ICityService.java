package com.in.weather.service;

import java.util.List;

import com.in.weather.parser.exception.ParseDataException;

/**
 * The Interface ICityService.
 */
public interface ICityService {

	/**
	 * Gets the citys.
	 *
	 * @return the citys
	 * @throws ParseDataException the parse data exception
	 */
	public List<String> getCities() throws ParseDataException;
}
