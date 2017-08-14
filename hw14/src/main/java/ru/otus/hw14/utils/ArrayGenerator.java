package ru.otus.hw14.utils;

import java.util.Random;

/**
 * Created by Stanislav on 15.08.2017
 */
public abstract class ArrayGenerator {

    public static Integer[] generateRandomIntegerArray(int capacity) {
        Random random = new Random();
        Integer[] array = new Integer[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

}
