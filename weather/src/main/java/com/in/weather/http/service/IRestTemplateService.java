package com.in.weather.http.service;

import org.springframework.util.MultiValueMap;

import com.in.weather.service.exceptions.ParamTypeNotValidException;

/**
 * The Interface IRestTemplateService.
 */
public interface IRestTemplateService {

	/**
	 * Gets the posts plain JSON.
	 *
	 * @return the posts plain JSON
	 */
	String getPostsPlainJSON();

	/**
	 * Gets the posts plain JSON.
	 *
	 * @param baseUrl   the base url
	 * @param pararms   the pararms
	 * @param paramType the param type
	 * @return the posts plain JSON
	 * @throws ParamTypeNotValidException the param type not valid exception
	 */
	String getPostsPlainJSON(String baseUrl, MultiValueMap<String, String> pararms, String paramType)
			throws ParamTypeNotValidException;

}
