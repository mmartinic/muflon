package org.mmartinic.muflon.dao.model;

import org.mmartinic.muflon.dao.base.AbstractServiceException;

public class EpisodeServiceException extends AbstractServiceException {

	private static final long serialVersionUID = -7563349935049225587L;

	public EpisodeServiceException() {
		super();
	}

	public EpisodeServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EpisodeServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EpisodeServiceException(String message) {
		super(message);
	}

	public EpisodeServiceException(Throwable cause) {
		super(cause);
	}

}
