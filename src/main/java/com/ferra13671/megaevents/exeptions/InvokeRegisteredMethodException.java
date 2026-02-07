package com.ferra13671.megaevents.exeptions;

import lombok.experimental.StandardException;

/**
 * This exception is thrown when an error occurs while calling a registered method.
 * This is usually due to internal errors in the method, invalid arguments to the method, or an inability to access the method.
 */
@StandardException
public class InvokeRegisteredMethodException extends RuntimeException {
}
