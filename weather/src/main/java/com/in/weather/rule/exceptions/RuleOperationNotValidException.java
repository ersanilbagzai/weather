package com.in.weather.rule.exceptions;

/**
 * The Class RuleOperationNotValidException.
 */
public class RuleOperationNotValidException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3006050460369110202L;

	/**
	 * Instantiates a new rule operation not valid exception.
	 */
	public RuleOperationNotValidException() {
	}

	/**
	 * Instantiates a new rule operation not valid exception.
	 *
	 * @param message the message
	 */
	public RuleOperationNotValidException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new rule operation not valid exception.
	 *
	 * @param cause the cause
	 */
	public RuleOperationNotValidException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new rule operation not valid exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public RuleOperationNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

}
