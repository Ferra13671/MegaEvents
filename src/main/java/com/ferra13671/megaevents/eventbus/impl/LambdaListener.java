package com.ferra13671.megaevents.eventbus.impl;

import com.ferra13671.megaevents.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * An object that stores the base lambda listener and the event class it requires.
 */
@Getter
@AllArgsConstructor
public class LambdaListener<T extends Event<T>> {
    /** Event class that the listener needs. **/
    private final Class<T> clazz;
    /** Lambda listener. **/
    private final Consumer<T> listener;
}
