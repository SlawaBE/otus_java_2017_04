package ru.otus.hw14;

import ru.otus.hw14.sorter.ForkJoinSorter;
import ru.otus.hw14.sorter.Sorter;
import ru.otus.hw14.utils.ArrayGenerator;

/**
 * Created by Stanislav on 14.08.2017
 */
public class Main {

    private static final int COUNT = 10_000_000;
    private static final int THREADS = 4;

    public static void main(String[] args) {
        Sorter sorter = new ForkJoinSorter(THREADS);
        Integer[] array = ArrayGenerator.generateRandomIntegerArray(COUNT);

        System.out.println("Сортировка массива случайных чисел");
        System.out.println("Размер массива: " + COUNT);
        System.out.println("Число потоков: " + THREADS);

        long start = System.currentTimeMillis();
        sorter.sort(array);
        long end = System.currentTimeMillis();

        System.out.println("Затрачено времени: " + (end - start) + "мс");
    }

}
