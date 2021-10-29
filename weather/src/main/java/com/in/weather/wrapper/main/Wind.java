package com.in.weather.wrapper.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new wind.
 */
@Data
@ApiModel(description = "Wind")
public class Wind {

	/** The speed. */
	@ApiModelProperty(value = "Speed")
	private Double speed;

	/** The deg. */
	@ApiModelProperty(value = "deg")
	private Integer deg;

	/** The gust. */
	@ApiModelProperty(value = "gust")
	private Double gust;

}
