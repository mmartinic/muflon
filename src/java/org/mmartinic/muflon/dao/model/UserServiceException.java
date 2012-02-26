package org.mmartinic.muflon.dao.model;

import org.mmartinic.muflon.dao.base.AbstractServiceException;

public class UserServiceException extends AbstractServiceException {

	private static final long serialVersionUID = 7481184047635047812L;

	public UserServiceException() {
		super();
	}

	public UserServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserServiceException(String message) {
		super(message);
	}

	public UserServiceException(Throwable cause) {
		super(cause);
	}

}
