import java.util.concurrent.Semaphore;

/**
 * Class for the bus threads
 */
public class Bus implements Runnable {

    private final int index;
    private final BusStop busStop;
    private final Semaphore riderBoardingSemaphore;
    private final Semaphore busDepartureSemaphore;
    private final Semaphore mutex_rider_bus;


    public Bus(Semaphore riderBoardingSemaphore, Semaphore busDepartureSemaphore, Semaphore mutex, int index, BusStop busStop) {
        this.index = index;
        this.busStop = busStop;
        this.riderBoardingSemaphore = riderBoardingSemaphore;
        this.busDepartureSemaphore = busDepartureSemaphore;
        this.mutex_rider_bus = mutex;
    }

    @Override
    public void run() {

        try {
            // Bus acquires the mutex stopping any more rider acquiring the mutex
            mutex_rider_bus.acquire();
                // Bus arrives
                arrived();

                // Checking if any riders are waiting in the bus stop
                if (busStop.getRidersCount() > 0) {

                    /* Releasing the rider boarding semaphore allowing a rider to get into the bus. 
                    Similar to opening the door for a rider to enter one by one 
                    */
                    riderBoardingSemaphore.release();
                    /* Acquiring the bus depaarture semaphore to wait the bus until the riders get boarded
                    The final rider to board signals the bus to depart by releasing this semaphore
                    */
                    busDepartureSemaphore.acquire();
                }
            // bus releases the mutex for other riders to enter the waiting area or for another bus 
            mutex_rider_bus.release();

            // Depart the bus
            depart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void depart() {
        System.out.println("<<<<<     Bus id : " + index + " departed     >>>>>\n");
    }

    public void arrived() {
        System.out.println("\n<<<<<     Bus id : " + index + " arrived     >>>>>");
        System.out.println("<<<<<     Waiting rider count is : " + busStop.getRidersCount() + "     >>>>>");
    }
}