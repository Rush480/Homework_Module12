import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeDisplay {

    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        Runnable everySecMessage = new Runnable() {
            private final long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("Час, що минув від запуску: " + (elapsedTime / 1000) + " секунд");
            }
        };

        Runnable fiveSecMessage = () -> System.out.println("Минуло 5 секунд");

        scheduler.scheduleAtFixedRate(everySecMessage, 0, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(fiveSecMessage, 5, 5, TimeUnit.SECONDS);
    }
}
