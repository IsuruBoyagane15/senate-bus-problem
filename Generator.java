import java.util.Random;

/**
 * Super class of thread generators
 *
 */
public class Generator {

    private static final Random RANDOM =  new Random();

    public long getWaitingTime(float arrivalMeanTime){
        float lambda = 1 / arrivalMeanTime;
        return Math.round(-Math.log(1 - RANDOM.nextFloat()) / lambda);
    }
}
