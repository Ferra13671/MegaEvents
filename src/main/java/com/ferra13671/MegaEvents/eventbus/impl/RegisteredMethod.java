package com.ferra13671.MegaEvents.eventbus.impl;

import java.lang.reflect.Method;

/**
 * An object that stores information about a registered listener.
 */
//TODO Rewrite
public class RegisteredMethod {
    /** Object that contains listener. **/
    public final Object object;
    /** Listener. **/
    public final Method method;
    /** Is listener is ghost (does not receive the called event object). **/
    public final boolean ghostEvent;

    /**
     * @param object object that contains listener.
     * @param method listener.
     * @param ghostEvent is listener is ghost (does not receive the called event object)
     */
    public RegisteredMethod(Object object, Method method, boolean ghostEvent) {
        this.object = object;
        this.method = method;
        this.ghostEvent = ghostEvent;
    }
}