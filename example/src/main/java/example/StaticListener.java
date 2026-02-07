package example;

import com.ferra13671.megaevents.eventbus.EventSubscriber;

public class StaticListener {

    @EventSubscriber(event = ExampleEvent.class)
    public static void defaultListener(ExampleEvent e) {
        System.out.println("Example static listener passed! " + e.count);
    }

    @EventSubscriber(event = ExampleEvent.class)
    public static void ghostListener() {
        System.out.println("Example static ghost listener passed!");
    }
}
