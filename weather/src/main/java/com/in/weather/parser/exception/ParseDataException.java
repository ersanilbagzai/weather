package com.in.weather.parser.exception;

/**
 * The Class ParseDataException.
 */
public class ParseDataException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3006050460369110202L;

	/**
	 * Instantiates a new parses the data exception.
	 */
	public ParseDataException() {
	}

	/**
	 * Instantiates a new parses the data exception.
	 *
	 * @param message the message
	 */
	public ParseDataException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new parses the data exception.
	 *
	 * @param cause the cause
	 */
	public ParseDataException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new parses the data exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public ParseDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
