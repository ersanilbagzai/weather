package com.in.weather.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.rest.IcityRest;
import com.in.weather.service.ICityService;

/**
 * The Class CityRestImpl.
 */
@Primary
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "city")
@CrossOrigin
public class CityRestImpl implements IcityRest {

	Logger logger = LoggerFactory.getLogger(CityRestImpl.class);
	
	/** The i city service. */
	@Autowired
	private ICityService iCityService;

	/**
	 * Gets the cites.
	 *
	 * @return the cites
	 * @throws ParseDataException the parse data exception
	 */
	@Override
	public ResponseEntity getCities() throws ParseDataException {
		logger.info(
				"In Methhod : getCitys, Calling service : iCityService.getCities");
		
		return new ResponseEntity(iCityService.getCities(), HttpStatus.OK);
	}
}
