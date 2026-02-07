package com.ferra13671.megaevents.event;

import com.ferra13671.megaevents.eventbus.impl.RegisteredMethod;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Encapsulates the invocation of all listeners for a specific event.
 * Its implementation is a miniature version of EventBus, which handles only one event.
 *
 * @param <T> type of event that the event dispatcher should handle.
 */
public class EventDispatcher<T extends Event<T>> {
    /** List of all registered listeners for this dispatcher. **/
    private final List<RegisteredMethod> registeredMap = new CopyOnWriteArrayList<>();
    /** Method to call all registered listeners. **/
    private Consumer<List<Object>> invokeConsumer = (args) -> {};
    /** Event class that the dispatcher should handle. **/
    private final Class<T> eventClass;

    /**
     * @param eventClass event class that the dispatcher should handle.
     */
    public EventDispatcher(Class<T> eventClass) {
        this.eventClass = eventClass;
    }

    /**
     * Returns list of all registered listeners for this dispatcher.
     *
     * @return list of all registered listeners for this dispatcher.
     */
    public List<RegisteredMethod> getRegisteredMap() {
        return registeredMap;
    }

    /**
     * Returns method to call all registered listeners.
     *
     * @return method to call all registered listeners.
     */
    public Consumer<List<Object>> getInvokeConsumer() {
        return invokeConsumer;
    }

    /**
     * Sets the method to call all registered listeners.
     *
     * @param invokeConsumer method to call all registered listeners.
     */
    public void setInvokeConsumer(Consumer<List<Object>> invokeConsumer) {
        this.invokeConsumer = invokeConsumer;
    }

    /**
     * Returns event class that the dispatcher should handle.
     *
     * @return event class that the dispatcher should handle.
     */
    public Class<T> getEventClass() {
        return eventClass;
    }
}
