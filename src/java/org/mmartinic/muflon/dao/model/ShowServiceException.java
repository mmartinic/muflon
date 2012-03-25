package org.mmartinic.muflon.dao.model;

import org.mmartinic.muflon.dao.base.AbstractServiceException;

public class ShowServiceException extends AbstractServiceException {

    private static final long serialVersionUID = 932250902896101693L;

    public ShowServiceException() {
        super();
    }

    public ShowServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ShowServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShowServiceException(String message) {
        super(message);
    }

    public ShowServiceException(Throwable cause) {
        super(cause);
    }

}
