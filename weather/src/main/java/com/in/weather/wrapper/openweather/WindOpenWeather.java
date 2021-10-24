package com.in.weather.wrapper.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.in.weather.wrapper.main.Wind;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WindOpenWeather {

	private Double speed;
	
	private Integer deg;
	
	private Double gust;
	
	public static Wind getGenericWindOpenWeather(WindOpenWeather input) {
		Wind wi = new Wind();
		wi.setSpeed(input.getSpeed());
		wi.setDeg(input.getDeg());
		wi.setGust(input.getGust());
		return wi;
	}
}
