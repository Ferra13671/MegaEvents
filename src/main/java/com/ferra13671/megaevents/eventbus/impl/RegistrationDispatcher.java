package com.ferra13671.megaevents.eventbus.impl;

import com.ferra13671.megaevents.*;
import com.ferra13671.megaevents.event.Event;
import com.ferra13671.megaevents.event.EventDispatcher;
import com.ferra13671.megaevents.eventbus.EventSubscriber;
import com.ferra13671.megaevents.exeptions.InvokeRegisteredMethodException;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;

/**
 * EventBus Listener Registration Manager.
 * Controls how listeners are registered and unregistered.
 */
public abstract class RegistrationDispatcher<T> {
    public static final RegistrationDispatcher<Object> OBJECT = new RegistrationDispatcher<Object>() {
        @Override
        public void register(Object listener, EventBus eventBus) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(EventSubscriber.class)) {
                    EventSubscriber annotation = method.getAnnotation(EventSubscriber.class);
                    Class<? extends Event> clazz = annotation.event()[0];
                    registerMethod(method, eventBus.getDispatcher(clazz), listener);
                }
            }
        }

        @Override
        public void unregister(Object listener, EventBus eventBus) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(EventSubscriber.class)) {
                    EventSubscriber annotation = method.getAnnotation(EventSubscriber.class);
                    Class<? extends Event> clazz = annotation.event()[0];
                    unregisterMethod(method, eventBus.getDispatcher(clazz), listener);
                }
            }
        }
    };
    public static final RegistrationDispatcher<LambdaListener<?>> LAMBDA = new RegistrationDispatcher<LambdaListener<?>>() {
        @Override
        public void register(LambdaListener<?> listener, EventBus eventBus) {
            for (Method method : listener.listener.getClass().getDeclaredMethods()) {
                if (method.getParameterTypes().length > 0) {
                    try {
                        method.setAccessible(true);
                        registerMethod(method, eventBus.getDispatcher(listener.clazz), listener.listener);
                    } catch (Exception ignored) {}
                }
            }
        }

        @Override
        public void unregister(LambdaListener<?> listener, EventBus eventBus) {
            for (Method method : listener.listener.getClass().getDeclaredMethods()) {
                if (method.getParameterTypes().length > 0) {
                    try {
                        unregisterMethod(method, eventBus.getDispatcher(listener.clazz), listener.listener);
                    } catch (Exception ignored) {}
                }
            }
        }
    };

    /**
     * Registers the listener.
     *
     * @param listener listener.
     */
    public abstract void register(T listener, EventBus eventBus);

    /**
     * Unregisters the listener.
     *
     * @param listener listener.
     */
    public abstract void unregister(T listener, EventBus eventBus);

    /**
     * Recreates method to call all registered listeners in event dispatcher.
     *
     * @param eventDispatcher event dispatcher.
     */
    protected void recreateConsumer(EventDispatcher<?> eventDispatcher) {
        eventDispatcher.setInvokeConsumer(ListUtils.convertToConsumer(eventDispatcher.getRegisteredList(), ((registeredMethod, args) -> {
            try {
                if (registeredMethod.ghostEvent)
                    registeredMethod.method.invoke(registeredMethod.object);
                else
                    registeredMethod.method.invoke(registeredMethod.object, args.toArray());
            } catch (Exception e) {
                throw new InvokeRegisteredMethodException(e);
            }
        })));
    }

    protected void registerMethod(Method method, EventDispatcher<?> dispatcher, Object listener) {
        boolean needAdd = true;
        for (RegisteredMethod registeredMethod : dispatcher.getRegisteredList()) {
            if (registeredMethod.method.equals(method) && registeredMethod.object.equals(listener)) {
                needAdd = false;
                break;
            }
        }
        if (needAdd)
            dispatcher.getRegisteredList().add(new RegisteredMethod(listener, method, method.getParameterTypes().length == 0));

        dispatcher.getRegisteredList().sort(Comparator.comparing(registeredMethod ->
                registeredMethod.method.isAnnotationPresent(EventSubscriber.class) ? registeredMethod.method.getAnnotation(EventSubscriber.class).priority() : 0
        ));
        Collections.reverse(dispatcher.getRegisteredList());

        recreateConsumer(dispatcher);
    }

    protected void unregisterMethod(Method method, EventDispatcher<?> dispatcher, Object listener) {
        dispatcher.getRegisteredList().removeIf(registeredMethod -> registeredMethod.object.equals(listener) && registeredMethod.method.equals(method));

        recreateConsumer(dispatcher);
    }
}
