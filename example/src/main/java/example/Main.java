package example;

import com.ferra13671.megaevents.eventbus.impl.EventBus;
import com.ferra13671.megaevents.eventbus.IEventBus;
import com.ferra13671.megaevents.eventbus.impl.LambdaListener;

public class Main {

    //Create a new EventBus
    public static final IEventBus eventBus = new EventBus();

    //Create a new lambda listener
    public static final LambdaListener<ExampleEvent> lambdaListener = new LambdaListener<>(ExampleEvent.class, exampleEvent -> System.out.println("Example lambda listener passed! " + exampleEvent.count));
    //Create a new Object listener
    public static final ObjectListener objectListener = new ObjectListener();

    public static void main(String[] args) {
        //Register listeners
        eventBus.register(lambdaListener);
        eventBus.register(objectListener);
        eventBus.register(StaticListener.class);
        eventBus.register(new SuperListener2());

        //Start a thread that will call the example event.
        new ExampleEventThread().start();
    }
}
