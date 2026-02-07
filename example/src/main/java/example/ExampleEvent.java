package example;

import com.ferra13671.megaevents.event.Event;

public class ExampleEvent extends Event<ExampleEvent> {
    public final int count;

    public ExampleEvent(int count) {
        this.count = count;
    }
}
