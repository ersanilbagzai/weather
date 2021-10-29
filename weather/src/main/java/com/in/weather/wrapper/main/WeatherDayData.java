package com.in.weather.wrapper.main;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new weather day data.
 */
@Data
@ApiModel(description = "Weather Data of a day")
public class WeatherDayData {

	/** The date. */
	@ApiModelProperty(value = "Date")
	private Long date;

	/** The weather info. */
	@ApiModelProperty(value = "Weather Info")
	private WeatherInfo weatherInfo;

	/** The weather. */
	@ApiModelProperty(value = "weather")
	private List<Weather> weather;

	/** The clouds. */
	@ApiModelProperty(value = "Clouds")
	private Clouds clouds;

	/** The wind. */
	@ApiModelProperty(value = "Wind")
	private Wind wind;

	/** The visibility. */
	@ApiModelProperty(value = "visibility")
	private Integer visibility;

}
