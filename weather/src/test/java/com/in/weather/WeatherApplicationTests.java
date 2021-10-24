package com.in.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.in.weather.service.IWeatherService;

@SpringBootTest
class WeatherApplicationTests {

	@Autowired
	private IWeatherService ws;
	
	@Test
	void contextLoads() {
		try {
			ws.getWeatherDetail("indore");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
