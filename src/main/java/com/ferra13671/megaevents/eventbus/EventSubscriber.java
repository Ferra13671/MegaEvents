package com.ferra13671.megaevents.eventbus;

import com.ferra13671.megaevents.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for methods that identifies the method as a listener.
 * The annotation also stores various basic information about the listener.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventSubscriber {
    /**
     * Returns listener priority among other listeners.
     * Listeners are invoked in descending priority order, so the higher the priority, the earlier it will be invoked among other listeners.
     * <p>
     * By default, a listener has a priority of 0.
     *
     * @return listener priority among other listeners.
     */
    int priority() default 0;

    /**
     * Returns event class required by the listener.
     *
     * @return event class required by the listener.
     */
    Class<? extends Event<?>>[] event();
}