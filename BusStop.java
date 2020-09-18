import java.util.concurrent.Semaphore;

/**
 * Class to keep track of the riders waiting area including the riders count
 */
public class BusStop {

    private static int ridersCount = 0;
    private static final int maximumBusCapacity = 50;

    // Semaphore used for riders to enter the waiting area, allowing 50 riders to remain at the waiting area
    private static final Semaphore waitingAreaEntranceSemaphore = new Semaphore(maximumBusCapacity);

    // Semaphore used for riders to enter the boarding area
    private static final Semaphore boardingAreaEntranceSemaphore = new Semaphore(0);

    // Semaphore used for bus to depart after the riders are boarded
    private static final Semaphore busDepartureSemaphore = new Semaphore(0);

    // Semaphore used to handle the access to the riders count variable
    private static final Semaphore mutex = new Semaphore(1);

    //Method to get the riders count
    public int getRidersCount() {
        return ridersCount;
    }

    //Method to increment the riders count
    public static void incrementRidersCount() {
        ridersCount++;
    }

    //Method to decrement the riders count
    public static void decrementRidersCount() {
        ridersCount--;
    }

    //Method to access the semaphore that is used for riders to enter the waiting area
    public static Semaphore getWaitingAreaEntranceSemaphore() {
        return waitingAreaEntranceSemaphore;
    }

    //Method to access the semaphore that is used for riders to board the bus
    public static Semaphore getBoardingAreaEntranceSemaphore() {
        return boardingAreaEntranceSemaphore;
    }

    //Method to access the semaphore that is used for the bus to depart
    public static Semaphore getBusDepartureSemaphore() {
        return busDepartureSemaphore;
    }

    //Method to access the semaphore that is used for handle access to the riders count
    public static Semaphore getMutex() {
        return mutex;
    }
}
