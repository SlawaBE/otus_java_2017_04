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

    Class[] classes;

    public Suite(Class... classes) {
        this.classes = classes;
    }

    public void run() {
        for (Class clazz : classes) {
            runTests(clazz);
        }
    }

    private void runTests(Class clazz) {
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

                ReflectionHelper.callMethod(obj, method.getName());

            } catch (AssertException e) {
                System.out.println("FAIL");
            } catch (Exception e) {
                e.printStackTrace();
            }

            executeAll(before, obj);
        }

    }

    private void executeAll(Object[] methods, Object obj) {
        if (methods != null) {
            for (Object o : methods) {
                Method method = (Method) o;
                ReflectionHelper.callMethod(obj, method.getName());
            }
        }
    }

}
