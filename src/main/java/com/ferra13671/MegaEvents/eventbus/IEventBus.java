package com.ferra13671.MegaEvents.eventbus;

import com.ferra13671.MegaEvents.event.Event;
import com.ferra13671.MegaEvents.eventbus.impl.EventBus;

/**
 * An interface that requires that an object have methods that implement EventBus.
 *
 * @see EventBus
 */
public interface IEventBus {

    /**
     * Registers a listener object.
     * Once registered, the listener will be called when the event it needs is called.
     *
     * @param listener listener object.
     */
    void register(Object listener);

    /**
     * Unregisters a listener object.
     * Once unregistered, the listener will no longer be called if the event it needs is called.
     *
     * @param listener listenerObject.
     */
    void unregister(Object listener);

    /**
     * Calls the event handler.
     * All listeners that require this event will be called when this event is called.
     *
     * @param event event to be called.
     * @param <T> event type.
     */
    <T extends Event<T>> void activate(T event);
}
