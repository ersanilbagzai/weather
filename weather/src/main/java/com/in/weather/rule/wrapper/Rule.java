package com.in.weather.rule.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.in.weather.rule.enums.Operation;
import com.in.weather.rule.enums.OperationKey;

import lombok.Data;

/**
 * Instantiates a new rule.
 */
@Data
public class Rule {

	/** The rule name. */
	private String ruleName;

	/** The op. */
	private Operation op;

	/** The on key. */
	private OperationKey onKey;

	/** The value. */
	private String value;

	/** The message. */
	private String message;

}
