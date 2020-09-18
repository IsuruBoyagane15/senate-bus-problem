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
    private final Semaphore mutex;
    ;

    public Rider(Semaphore waitingAreaEntranceSemaphore, Semaphore boardingSemaphore, Semaphore busDepartureSemaphore, Semaphore mutex, int count, BusStop busStop) {
        this.count = count;
        this.busStop = busStop;
        this.waitingAreaEntranceSemaphore = waitingAreaEntranceSemaphore;
        this.boardingSemaphore = boardingSemaphore;
        this.busDepartureSemaphore = busDepartureSemaphore;
        this.mutex = mutex;
    }

    @Override
    public void run() {

        try {
            // Acquiring the semaphore in trying to enter the rider waiting area, only 50 allowed at a given time
            waitingAreaEntranceSemaphore.acquire();

                // Entering the boarding area and incrementing the riders count
                mutex.acquire();
                    enterBoardingArea();
                    busStop.incrementRidersCount();
                mutex.release();

                //Acquiring the semaphore to board the bus
                boardingSemaphore.acquire();
                boardBus();

            // Releasing the semaphore for enter waiting area
            waitingAreaEntranceSemaphore.release();

            // Decrementing the rider count once boarded
            busStop.decrementRidersCount();

            // When the riders are boarded, allowing the bus to depart by releasing the bus departure semaphore
            if (busStop.getRidersCount() == 0) {
                busDepartureSemaphore.release();
            }
            // If there are more riders waiting, allowing them to get into the bus
            else {
                boardingSemaphore.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void boardBus() {
        System.out.println("Rider :" + count + " boarded");
    }

    public void enterBoardingArea() {
        System.out.println("Rider :" + count + " entered boarding area");
    }
}