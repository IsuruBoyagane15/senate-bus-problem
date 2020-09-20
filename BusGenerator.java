/**
 * Sub class to spawn the bus threads
 *
 */
public class BusGenerator extends Generator implements Runnable {

    private final float busArrivalMeanTime;
    private final BusStop busStop;

    public BusGenerator(float busArrivalMeanTime, BusStop busStop) {
        this.busArrivalMeanTime = busArrivalMeanTime;
        this.busStop = busStop;
    }

    @Override
    public void run() {

        int busCount = 1;

        // Spawning bus threads for the user specified value
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // Initializing and starting the bus threads
                Bus bus = new Bus(busStop.getBoardingAreaEntranceSemaphore(), busStop.getBusDepartureSemaphore(), busStop.getMutex(), busCount, busStop);
                Thread busThread = new Thread(bus);
                busThread.start();

                busCount++;
                // Sleeping the thread to obtain the inter arrival time between the bus threads
                Thread.sleep(getWaitingTime(busArrivalMeanTime));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}