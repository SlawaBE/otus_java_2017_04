package ru.otus.hw_2;

import java.lang.management.ManagementFactory;

/**
 * Created by stas on 09.04.17.
 */

//параметры для запуска jvm: -Xmx512m -Xms512m
public class ObjectMeter {

    private static final int ITEMS_COUNT = 10_000_000;
    private static final int TEST_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        System.out.println("\nИзмерение размера пустой строки и массива\n");
        measureEmptyStringSize();

        System.out.println("\nЗависимость размера массива от количества элементов\n");
        measureArraySize();

    }

    private static void measureEmptyStringSize() {
        final Runtime runtime = Runtime.getRuntime();
        long sumElementSize = 0;
        long sumArraySize = 0;

        for (int i = 0; i < TEST_COUNT; i++) {
            System.out.println("\nTest " + i);

            long initialFreeMemSize = runtime.freeMemory();
            Object[] array = new Object[ITEMS_COUNT];
            long emptyArrayFreeMemSize = runtime.freeMemory();

            int n = 0;
            for (int j = 0 ; j < ITEMS_COUNT; j++) {
                array[j] = new String();
            }
            long arrayFreeMemSize = runtime.freeMemory();

            array = null;
            System.gc();

            long arrayMemSize = initialFreeMemSize - emptyArrayFreeMemSize;
            long fillArrayMemSize = initialFreeMemSize - arrayFreeMemSize;
            System.out.println("\tПамять занимаемая пустым массивом: " + arrayMemSize);
            System.out.println("\tПамять занимаемая заполненным массивом: " + fillArrayMemSize);
            long elementsSize = fillArrayMemSize - arrayMemSize;
            long elementSize = Math.round(1.*elementsSize / ITEMS_COUNT);
            System.out.println("\tРазмер хранимого элемента: " + elementSize);

            sumElementSize += elementSize;
            sumArraySize += arrayMemSize;
            sleep();
        }
        System.out.println("\n==========================================================\n");
        System.out.println("Размер пустой строки (String): " + Math.round(1.*sumElementSize / TEST_COUNT) + " байт.");
        System.out.println("Размер незаполненного контейнера из " + ITEMS_COUNT +
                " элементов: " + Math.round(1.*sumArraySize / TEST_COUNT) + " байт.");
    }

    private static void measureArraySize() {
        final Runtime runtime = Runtime.getRuntime();
        Object obj;
        for (int i = 0; i < ITEMS_COUNT; i += 1_000_000) {
            long initialMemory = runtime.freeMemory();
            obj = new Object[i];
            long arraySize = runtime.freeMemory();
            System.out.println("Занимаемая массивом память: "+ (initialMemory - arraySize) + " байт.");
            obj = null;
            System.gc();
            sleep();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
