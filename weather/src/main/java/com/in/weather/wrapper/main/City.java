package com.in.weather.wrapper.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new city.
 */
@Data
@ApiModel(description = "City Details")
public class City {

	/** The id. */
	@ApiModelProperty(value = "City id")
	private Long id;

	/** The name. */
	@ApiModelProperty(value = "Name of the city")
	private String name;

	/** The coord. */
	@ApiModelProperty(value = "Cordinates of city")
	private Coordinates coord;

	/** The country. */
	@ApiModelProperty(value = "Country name")
	private String country;

	/** The population. */
	@ApiModelProperty(value = "Population of city")
	private Integer population;

	/** The timezone. */
	@ApiModelProperty(value = "timezome")
	private Integer timezone;

	/** The sunrise time. */
	@ApiModelProperty(value = "Time of sunrise")
	private Long sunriseTime;

	/** The sunset time. */
	@ApiModelProperty(value = "Time of sunset")
	private Long sunsetTime;

}
