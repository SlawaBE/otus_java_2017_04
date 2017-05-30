package ru.otus.hw5.mytestframework.asserts;

/*
    Created by Stanislav on 10.05.2017
*/

public class Assert {

    private Assert() {}

    public static void assertTrue(Boolean actual) {
        if (!actual) {
            throw new AssertException("your value is false, but true expected");
        }
    }

    public static void assertNotNull(Object actual) {
        if (actual == null) {
            throw new AssertException("your value is null, but not null expected");
        }
    }

    public static void assertFail() {
        throw new AssertException("this is fail");
    }

}
