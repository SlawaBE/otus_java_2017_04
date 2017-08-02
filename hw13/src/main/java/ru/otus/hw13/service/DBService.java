package ru.otus.hw13.service;

import ru.otus.hw13.dataset.UserDataSet;

/**
 * Created by Stanislav on 15.06.2017
 */
public interface DBService {

    void save(UserDataSet user);

    UserDataSet load(long id);

    void shutdown();
}
