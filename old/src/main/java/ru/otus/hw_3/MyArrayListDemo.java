package ru.otus.hw_3;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by stas on 18.04.17.
 */
public class MyArrayListDemo {

    public static void main(String[] args) {
        MyArrayList<Integer> list1 = new MyArrayList<>();
        Random random = new Random();

        System.out.println("Заполнение");
        for (int i = 0; i < 10; i++) {
            list1.add(random.nextInt());
        }
        printList(list1);

        System.out.println("Заполнение с увеличением размера");
        list1.add(123);
        list1.add(124);
        printList(list1);

        System.out.println("уделение по индексу и содержанию");
        list1.remove(new Integer(123));
        list1.remove(0);
        printList(list1);

        System.out.println("Сортировка");
        Collections.sort(list1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        printList(list1);

        System.out.println("Создание добавлением всего");
        MyArrayList<Integer> list2 = new MyArrayList<>(15);
        Collections.addAll(list2, 1,2,3,4,5,6,7,8,9,10,11,12,13);
        printList(list2);

        System.out.println("Копирование в другой массив");
        Collections.copy(list2, list1);
        printList(list2);

        System.out.println("Очистка");
        System.out.println("ДО: Размер контейнера: " + list1.size() + ", он пуст? " + list1.isEmpty());
        list1.clear();
        System.out.println("ПОСЛЕ: Размер контейнера: " + list1.size() + ", он пуст? " + list1.isEmpty());
    }

    private static void printList(MyArrayList<Integer> list) {
        for (Integer i : list) {
            System.out.print(i + " ");
        }
        System.out.println("\n");
    }

}
