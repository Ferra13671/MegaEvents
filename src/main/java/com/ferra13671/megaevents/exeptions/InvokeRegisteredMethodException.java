package com.ferra13671.megaevents.exeptions;

/**
 * This exception is thrown when an error occurs while calling a registered method.
 * This is usually due to internal errors in the method, invalid arguments to the method, or an inability to access the method.
 */

public class InvokeRegisteredMethodException extends RuntimeException {
    static final long serialVersionUID = 4669086040021792563L;

    public InvokeRegisteredMethodException(String message) {
        super(message);
    }

    public InvokeRegisteredMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeRegisteredMethodException(Throwable cause) {
        super(cause);
    }
}
