package com.in.weather.wrapper.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new weather info.
 */
@Data
@ApiModel(description = "Weather Info")
public class WeatherInfo {

	/** The temp. */
	@ApiModelProperty(value = "Temperature")
	private Double temp;

	/** The feels like. */
	@ApiModelProperty(value = "Feels Like")
	private Double feelsLike;

	/** The temp min. */
	@ApiModelProperty(value = "Minimum Temperature")
	private Double tempMin;

	/** The temp max. */
	@ApiModelProperty(value = "Maximum Temperature")
	private Double tempMax;

	/** The pressure. */
	@ApiModelProperty(value = "pressure")
	private Double pressure;

	/** The sea level. */
	@ApiModelProperty(value = "Sea Level")
	private Double seaLevel;

	/** The grnd level. */
	@ApiModelProperty(value = "Grnd Level")
	private Double grndLevel;

	/** The humidity. */
	@ApiModelProperty(value = "Humidity")
	private Double humidity;

	/** The temp kf. */
	@ApiModelProperty(value = "Temp Kf")
	private Double tempKf;

	/** The suggestion. */
	@ApiModelProperty(value = "Suggestion")
	private String suggestion;

}
