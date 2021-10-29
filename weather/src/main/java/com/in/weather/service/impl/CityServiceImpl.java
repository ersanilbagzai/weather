package com.in.weather.service.impl;

import java.util.List;

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

	/** The env. */
	@Autowired
	Environment env;

	/**
	 * Gets the citys.
	 *
	 * @return the citys
	 * @throws ParseDataException the parse data exception
	 */
	@Override
	public List<String> getCitys() throws ParseDataException {
		String data = env.getProperty(WeatherConstants.CITY_DATA_CONFIG_CONST);

		ObjectMapper objectMapper = new ObjectMapper();
		List<String> cityList;
		try {
			cityList = objectMapper.readValue(data, new TypeReference<List<String>>() {
			});
		} catch (JsonProcessingException e) {
			throw new ParseDataException();
		}

		return cityList;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ParseDataException the parse data exception
	 */
	public static void main(String[] args) throws ParseDataException {
		CityServiceImpl c = new CityServiceImpl();
		c.getCitys();
	}

}
