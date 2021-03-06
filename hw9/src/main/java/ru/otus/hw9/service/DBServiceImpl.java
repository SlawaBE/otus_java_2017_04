package ru.otus.hw9.service;


import ru.otus.hw9.dataset.UserDataSet;
import ru.otus.hw9.exception.DBException;
import ru.otus.hw9.executor.DBExecutor;
import ru.otus.hw9.executor.SimpleExecutor;
import ru.otus.hw9.factory.DataSetDescriptionFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {

    private Connection connection;
    private DataSetDescriptionFactory descriptionFactory;

    public DBServiceImpl() throws DBException {
        connection = getMySqlConnection();
        descriptionFactory = DataSetDescriptionFactory.getInstance();
        init();
    }

    public void init() throws DBException {
        try {
            descriptionFactory.addAnnotatedClass(UserDataSet.class);
            SimpleExecutor exec = new SimpleExecutor(connection);
            exec.execCommand("create table if not exists users (id bigint(20) auto_increment primary key, name varchar(255), age int(3))");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static Connection getMySqlConnection() throws DBException {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.append("jdbc:mysql://")
                    .append("localhost:")
                    .append("3306/")
                    .append("db_stas?")
                    .append("user=stas&")
                    .append("password=Pa$$w0rd")
                    .append("&useLegacyDatetimeCode=false&amp&serverTimezone=UTC");

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void save(UserDataSet user) {
        DBExecutor<UserDataSet> exec = new DBExecutor<>(connection);
        exec.save(user);
    }

    @Override
    public UserDataSet load(long id) {
        DBExecutor<UserDataSet> exec = new DBExecutor<>(connection);
        return exec.load(id, UserDataSet.class);
    }

    public void cleanUp() throws DBException {
        try {
            new SimpleExecutor(connection).execCommand("drop table users;");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
