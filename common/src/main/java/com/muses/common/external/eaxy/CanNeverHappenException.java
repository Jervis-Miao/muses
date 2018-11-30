package com.muses.common.external.eaxy;

public class CanNeverHappenException extends RuntimeException {

    public CanNeverHappenException(String message, Exception e) {
        super(message, e);
    }

}
