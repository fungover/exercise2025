package ExerciseTwo.Utils;

import java.util.Random;

public class RandomGenerator {

    public int getRandomNumber(int number){
        if(number <= 0){
            throw new IllegalArgumentException("Number must be greater than 0");
        }

        Random rand = new Random();
        return rand.nextInt(number);
    }

}
