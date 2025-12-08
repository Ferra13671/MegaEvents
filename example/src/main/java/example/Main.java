package example;

import com.ferra13671.MegaEvents.eventbus.impl.EventBus;
import com.ferra13671.MegaEvents.eventbus.IEventBus;
import com.ferra13671.MegaEvents.eventbus.impl.LambdaListener;

public class Main {

    //Create a new EventBus
    public static final IEventBus eventBus = new EventBus();

    //Create a new lambda listener
    public static final LambdaListener<ExampleEvent> lambdaListener = new LambdaListener<>(ExampleEvent.class, exampleEvent -> System.out.println("Example lambda listener passed! " + exampleEvent.count));
    //Create a new Object listener
    public static final Listener objectListener = new Listener();

    public static void main(String[] args) {
        //Register listeners
        eventBus.register(lambdaListener);
        eventBus.register(objectListener);

        //Start a thread that will call the example event.
        new ExampleEventThread().start();
    }
}
