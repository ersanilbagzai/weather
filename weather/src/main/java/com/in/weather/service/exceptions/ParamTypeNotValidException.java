package com.in.weather.service.exceptions;

/**
 * The Class ParamTypeNotValidException.
 */
public class ParamTypeNotValidException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3006050460369110202L;

	/**
	 * Instantiates a new param type not valid exception.
	 */
	public ParamTypeNotValidException() {
	}

	/**
	 * Instantiates a new param type not valid exception.
	 *
	 * @param message the message
	 */
	public ParamTypeNotValidException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new param type not valid exception.
	 *
	 * @param cause the cause
	 */
	public ParamTypeNotValidException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new param type not valid exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public ParamTypeNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

}
