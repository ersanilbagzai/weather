package com.in.weather.http.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.in.weather.http.service.IRestTemplateService;
import com.in.weather.service.exceptions.ParamTypeNotValidException;
import com.in.weather.utils.WeatherConstants;

/**
 * The Class RestTemplateServiceImpl.
 */
@Service
public class RestTemplateServiceImpl implements IRestTemplateService {

	Logger logger = LoggerFactory.getLogger(RestTemplateServiceImpl.class);
			
	/** The rest template. */
	private final RestTemplate restTemplate;

	/**
	 * Instantiates a new rest template service impl.
	 *
	 * @param restTemplateBuilder the rest template builder
	 */
	public RestTemplateServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	/**
	 * Gets the posts plain JSON.
	 *
	 * @return the posts plain JSON
	 */
	@Override
	public String getPostsPlainJSON() {
		String url = "https://api.openweathermap.org/data/2.5/forecast?q=london&appid=d2929e9483efc82c82c32ee7e02d563e&cnt=10";
		return this.restTemplate.getForObject(url, String.class);
	}

	/**
	 * Gets the posts plain JSON.
	 *
	 * @param baseUrl   the base url
	 * @param pararms   the pararms
	 * @param paramType the param type
	 * @return the posts plain JSON
	 * @throws ParamTypeNotValidException the param type not valid exception
	 */
	@Override
	public String getPostsPlainJSON(String baseUrl, MultiValueMap<String, String> params, String paramType)
			throws ParamTypeNotValidException {
		logger.trace("Calling Rest : baseUrl - " + baseUrl + " params - " + params + " paramType - " + paramType);
		if (paramType.equalsIgnoreCase(WeatherConstants.PARAM_TYPE_QUERY)) {
			UriComponents build = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParams(params).build();
			return restTemplate.getForObject(build.toUriString(), String.class);
		} else {
			logger.error("ParamTypeNotValidException in getPostsPlainJSON");
			throw new ParamTypeNotValidException();
		}
	}

}
