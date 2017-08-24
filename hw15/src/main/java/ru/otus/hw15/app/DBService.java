package ru.otus.hw15.app;

import ru.otus.hw15.app.dataset.UserDataSet;
import ru.otus.hw15.app.dto.CacheInfo;

/**
 * Created by Stanislav on 15.06.2017
 */
public interface DBService {

    void save(UserDataSet user);

    UserDataSet load(long id);

    CacheInfo getCacheInfo();

    void shutdown();
}
