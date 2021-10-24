package com.in.weather.http.service.impl;

import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.in.weather.http.service.IRestTemplateService;

@Service
public class RestTemplateServiceImpl implements IRestTemplateService {

	private final RestTemplate restTemplate;

	public RestTemplateServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public String getPostsPlainJSON() {
		String url = "https://api.openweathermap.org/data/2.5/forecast?q=london&appid=d2929e9483efc82c82c32ee7e02d563e&cnt=10";
		return this.restTemplate.getForObject(url, String.class);
	}
	
	@Override
	public String getPostsPlainJSON(String baseUrl, MultiValueMap<String, String> pararms, String paramType) {
		UriComponents build = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParams(pararms).build();
		return this.restTemplate.getForObject(build.toUriString(), String.class);
	}
	
}
