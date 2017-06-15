package ru.otus.hw10;

import ru.otus.hw10.database.DBService;
import ru.otus.hw10.database.DBServiceHibernateImpl;
import ru.otus.hw10.dataset.AddressDataSet;
import ru.otus.hw10.dataset.PhoneDataSet;
import ru.otus.hw10.dataset.UserDataSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Stanislav on 14.06.2017
 */
public class Main {

    /**
     * mysql> CREATE USER stas@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_stas;
     * mysql> GRANT ALL PRIVILEGES ON db_stas.* TO stas@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */

    public static void main(String[] args) {
        DBService dbService = new DBServiceHibernateImpl();

        System.out.println("\nВыполняется запись данных в базу");
        AddressDataSet address = new AddressDataSet("Dangeon st.", 10);
        Set<PhoneDataSet> phones = new HashSet<>();
        phones.add(new PhoneDataSet("8-800-000-00-00", 7));
        phones.add(new PhoneDataSet("8-8172-88-88-88", 7));
        UserDataSet user = new UserDataSet("Manchkin", phones, address);

        System.out.println(user);
        dbService.save(user);

        address = new AddressDataSet("Vologda", 1);
        phones = new HashSet<>();
        phones.add(new PhoneDataSet("11-11-11", 2));
        user = new UserDataSet("Dmitry", phones, address);

        System.out.println(user);
        dbService.save(user);

        System.out.println("\nВыполняется чтение данных из базы");
        System.out.println(dbService.load(1));
        System.out.println(dbService.load(2));

        dbService.shutdown();
    }
}
