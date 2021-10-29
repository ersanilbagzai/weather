package com.in.weather.wrapper.main;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new weather data.
 */
@Data
@ApiModel(description = "Weather Data")
public class WeatherData {

	/** The weather day data. */
	@ApiModelProperty(value = "Weather Data Of a Particular Day")
	private List<WeatherDayData> weatherDayData;

	/** The city. */
	@ApiModelProperty(value = "city")
	private City city;

}
