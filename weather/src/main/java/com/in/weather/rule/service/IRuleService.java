package com.in.weather.rule.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.in.weather.rule.wrapper.Rule;

public interface IRuleService {

	List<Rule> getRules() throws JsonMappingException, JsonProcessingException;

}
