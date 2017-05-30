package ru.otus.hw5.tests;

/*
    Created by Stanislav on 11.05.2017
*/

import ru.otus.hw5.classes.MyStrangeClass;
import ru.otus.hw5.mytestframework.annotations.After;
import ru.otus.hw5.mytestframework.annotations.Before;
import ru.otus.hw5.mytestframework.annotations.Test;
import ru.otus.hw5.mytestframework.asserts.Assert;

public class MyStrangeClassTest {

    @Before
    public void before() {
        System.out.println("before method");
    }

    @Test
    public void getNullTest() {
        Object o = MyStrangeClass.getNull();
        Assert.assertNotNull(o);
    }

    @Test
    public void getNullTest2() {
        Object o = MyStrangeClass.getNull();
        if (o != null) {

        }
    }

    @After
    public void after() {
        System.out.println("after method");
    }

}
