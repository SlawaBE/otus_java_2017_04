package ru.otus.hw5;

/*
    Created by Stanislav on 10.05.2017
*/

import ru.otus.hw5.mytestframework.Suite;
import ru.otus.hw5.tests.CounterTest;
import ru.otus.hw5.tests.MyStrangeClassTest;

public class Main {

    public static void main(String[] args) {
        Suite suite = new Suite(CounterTest.class, MyStrangeClassTest.class);
        suite.run();
    }

}
