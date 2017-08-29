package ru.otus.hw5.mytestframework;

/*
    Created by Stanislav on 10.05.2017
*/

import ru.otus.hw5.mytestframework.annotations.After;
import ru.otus.hw5.mytestframework.annotations.Before;
import ru.otus.hw5.mytestframework.annotations.Test;
import ru.otus.hw5.mytestframework.asserts.AssertException;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Suite {

    private Class[] classes;

    public Suite(Class... classes) {
        this.classes = classes;
    }

    public void run() {
        System.out.println("========================\nMy Test Framework\n========================");
        for (Class clazz : classes) {
            runTests(clazz);
        }
    }

    private void runTests(Class clazz) {
        System.out.println("Test suite: " + clazz.getName());
        Statistics statistics = new Statistics();

        Object[] before = Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(Before.class)).toArray();
        Object[] methods = Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(Test.class)).toArray();
        Object[] after = Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(After.class)).toArray();


        for (Object o : methods) {
            Object obj = ReflectionHelper.instantiate(clazz);
            Method method = (Method) o;
            executeAll(before, obj);

            try {
                statistics.incrementTotal();
                System.out.print("\tmethod " + method.getName());
                ReflectionHelper.callMethod(obj, method.getName());
                System.out.println(" SUCCESS");
                statistics.incrementSuccess();
            } catch (AssertException e) {
                System.out.println(" FAIL");
                statistics.incrementFail();
            } catch (Exception e) {
                e.printStackTrace();
                statistics.incrementFail();
            }

            executeAll(after, obj);
        }

        printStatistics(statistics);

    }

    private void executeAll(Object[] methods, Object obj) {
        if (methods != null) {
            for (Object o : methods) {
                Method method = (Method) o;
                try {
                    ReflectionHelper.callMethod(obj, method.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void printStatistics(Statistics statistics) {
        System.out.println("\nTotal: " + statistics.getTotal()
                + ", Success: " + statistics.getSuccess()
                + ", Fail: " + statistics.getFail());
        System.out.println();

    }

}
