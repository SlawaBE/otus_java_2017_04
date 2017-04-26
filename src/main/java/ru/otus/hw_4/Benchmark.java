package ru.otus.hw_4;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by stas on 25.04.17.
 */
//starts with start_hw4.sh
public class Benchmark {

    private static final int ITEMS = 1_000_000;

    public static void main(String[] args) throws Exception {
        GCMonitor gcMon = new GCMonitor();
        gcMon.startGCMonitor();
        run();
    }

    /**
     * Метод создающий утечку памяти
     */
    public static void run() {
        List<Integer> list;
        list = new LinkedList<>();
        while (true) {
            for (int i = 0; i < ITEMS; i++) {
                list.add(i);
            }
            for (int i = 0; i < ITEMS / 2; i++) {
                list.remove(0);
            }
        }
    }

}
