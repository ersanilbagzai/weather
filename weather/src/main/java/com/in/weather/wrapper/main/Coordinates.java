package com.in.weather.wrapper.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new coordinates.
 */
@Data
@ApiModel(description = "City Coordinates")
public class Coordinates {

	/** The lat. */
	@ApiModelProperty(value = "latitude")
	private Double lat;

	/** The lon. */
	@ApiModelProperty(value = "longitude")
	private Double lon;

}
