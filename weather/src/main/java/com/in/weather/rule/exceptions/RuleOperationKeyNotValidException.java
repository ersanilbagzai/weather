package com.in.weather.rule.exceptions;

/**
 * The Class RuleOperationKeyNotValidException.
 */
public class RuleOperationKeyNotValidException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3006050460369110202L;

	/**
	 * Instantiates a new rule operation key not valid exception.
	 */
	public RuleOperationKeyNotValidException() {
	}

	/**
	 * Instantiates a new rule operation key not valid exception.
	 *
	 * @param message the message
	 */
	public RuleOperationKeyNotValidException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new rule operation key not valid exception.
	 *
	 * @param cause the cause
	 */
	public RuleOperationKeyNotValidException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new rule operation key not valid exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public RuleOperationKeyNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

}
