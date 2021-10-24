package com.in.weather.rest;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface IcityRest {

	@GetMapping(path = "detail")
	public ResponseEntity getCitys() throws IOException;
	
}
