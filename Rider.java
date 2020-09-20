import java.util.concurrent.Semaphore;

/**
 * Class for the rider threads
 */
class Rider implements Runnable {

    private final int count;
    private final BusStop busStop;
    private final Semaphore waitingAreaEntranceSemaphore;
    private final Semaphore boardingSemaphore;
    private final Semaphore busDepartureSemaphore;
    private final Semaphore mutex_rider_bus;
    ;

    public Rider(Semaphore waitingAreaEntranceSemaphore, Semaphore boardingSemaphore, Semaphore busDepartureSemaphore, Semaphore mutex, int count, BusStop busStop) {
        //rider id 
        this.count = count; 

        //bus stop area
        this.busStop = busStop;

        // waiting area. Only 50 are allowed to enter at max
        this.waitingAreaEntranceSemaphore = waitingAreaEntranceSemaphore;

        /* boardingSemaphore is released by the bus if there exist any rider. 
        Acquired by a rider when boarding to the bus and released for the next rider.        
        */
        this.boardingSemaphore = boardingSemaphore;

        // busDepartureSemaphore is acquired by the bus once arrived. The final rider releases this signaling the bus to leave
        this.busDepartureSemaphore = busDepartureSemaphore;

        /* mutex_rider_bus is acquired by a rider after entering to the waiting are or by a bus.
        A rider can acquire this only if a bus is not arrived at the moment. 
        If a rider acquires this, he releases the mutex for a bus or another rider.
        If a bus acquires this, no more rider can acquire the mutex until the bus leaves.
        */ 
        this.mutex_rider_bus = mutex;
    }

    @Override
    public void run() {

        try {
            // In order to enter the rider-waiting area, should acquire this semaphore. Only 50 allowed at a given time
            waitingAreaEntranceSemaphore.acquire();

                // Entering the boarding area if a bus has not arrived and incrementing the riders count
                mutex_rider_bus.acquire();
                    enterBoardingArea();
                    busStop.incrementRidersCount();
                // Release the mutex for  bus or another waiting rider
                mutex_rider_bus.release();

                /* Acquiring the semaphore to board the bus. This is similar to opening the door of the bus
                The semaphore is first released by the bus to signal the rider/s to board.
                After acquiring the semaphore, rider exits the waiting area and board to the bus
                */
                boardingSemaphore.acquire();
                boardBus();

            // Releasing the wait area semaphore 
            waitingAreaEntranceSemaphore.release();

            // Decrementing the rider count for the bus after each rider gets into the bus
            busStop.decrementRidersCount();

            // When the riders are boarded, allowing the bus to leave by releasing/signaling the bus departure semaphore
            if (busStop.getRidersCount() == 0) {
                busDepartureSemaphore.release();
            }
            // If there are more riders waiting, passing the semaphore/signaling them to get into the bus
            else {
                boardingSemaphore.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void boardBus() {
        System.out.println("Rider id :" + count + " boarded");
    }

    public void enterBoardingArea() {
        System.out.println("Rider id :" + count + " entered boarding area");
    }
}