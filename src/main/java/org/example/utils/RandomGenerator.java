package org.example.utils;

public class RandomGenerator {
    public int generateNumber(int minSize, int maxSize) {
        return (int) (Math.random() * (maxSize-minSize+1)) + minSize;
    }
}
