package com.ferra13671.megaevents.event;

import com.ferra13671.megaevents.eventbus.impl.RegisteredMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Encapsulates the invocation of all listeners for a specific event.
 * Its implementation is a miniature version of EventBus, which handles only one event.
 *
 * @param <T> type of event that the event dispatcher should handle.
 */
@Getter
public class EventDispatcher<T extends Event<T>> {
    /** List of all registered listeners for this dispatcher. **/
    private final List<RegisteredMethod> registeredList = new CopyOnWriteArrayList<>();
    /** Method to call all registered listeners. **/
    @Setter
    private Consumer<List<Object>> invokeConsumer = (args) -> {};
    /** Event class that the dispatcher should handle. **/
    private final Class<T> eventClass;

    /**
     * @param eventClass event class that the dispatcher should handle.
     */
    public EventDispatcher(Class<T> eventClass) {
        this.eventClass = eventClass;
    }
}
