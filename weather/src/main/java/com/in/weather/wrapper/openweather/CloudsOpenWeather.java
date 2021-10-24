package com.in.weather.wrapper.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.in.weather.wrapper.main.Clouds;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudsOpenWeather {

	private Integer all;

	public static Clouds getGenericCloudsOpenWeather(CloudsOpenWeather input) {
		Clouds cl = new Clouds();
		cl.setAll(input.getAll());
		return cl;
	}
}
