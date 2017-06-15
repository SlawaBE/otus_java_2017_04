package ru.otus.hw10.dao;

import org.hibernate.Session;
import ru.otus.hw10.dataset.UserDataSet;

/**
 * Created by Stanislav on 14.06.2017
 */
public class UsersDAO {
    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }
}