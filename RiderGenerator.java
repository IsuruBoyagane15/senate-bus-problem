import java.util.Random;

/**
 * Class to spawn the threads for the riders arrival
 */
public class RiderGenerator implements Runnable {

    private final float meanArivalTime;
    private final BusStop busStop;
    private static Random RANDOM =  new Random();

    public RiderGenerator(float arrivalMeanTime, BusStop waitingArea) {
        this.meanArivalTime = arrivalMeanTime;
        this.busStop = waitingArea;
//        RANDOM = new Random();
    }

    @Override
    public void run() {

        int riderCount = 1;
        // Spawning rider threads for the user specified value
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // Initializing and starting the rider threads
                Rider rider = new Rider(busStop.getWaitingAreaEntranceSemaphore(), busStop.getBoardingAreaEntranceSemaphore(), busStop.getBusDepartureSemaphore(), busStop.getMutex(), riderCount, busStop);
//                (new Thread(rider)).start();
                Thread riderThread = new Thread(rider);
                riderThread.start();

                riderCount++;
                // Sleeping the thread to obtain the inter arrival time between the threads
                Thread.sleep(getRiderWaitingTime());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long getRiderWaitingTime() {
        float lambda = 1 / meanArivalTime;
        return Math.round(-Math.log(1 - RANDOM.nextFloat()) / lambda);
    }
}
