package example;

import com.ferra13671.megaevents.eventbus.EventSubscriber;

public class ObjectListener {

    @EventSubscriber(event = ExampleEvent.class)
    public void defaultListener(ExampleEvent e) {
        System.out.println("Example object listener passed! " + e.count);
    }

    @EventSubscriber(event = ExampleEvent.class)
    public void ghostListener() {
        System.out.println("Example object ghost listener passed!");
    }
}
