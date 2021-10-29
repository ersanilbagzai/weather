package com.in.weather.rule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.rule.service.IRuleService;
import com.in.weather.rule.wrapper.Rule;

/**
 * The Class RuleServiceImpl.
 */
@Service
public class RuleServiceImpl implements IRuleService {

	/** The env. */
	@Autowired
	private Environment env;

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 * @throws ParseDataException the parse data exception
	 */
	@Override
	public List<Rule> getRules() throws ParseDataException {
		String ruleDataStr = env.getProperty("rule.data");

		ObjectMapper objectMapper = new ObjectMapper();
		List<Rule> rules;
		try {
			rules = objectMapper.readValue(ruleDataStr, new TypeReference<List<Rule>>() {
			});
		} catch (JsonProcessingException e) {
			throw new ParseDataException();
		}

		return rules;
	}
}
