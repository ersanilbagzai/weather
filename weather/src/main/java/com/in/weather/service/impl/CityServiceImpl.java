package com.in.weather.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in.weather.service.ICityService;

@Service
public class CityServiceImpl implements ICityService {

	@Autowired
	Environment env;
	
//	@Override
//	public List<String> getCitys() throws IOException {
//		byte[] encoded = Files.readAllBytes(Paths.get("src/main/java/com/in/weather/data/city.json"));
//		String cityListStr = new String(encoded, StandardCharsets.US_ASCII);
//		  
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<String> cityList = objectMapper.readValue(cityListStr, new TypeReference<List<String>>() {
//		});
//
//		return cityList;
//	}
	
	@Override
	public List<String> getCitys() throws IOException {
		String data = env.getProperty("city.data");
		  
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> cityList = objectMapper.readValue(data, new TypeReference<List<String>>() {
		});

		return cityList;
	}
	
	public static void main(String[] args) throws IOException {
		CityServiceImpl c = new CityServiceImpl();
		c.getCitys();
	}

}
