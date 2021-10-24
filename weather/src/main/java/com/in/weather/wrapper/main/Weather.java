package com.in.weather.wrapper.main;

import lombok.Data;

@Data
public class Weather {

	private Long id;
	
	private String main;
	
	private String description;
	
	private String icon;

}
