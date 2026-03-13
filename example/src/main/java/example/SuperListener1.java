package example;

import com.ferra13671.megaevents.eventbus.EventSubscriber;

public class SuperListener1 {

    @EventSubscriber(event = ExampleEvent.class)
    public void ghostListener() {
        System.out.println("Example super object listener passed!");
    }
}
