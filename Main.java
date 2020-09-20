import java.lang.*;
/**
 * Main Class for the Senate Bus Problem
 *
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        float riderArrivalMeanTime = Float.parseFloat(args[0]) * 1000;
        float busArrivalMeanTime = Float.parseFloat(args[1]) * 60 * 1000;

        BusStop busStop = new BusStop();

        RiderGenerator riderGenerator = new RiderGenerator(riderArrivalMeanTime, busStop);
        Thread riderGeneratorThread = new Thread(riderGenerator);
        riderGeneratorThread.start();

        BusGenerator busGenerator = new BusGenerator(busArrivalMeanTime,busStop);
        Thread busGeneratorThread = new Thread(busGenerator);
        busGeneratorThread.start();

    }
}
