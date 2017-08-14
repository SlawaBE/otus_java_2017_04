package ru.otus.hw14.sorter;

/**
 * Created by Stanislav on 14.08.2017
 */
public interface Sorter {

    <T extends Comparable<? super T>> void sort(T[] array);

}
