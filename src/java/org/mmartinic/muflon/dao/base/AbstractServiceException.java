package org.mmartinic.muflon.dao.base;

public class AbstractServiceException extends Exception {

	private static final long serialVersionUID = 6548469097119332768L;

	public AbstractServiceException() {
		super();
	}

	public AbstractServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AbstractServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbstractServiceException(String message) {
		super(message);
	}

	public AbstractServiceException(Throwable cause) {
		super(cause);
	}

}
