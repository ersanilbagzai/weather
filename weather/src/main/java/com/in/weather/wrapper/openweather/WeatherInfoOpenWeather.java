package com.in.weather.wrapper.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.in.weather.wrapper.main.WeatherInfo;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfoOpenWeather {

	private Double temp;

	private Double feels_like;

	private Double temp_min;

	private Double temp_max;

	private Double pressure;

	private Double sea_level;

	private Double grnd_level;

	private Double humidity;

	private Double temp_kf;

	public static WeatherInfo getGenericWeatherInfoOpenWeather(WeatherInfoOpenWeather input) {
		WeatherInfo wi = new WeatherInfo();
		wi.setTemp(input.getTemp());
		wi.setFeelsLike(input.getFeels_like());
		wi.setTempMin(input.getTemp_min());
		wi.setTempMax(input.getTemp_max());
		wi.setPressure(input.getPressure());
		wi.setSeaLevel(input.getSea_level());
		wi.setGrndLevel(input.getGrnd_level());
		wi.setHumidity(input.getHumidity());
		wi.setTempKf(input.getTemp_kf());
		return wi;
	}

}
