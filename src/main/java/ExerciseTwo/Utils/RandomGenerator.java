package ExerciseTwo.Utils;

import java.util.Random;

public class RandomGenerator {

    public int getRandomNumber(int number){
        Random rand = new Random();
        return rand.nextInt(number);
    }

}
