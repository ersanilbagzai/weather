package com.in.weather.rule.service;

import java.util.List;

import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.rule.wrapper.Rule;

/**
 * The Interface IRuleService.
 */
public interface IRuleService {

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 * @throws ParseDataException the parse data exception
	 */
	List<Rule> getRules() throws ParseDataException;

}
