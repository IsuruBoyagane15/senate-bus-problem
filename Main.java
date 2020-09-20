import java.lang.*;
/**
 * Main Class for the Senate Bus Problem
 *
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        float meanRiderArivalTime = Float.parseFloat(args[0]) * 1000;
        float meanBusArivalTime = Float.parseFloat(args[1]) * 60 * 1000;

        BusStop busStop = new BusStop();

        RiderGenerator riderGenerator = new RiderGenerator(meanRiderArivalTime, busStop);
        Thread riderGeneratorThread = new Thread(riderGenerator);
        riderGeneratorThread.start();

        BusGenerator busGenerator = new BusGenerator(meanBusArivalTime,busStop);
        Thread busGeneratorThread = new Thread(busGenerator);
        busGeneratorThread.start();

    }
}
