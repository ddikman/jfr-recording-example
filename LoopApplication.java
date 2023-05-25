import jdk.jfr.Configuration;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Label;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.lang.management.ManagementFactory;

public class LoopApplication {
    public static void main(String[] args) throws Exception {
        // Configuration config = Configuration.getConfiguration("default");
        // config.enable("ActionDurationEvent");

        // Recording recording = new Recording(config);
        // recording.start();

        LoopApplication loopApp = new LoopApplication();
        loopApp.runLoop();

        // recording.stop();

        // // Process the recorded events
        // for (RecordedEvent event : recording.getEvents()) {
        //     if (event.getEventType().getName().equals("ActionDurationEvent")) {
        //         // Do something with the event data
        //         String actionName = event.getString("actionName");
        //         System.out.println("Action Name: " + actionName);
        //         // You can access other attributes of the event if needed
        //         // ...
        //     }
        // }

        // recording.close();
    }

    private void runLoop() throws InterruptedException {

        String processId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        System.out.println("Running in process with PID: " + processId);

        while (true) {
            ActionDurationEvent event = new ActionDurationEvent("Custom event");
            event.begin();

            Random random = new Random();

            int minSeconds = 1;
            int maxSeconds = 5;
            int randomSeconds = random.nextInt(maxSeconds - minSeconds + 1) + minSeconds;

            new SleepEvent(randomSeconds).commit();

            System.out.print("Sleeping for " + randomSeconds + " seconds\n");
            Thread.sleep(randomSeconds * 1000);

            event.end();
            event.commit();
        }
    }
}

@Label("Sleep Action")
class SleepEvent extends jdk.jfr.Event {
  @Label("Sleep duration")
  private final int sleepDuration;

  public SleepEvent(int sleepDuration) {
    this.sleepDuration = sleepDuration;
  }
}

@Label("Action Duration")
class ActionDurationEvent extends jdk.jfr.Event {
    @Label("Action Name")
    private final String actionName;

    public ActionDurationEvent(String actionName) {
        this.actionName = actionName;
    }
}
