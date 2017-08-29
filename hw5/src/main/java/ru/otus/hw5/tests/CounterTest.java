package ru.otus.hw5.tests;

/*
    Created by Stanislav on 11.05.2017
*/

import ru.otus.hw5.classes.Counter;
import ru.otus.hw5.mytestframework.annotations.After;
import ru.otus.hw5.mytestframework.annotations.Before;
import ru.otus.hw5.mytestframework.annotations.Test;
import ru.otus.hw5.mytestframework.asserts.Assert;

import java.lang.reflect.Field;

public class CounterTest {

    public static final int TEST_NUMBER = 10;
    Counter counter;

    @Before
    public void initCounter() throws NoSuchFieldException, IllegalAccessException {
        counter = new Counter();
        Field field = counter.getClass().getDeclaredField("count");
        field.setAccessible(true);
        field.set(counter, TEST_NUMBER);
    }

    @Test
    public void testIncrement() throws NoSuchFieldException, IllegalAccessException {
        counter.increment();
        Field field = counter.getClass().getDeclaredField("count");
        field.setAccessible(true);
        int value = field.getInt(counter);
        Assert.assertTrue(value == TEST_NUMBER + 1 );
    }

    @Test
    public void testGetCount() throws NoSuchFieldException, IllegalAccessException {
        Field field = counter.getClass().getDeclaredField("count");
        field.setAccessible(true);
        int value = field.getInt(counter);
        Assert.assertTrue(value == TEST_NUMBER);
    }

    @After
    public void disposeCounter() {
        counter = null;
    }

}
