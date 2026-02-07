package com.ferra13671.megaevents.eventbus.impl;

import com.ferra13671.megaevents.event.Event;
import com.ferra13671.megaevents.event.EventDispatcher;
import com.ferra13671.megaevents.eventbus.IEventBus;

import java.util.*;

/**
 * Basic implementation of EventBus.
 */
public class EventBus implements IEventBus {
    private final HashMap<Class<? extends Event<?>>, EventDispatcher<?>> dispatchers = new HashMap<>();

    @Override
    public void register(Object listener) {
        if (listener instanceof LambdaListener<?>) {
            LambdaListener<?> lambdaListener = (LambdaListener<?>) listener;
            RegistrationDispatcher.LAMBDA.register(lambdaListener, this);
        } else if (listener instanceof Class<?>) {
            Class<?> clazz = (Class<?>) listener;
            RegistrationDispatcher.CLASS.register(clazz, this);
        } else {
            RegistrationDispatcher.OBJECT.register(listener, this);
        }
    }

    @Override
    public void unregister(Object listener) {
        if (listener instanceof LambdaListener<?>) {
            LambdaListener<?> lambdaListener = (LambdaListener<?>) listener;
            RegistrationDispatcher.LAMBDA.unregister(lambdaListener, this);
        } else if (listener instanceof Class<?>) {
            Class<?> clazz = (Class<?>) listener;
            RegistrationDispatcher.CLASS.unregister(clazz, this);
        } else {
            RegistrationDispatcher.OBJECT.unregister(listener, this);
        }
    }

    @Override
    public <T extends Event<T>> void activate(T event) {
        getDispatcher(event.getClass()).getInvokeConsumer().accept(Collections.singletonList(event));
    }

    protected  <T extends Event<T>> EventDispatcher<T> getDispatcher(Class<T> clazz) {
        return (EventDispatcher<T>) this.dispatchers.computeIfAbsent(
                clazz,
                a -> new EventDispatcher<>(clazz)
        );
    }
}
