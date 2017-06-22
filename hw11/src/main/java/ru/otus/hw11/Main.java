package ru.otus.hw11;

import ru.otus.hw11.cache.MyCacheImpl;
import ru.otus.hw11.dataset.UserDataSet;
import ru.otus.hw11.exception.DBException;
import ru.otus.hw11.service.DBService;
import ru.otus.hw11.service.DBServiceImpl;

import java.util.Random;

/**
 * Created by Stanislav on 21.06.2017
 */
public class Main {

    /**
     * mysql> CREATE USER stas@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_stas;
     * mysql> GRANT ALL PRIVILEGES ON db_stas.* TO stas@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */

    private static DBService dbService;

    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        init();

        System.out.println("\nВыполняется вставка данных");
        insertData();

        System.out.println("\nЗагрузка данных из таблицы users");
        loadData();

        System.out.println("\nНебольшшое ожидание");
        Thread.sleep(500);

        System.out.println("\nЧтение по случайным id");
        readThreeDataSetWithRandomId();

        System.out.println("\nЖдём некоторое время");
        Thread.sleep(1000);

        System.out.println("\nПовторное чтение всех данных");
        loadData();

        System.out.println("\nВыполняется очистка");
        cleanUp();
    }

    private static void init() {
        try {
            System.out.println("Подключение к базе");
//            dbService = new DBServiceImpl();
            dbService = new DBServiceImpl(new MyCacheImpl<>(10, 2000, 1000));
            System.out.println("Создание таблицы users");
        } catch (DBException e) {
            System.out.println("Сбой инициализации");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void insertData() {
        try {
            dbService.save(new UserDataSet("Kris", 10));
            dbService.save(new UserDataSet("Terminator", 256));
            dbService.save(new UserDataSet("Robert", 51));
            dbService.save(new UserDataSet("Tom" , 20));
            dbService.save(new UserDataSet("Jerry", 15));
            dbService.save(new UserDataSet("Snow", 17));
        } catch (DBException e) {
            System.out.println("Не удалось выполнить вставку данных");
        }
    }

    private static void loadData() throws InterruptedException {
        try {
            for (int i = 1; i <= 6; i++) {
                System.out.println(dbService.load((long) i));
            }
        } catch (DBException e) {
            System.out.println("Не удалось выполнить загрузку данных");
        }
    }

    private static void readThreeDataSetWithRandomId() throws InterruptedException {
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println(dbService.load(random.nextInt(5)+1));
            }
        } catch (DBException e) {
            System.out.println("Не удалось выполнить загрузку данных");
        }
    }

    private static void cleanUp(){
        try {
            ((DBServiceImpl) dbService).cleanUp();
            dbService.shutdown();
        } catch (DBException e) {
            System.out.println("Не удалось выполнить очистку");
            e.printStackTrace();
        }
    }

}
