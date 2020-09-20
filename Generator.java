import java.util.Random;

public class Generator {

    private static final Random RANDOM =  new Random();

    public long getWaitingTime(float meanArivalTime){
        float lambda = 1 / meanArivalTime;
        return Math.round(-Math.log(1 - RANDOM.nextFloat()) / lambda);
    }
}
