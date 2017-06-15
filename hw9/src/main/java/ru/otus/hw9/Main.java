package ru.otus.hw9;

import ru.otus.hw9.dataset.UserDataSet;
import ru.otus.hw9.exception.DBException;
import ru.otus.hw9.service.DBService;
import ru.otus.hw9.service.DBServiceImpl;

public class Main {

    /**
     * mysql> CREATE USER stas@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_stas;
     * mysql> GRANT ALL PRIVILEGES ON db_stas.* TO stas@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */

    private static DBService dbService;

    public static void main(String[] args) {
        init();

        insertData();

        loadData();

        cleanUp();
    }

    private static void init() {
        try {
            System.out.println("Подключение к базе");
            dbService = new DBServiceImpl();
            System.out.println("Создание таблицы users");
        } catch (DBException e) {
            System.out.println("Сбой инициализации");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void insertData() {
        System.out.println("\nВыполняется вставка данных");
        try {
            dbService.save(new UserDataSet("first", 10));
            dbService.save(new UserDataSet("second", 8));
            dbService.save(new UserDataSet("third", 6));
        } catch (DBException e) {
            System.out.println("Не удалось выполнить вставку данных");
        }
    }

    private static void loadData() {
        System.out.println("\nЗагрузка данных из таблицы users");
        try {
            System.out.println(dbService.load(1L));
            System.out.println(dbService.load(2L));
            System.out.println(dbService.load(3L));
        } catch (DBException e) {
            System.out.println("Не удалось выполнить загрузку данных");
        }
    }

    private static void cleanUp(){
        try {
            System.out.println("\nВыполняется очистка");
            ((DBServiceImpl) dbService).cleanUp();
            dbService.shutdown();
        } catch (DBException e) {
            System.out.println("Не удалось выполнить очистку");
            e.printStackTrace();
        }
    }

}
