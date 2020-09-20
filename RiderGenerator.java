/**
 * Class to spawn the rider thread
 *
 */
public class RiderGenerator extends Generator implements Runnable {

    private final float riderArivalMeanTime;
    private final BusStop busStop;

    public RiderGenerator(float riderArrivalMeanTime, BusStop busStop) {
        this.riderArivalMeanTime = riderArrivalMeanTime;
        this.busStop = busStop;
    }

    @Override
    public void run() {

        int riderCount = 1;
        // Spawning rider threads for the user specified value
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // Initializing and starting the rider threads
                Rider rider = new Rider(busStop.getWaitingAreaEntranceSemaphore(), busStop.getBoardingAreaEntranceSemaphore(), busStop.getBusDepartureSemaphore(), busStop.getMutex(), riderCount, busStop);
                Thread riderThread = new Thread(rider);
                riderThread.start();

                riderCount++;
                // Sleeping the thread to obtain the inter arrival time between the threads
                Thread.sleep(getWaitingTime(riderArivalMeanTime));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
