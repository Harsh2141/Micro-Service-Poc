package com.tender247.poc.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
