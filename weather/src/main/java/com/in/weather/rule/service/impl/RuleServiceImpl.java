package com.in.weather.rule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in.weather.rule.service.IRuleService;
import com.in.weather.rule.wrapper.Rule;

@Service
public class RuleServiceImpl implements IRuleService {

	@Autowired
	private Environment env;

	@Override
	public List<Rule> getRules() throws JsonMappingException, JsonProcessingException {
		String ruleDataStr = env.getProperty("rule.data");

		ObjectMapper objectMapper = new ObjectMapper();
		List<Rule> rules = objectMapper.readValue(ruleDataStr, new TypeReference<List<Rule>>() {
		});

		return rules;
	}
}
