package ru.otus.hw10.database;

import ru.otus.hw10.dataset.UserDataSet;

/**
 * Created by Stanislav on 14.06.2017
 */
public interface DBService {

    void save(UserDataSet user);

    UserDataSet load(long id);

    void shutdown();

}
