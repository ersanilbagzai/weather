package com.in.weather.rest.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.weather.rest.IcityRest;
import com.in.weather.service.ICityService;

@Primary
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "city")
@CrossOrigin
public class CityRestImpl implements IcityRest {

	@Autowired
	private ICityService iCityService;

	@Override
	public ResponseEntity getCitys() throws IOException {
		return new ResponseEntity(iCityService.getCitys(), HttpStatus.OK);
	}
}
