package com.in.weather.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.in.weather.parser.exception.ParseDataException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Interface IcityRest.
 */
@ResponseBody
public interface IcityRest {

	/**
	 * Gets the citys.
	 *
	 * @return the citys
	 * @throws ParseDataException the parse data exception
	 */
	@ApiOperation(value = "Get City List", response = String.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorised"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(path = "detail")
	public ResponseEntity<Object> getCitys() throws ParseDataException;

}
