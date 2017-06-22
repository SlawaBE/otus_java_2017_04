package ru.otus.hw11.service;

import ru.otus.hw11.dataset.UserDataSet;

/**
 * Created by Stanislav on 15.06.2017
 */
public interface DBService {

    void save(UserDataSet user);

    UserDataSet load(long id);

    void shutdown();
}
