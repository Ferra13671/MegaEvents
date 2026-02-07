package com.ferra13671.megaevents;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class ListUtils {

    public static <T> Runnable convertToRunnable(List<T> list, Consumer<T> consumer) {
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

    public static <T> Consumer<List<Object>> convertToConsumer(List<T> list, BiConsumer<T, List<Object>> invokeConsumer) {
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
}
