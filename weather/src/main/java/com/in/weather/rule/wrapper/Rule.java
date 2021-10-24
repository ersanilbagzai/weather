package com.in.weather.rule.wrapper;

import com.in.weather.rule.enums.Operation;
import com.in.weather.rule.enums.OperationKey;

import lombok.Data;

@Data
public class Rule {

	private String ruleName;
	
	private Operation op;
	
	private OperationKey onKey;
	
	private String value;
	
	private String message;
	
}
