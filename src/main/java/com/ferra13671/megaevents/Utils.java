package com.ferra13671.megaevents;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@UtilityClass
public class Utils {

    public <T> Runnable convertToRunnable(List<T> list, Consumer<T> consumer) {
        Runnable runnable = () -> {};
        for (T object : list) {
            Runnable r = () -> consumer.accept(object);
            Runnable r2 = runnable;
            runnable = () -> {
                r2.run();
                r.run();
            };
        }
        return runnable;
    }

    public <T> Consumer<List<Object>> convertToConsumer(List<T> list, BiConsumer<T, List<Object>> invokeConsumer) {
        Consumer<List<Object>> consumer = (args) -> {};
        for (T object : list) {
            Consumer<List<Object>> c = (args) -> invokeConsumer.accept(object, args);
            Consumer<List<Object>> c2 = consumer;
            consumer = (args) -> {
                c2.accept(args);
                c.accept(args);
            };
        }
        return consumer;
    }

    public List<Method> getAllMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        getAllMethods(clazz, methods);

        return methods;
    }

    public void getAllMethods(Class<?> clazz, List<Method> dest) {
        Collections.addAll(dest, clazz.getDeclaredMethods());

        Class<?> superClass = clazz.getSuperclass();
        if (!superClass.equals(Object.class))
            getAllMethods(superClass, dest);
    }
}
