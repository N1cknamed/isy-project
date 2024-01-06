package gui;

import java.util.Random;

public class RandomAI {
    public int[] generateRandomMove() {
        Random random = new Random();
        int row = random.nextInt(10);
        int col = random.nextInt(10);
        return new int[]{row, col};
    }
}
