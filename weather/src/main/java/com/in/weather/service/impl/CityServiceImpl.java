package com.in.weather.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.service.ICityService;
import com.in.weather.utils.WeatherConstants;

/**
 * The Class CityServiceImpl.
 */
@Service
public class CityServiceImpl implements ICityService {

	Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
	
	/** The env. */
	@Autowired
	Environment env;

	/**
	 * Gets the cities.
	 *
	 * @return the cities
	 * @throws ParseDataException the parse data exception
	 */
	@Override
	public List<String> getCities() throws ParseDataException {
		String data = env.getProperty(WeatherConstants.CITY_DATA_CONFIG_CONST);

		logger.info("In getCities");
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> cityList;
		try {
			cityList = objectMapper.readValue(data, new TypeReference<List<String>>() {
			});
		} catch (JsonProcessingException e) {
			logger.error("ParseDataException in getCities : ", e.getStackTrace());
			throw new ParseDataException();
		}

		logger.info("getCities success : city size - " + cityList != null ? cityList.size() + "" : "0");
		return cityList;
	}
	
}
