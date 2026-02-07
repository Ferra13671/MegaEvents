package example;

import com.ferra13671.megaevents.eventbus.EventSubscriber;

public class Listener {

    @EventSubscriber(event = ExampleEvent.class)
    public void exampleDefaultListener(ExampleEvent e) {
        System.out.println("Example default listener passed! " + e.count);
    }

    @EventSubscriber(event = ExampleEvent.class)
    public void exampleGhostListener() {
        System.out.println("Example ghost listener passed!");
    }
}
