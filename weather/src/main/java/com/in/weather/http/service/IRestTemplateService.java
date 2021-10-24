package com.in.weather.http.service;

import java.util.Map;

import org.springframework.util.MultiValueMap;

public interface IRestTemplateService {

	String getPostsPlainJSON();

	String getPostsPlainJSON(String baseUrl, MultiValueMap<String, String> pararms, String paramType);

}
