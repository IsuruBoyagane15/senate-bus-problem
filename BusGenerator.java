import java.util.Random;

/**
 * Class to spawn the threads for the bus arrival
 */
public class BusGenerator extends Generator implements Runnable {

    private final float meanArivalTime;
    private final BusStop busStop;
//    private static final Random random = new Random();

    public BusGenerator(float meanArivalTime, BusStop busStop) {
        this.meanArivalTime = meanArivalTime;
        this.busStop = busStop;
//        random = new Random();
    }

    @Override
    public void run() {

        int busCount = 1;

        // Spawning bus threads for the user specified value
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // Initializing and starting the bus threads
                Bus bus = new Bus(busStop.getBoardingAreaEntranceSemaphore(), busStop.getBusDepartureSemaphore(), busStop.getMutex(), busCount, busStop);
//                (new Thread(bus)).start();
                Thread busThread = new Thread(bus);
                busThread.start();

                busCount++;
                // Sleeping the thread to obtain the inter arrival time between the bus threads
                Thread.sleep(getWaitingTime(meanArivalTime));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("All buses have finished arriving");
    }

    // Method to get the exponentially distributed bus inter arrival time
//    public long getBusWaitingTime() {
//        float lambda = 1 / meanArivalTime;
//        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
//    }
}