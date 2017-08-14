package ru.otus.hw14.sorter;

import java.lang.reflect.Array;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Stanislav on 14.08.2017
 */
public class ForkJoinSorter implements Sorter {

    private final int BLOCK_SIZE = 8;

    private int parallelism = 2;

    public ForkJoinSorter() {
    }

    public ForkJoinSorter(int parallelism) {
        this.parallelism = parallelism;
    }

    @Override
    public <T extends Comparable<? super T>> void sort(T[] array) {
        ForkJoinPool pool = new ForkJoinPool(parallelism);
        pool.invoke(new Task(array, 0 , array.length));
    }

    private class Task<T extends Comparable<? super T>> extends RecursiveAction {

        private final T[] array;
        private final int begin;
        private final int end;

        public Task(T[] array, int begin, int end) {
            this.array = array;
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - begin < BLOCK_SIZE) {
                selectionSort(array, begin, end);
            } else {
                int middle = (begin + end) / 2;
                invokeAll(new Task(array, begin, middle), new Task(array, middle, end));
                merge(array, begin, middle, end);
            }

        }

        private void selectionSort(T[] array, int begin, int end) {
            for (int i = begin; i < end - 1; i++) {
                int indexOfMin = i;
                for (int j = i + 1; j < end; j++) {
                    if (array[indexOfMin].compareTo(array[j]) > 0) {
                        indexOfMin = j;
                    }
                }
                if (i != indexOfMin) {
                    T temp = array[indexOfMin];
                    array[indexOfMin] = array[i];
                    array[i] = temp;
                }
            }
        }

        private void merge(T[] array, int begin, int middle, int end) {
            int i = begin;
            int j = middle;
            int k = 0;
            T[] temp = (T[]) Array.newInstance(array[0].getClass(), end - begin);
            while (i < middle && j < end) {
                if (array[i].compareTo(array[j]) <= 0) {
                    temp[k++] = array[i++];
                } else {
                    temp[k++] = array[j++];
                }
            }
            while (i < middle) {
                temp[k++] = array[i++];
            }
            while (j < end) {
                temp[k++] = array[j++];
            }
            System.arraycopy(temp, 0, array, begin, end - begin);
        }
    }
}
