package com.in.weather.wrapper.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new clouds.
 */
@Data
@ApiModel(description = "Clouds Details")
public class Clouds {

	/** The all. */
	@ApiModelProperty(value = "all")
	private Integer all;
}
