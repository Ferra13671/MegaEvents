package com.ferra13671.megaevents.event;

import com.ferra13671.megaevents.eventbus.IEventBus;

/**
 * The base class for all events.
 *
 * @see IEventBus
 * @see EventDispatcher
 */

public abstract class Event<T extends Event<T>> {
    /** Event closing status. **/
    private boolean cancelled = false;

    public Event() {}

    /**
     * Returns whether the event is currently closed or not.
     *
     * @return whether the event is currently closed or not.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the event closing status.
     *
     * @param cancelled event closing status.
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Closes the event.
     */
    public void cancel() {
        this.cancelled = true;
    }
}
