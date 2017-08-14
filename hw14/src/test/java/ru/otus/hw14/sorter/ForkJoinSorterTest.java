package ru.otus.hw14.sorter;

import org.junit.Test;
import ru.otus.hw14.utils.ArrayGenerator;

import static org.junit.Assert.fail;


/**
 * Created by Stanislav on 15.08.2017
 */
public class ForkJoinSorterTest {

    @Test
    public void sort() throws Exception {
        ForkJoinSorter sorter = new ForkJoinSorter();
        Integer[] array = ArrayGenerator.generateRandomIntegerArray(1000);

        sorter.sort(array);

        verifySort(array);
    }

    private void verifySort(Integer[] a) {
        int x = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < x) {
                fail();
            }
            x = a[i];
        }
    }

}