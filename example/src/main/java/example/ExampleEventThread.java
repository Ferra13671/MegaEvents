package example;

public class ExampleEventThread extends Thread {

    private int count;

    @Override
    public void run() {
        while (!isInterrupted()) {
            //Activating the test event
            Main.eventBus.activate(new ExampleEvent(this.count++));

            try {
                sleep(1000);
            } catch (Exception ignored) {}
        }
    }
}
