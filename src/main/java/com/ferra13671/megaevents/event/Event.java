package com.ferra13671.megaevents.event;

import com.ferra13671.megaevents.eventbus.IEventBus;
import lombok.Getter;
import lombok.Setter;

/**
 * The base class for all events.
 *
 * @see IEventBus
 * @see EventDispatcher
 */

@Getter
@Setter
public abstract class Event<T extends Event<T>> {
    /** Event closing status. **/
    private boolean cancelled = false;

    public Event() {}

    /**
     * Closes the event.
     */
    public void cancel() {
        setCancelled(true);
    }
}
