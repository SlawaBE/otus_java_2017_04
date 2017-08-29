package ru.otus.hw5.tests;

/*
    Created by Stanislav on 11.05.2017
*/

import ru.otus.hw5.classes.MyStrangeClass;
import ru.otus.hw5.mytestframework.annotations.Test;
import ru.otus.hw5.mytestframework.asserts.Assert;

public class MyStrangeClassTest {

    @Test
    public void testGetNull() {
        Object o = MyStrangeClass.getNull();
        Assert.assertNull(o);
    }

    @Test
    public void testGetObject() {
        Object o = MyStrangeClass.getObject();
        Assert.assertNotNull(o);
    }

    @Test
    public void testSpecialFail() {
        Assert.assertFail();
    }

}
