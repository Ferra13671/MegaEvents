package com.ferra13671.megaevents.eventbus.impl;

import com.ferra13671.megaevents.*;
import com.ferra13671.megaevents.event.Event;
import com.ferra13671.megaevents.event.EventDispatcher;
import com.ferra13671.megaevents.eventbus.EventSubscriber;
import com.ferra13671.megaevents.exeptions.InvokeRegisteredMethodException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
                if (!Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(EventSubscriber.class)) {
                    EventSubscriber annotation = method.getAnnotation(EventSubscriber.class);
                    Class<? extends Event> clazz = annotation.event()[0];
                    registerMethod(method, eventBus.getDispatcher(clazz), listener);
                }
            }
        }

        @Override
        public void unregister(Object listener, EventBus eventBus) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (!Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(EventSubscriber.class)) {
                    EventSubscriber annotation = method.getAnnotation(EventSubscriber.class);
                    Class<? extends Event> clazz = annotation.event()[0];
                    unregisterMethod(method, eventBus.getDispatcher(clazz), listener);
                }
            }
        }
    };
    public static final RegistrationDispatcher<Class<?>> CLASS = new RegistrationDispatcher<Class<?>>() {
        @Override
        public void register(Class<?> listener, EventBus eventBus) {
            for (Method method : listener.getDeclaredMethods()) {
                if (Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(EventSubscriber.class)) {
                    EventSubscriber annotation = method.getAnnotation(EventSubscriber.class);
                    Class<? extends Event> clazz = annotation.event()[0];
                    registerMethod(method, eventBus.getDispatcher(clazz), listener);
                }
            }
        }

        @Override
        public void unregister(Class<?> listener, EventBus eventBus) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(EventSubscriber.class)) {
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
            for (Method method : listener.getListener().getClass().getDeclaredMethods()) {
                if (method.getParameterTypes().length > 0) {
                    try {
                        method.setAccessible(true);
                        registerMethod(method, eventBus.getDispatcher(listener.getClazz()), listener.getListener());
                    } catch (Exception ignored) {}
                }
            }
        }

        @Override
        public void unregister(LambdaListener<?> listener, EventBus eventBus) {
            for (Method method : listener.getListener().getClass().getDeclaredMethods()) {
                if (method.getParameterTypes().length > 0) {
                    try {
                        unregisterMethod(method, eventBus.getDispatcher(listener.getClazz()), listener.getListener());
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
                if (registeredMethod.isGhostEvent())
                    registeredMethod.getMethod().invoke(registeredMethod.getObject());
                else
                    registeredMethod.getMethod().invoke(registeredMethod.getObject(), args.toArray());
            } catch (Exception e) {
                throw new InvokeRegisteredMethodException(e);
            }
        })));
    }

    protected void registerMethod(Method method, EventDispatcher<?> dispatcher, Object listener) {
        boolean needAdd = true;
        for (RegisteredMethod registeredMethod : dispatcher.getRegisteredList()) {
            if (registeredMethod.getMethod().equals(method) && registeredMethod.getObject().equals(listener)) {
                needAdd = false;
                break;
            }
        }
        if (needAdd)
            dispatcher.getRegisteredList().add(new RegisteredMethod(listener, method, method.getParameterTypes().length == 0));

        dispatcher.getRegisteredList().sort(Comparator.comparing(registeredMethod ->
                registeredMethod.getMethod().isAnnotationPresent(EventSubscriber.class) ? registeredMethod.getMethod().getAnnotation(EventSubscriber.class).priority() : 0
        ));
        Collections.reverse(dispatcher.getRegisteredList());

        recreateConsumer(dispatcher);
    }

    protected void unregisterMethod(Method method, EventDispatcher<?> dispatcher, Object listener) {
        dispatcher.getRegisteredList().removeIf(registeredMethod -> registeredMethod.getObject().equals(listener) && registeredMethod.getMethod().equals(method));

        recreateConsumer(dispatcher);
    }
}
