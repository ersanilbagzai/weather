package com.in.weather.rest.impl;

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

	/** The i city service. */
	@Autowired
	private ICityService iCityService;

	/**
	 * Gets the citys.
	 *
	 * @return the citys
	 * @throws ParseDataException the parse data exception
	 */
	@Override
	public ResponseEntity getCitys() throws ParseDataException {
		return new ResponseEntity(iCityService.getCitys(), HttpStatus.OK);
	}
}
