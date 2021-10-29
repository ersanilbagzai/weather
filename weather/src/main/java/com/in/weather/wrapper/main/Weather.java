package com.in.weather.wrapper.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new weather.
 */
@Data
@ApiModel(description = "Weather Details")
public class Weather {

	/** The id. */
	@ApiModelProperty(value = "Weather id")
	private Long id;

	/** The main. */
	@ApiModelProperty(value = "main")
	private String main;

	/** The description. */
	@ApiModelProperty(value = "Weather description")
	private String description;

	/** The icon. */
	@ApiModelProperty(value = "icon")
	private String icon;

}
