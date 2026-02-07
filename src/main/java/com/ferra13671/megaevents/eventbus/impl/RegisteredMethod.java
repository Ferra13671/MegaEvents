package com.ferra13671.megaevents.eventbus.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * An object that stores information about a registered listener.
 */
@Getter
@AllArgsConstructor
public class RegisteredMethod {
    /** Object that contains listener. **/
    private final Object object;
    /** Listener. **/
    private final Method method;
    /** Is listener is ghost (does not receive the called event object). **/
    private final boolean ghostEvent;
}