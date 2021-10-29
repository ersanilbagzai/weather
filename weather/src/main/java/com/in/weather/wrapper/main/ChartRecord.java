package com.in.weather.wrapper.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Instantiates a new chart record.
 */
@Data
@ApiModel(description = "Chart Record, Constitutes a record for chart having date and the corresponding value on that date")
public class ChartRecord {

	/** The date. */
	@ApiModelProperty(value = "date")
	private Long date;

	/** The value. */
	@ApiModelProperty(value = "value")
	private Double value;
}
