import java.util.concurrent.Semaphore;

/**
 * Class for the bus threads
 */
public class Bus implements Runnable {

    private final int index;
    private final BusStop busStop;
    private final Semaphore riderBoardingSemaphore;
    private final Semaphore busDepartureSemaphore;
    private final Semaphore mutex;


    public Bus(Semaphore riderBoardingSemaphore, Semaphore busDepartureSemaphore, Semaphore mutex, int index, BusStop busStop) {
        this.index = index;
        this.busStop = busStop;
        this.riderBoardingSemaphore = riderBoardingSemaphore;
        this.busDepartureSemaphore = busDepartureSemaphore;
        this.mutex = mutex;
    }

    @Override
    public void run() {

        try {
            mutex.acquire();
                // Arrival of the bus
                arrived();

                // Checking if there are waiting riders
                if (busStop.getRidersCount() > 0) {

                    // Releasing the rider boarding semaphore allowing a rider to get into the bus
                    riderBoardingSemaphore.release();
                    // Acquiring the bus depaarture semaphore to wait the bus until the riders get boarded
                    busDepartureSemaphore.acquire();
                }
            mutex.release();

            // Depart the bus
            depart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void depart() {
        System.out.println("---- Bus : " + index + " departed");
    }

    public void arrived() {
        System.out.println("---- Bus : " + index + " arrived");
        System.out.println("---- Waiting rider count : " + busStop.getRidersCount());
    }
}