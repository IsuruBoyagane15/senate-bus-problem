import java.lang.*;
/**
 * Main Class for the Senate Bus Problem
 *
 *
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        float meanRiderArivalTime = Float.parseFloat(args[0]) * 1000;
        float meanBusArivalTime = Float.parseFloat(args[1]) * 60 * 1000;
        
//        Scanner scanner = new Scanner(System.in);
//        String userInput;
        BusStop busStop = new BusStop();

//        System.out.println("\n*******  Press any key to exit.  *******\n" );

        RiderGenerator riderGenerator = new RiderGenerator(meanRiderArivalTime, busStop);
//        (new Thread(riderGenerator)).start();
        Thread riderGeneratorThread = new Thread(riderGenerator);
        riderGeneratorThread.start();

        BusGenerator busGenerator = new BusGenerator(meanBusArivalTime,busStop);
//        (new Thread(busGenerator)).start();
        Thread busGeneratorThread = new Thread(busGenerator);
        busGeneratorThread.start();
        // Program Termination with a user input
//        while(true){
//            userInput = scanner.nextLine();
//            if(userInput != null)
//                System.exit(0);
//        }
    }
}
