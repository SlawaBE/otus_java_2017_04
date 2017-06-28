package ru.otus.hw12.service;

import ru.otus.hw12.dataset.UserDataSet;

/**
 * Created by Stanislav on 15.06.2017
 */
public interface DBService {

    void save(UserDataSet user);

    UserDataSet load(long id);

    void shutdown();
}
