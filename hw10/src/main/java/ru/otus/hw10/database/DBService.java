package ru.otus.hw10.database;

import ru.otus.hw10.dataset.UserDataSet;

/**
 * Created by Stanislav on 14.06.2017
 */
public interface DBService {

    public void save(UserDataSet user);

    public UserDataSet load(long id);

    public void shutdown();

}
