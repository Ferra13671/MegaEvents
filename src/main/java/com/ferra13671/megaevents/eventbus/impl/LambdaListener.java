package com.ferra13671.megaevents.eventbus.impl;

import com.ferra13671.megaevents.event.Event;

import java.util.function.Consumer;

/**
 * An object that stores the base lambda listener and the event class it requires.
 */
public class LambdaListener<T extends Event<T>> {
    /** Event class that the listener needs. **/
    public final Class<T> clazz;
    /** Lambda listener. **/
    public final Consumer<T> listener;

    /**
     * @param clazz event class that the listener needs.
     * @param listener lambda listener.
     */
    public LambdaListener(Class<T> clazz, Consumer<T> listener) {
        this.clazz = clazz;
        this.listener = listener;
    }
}
