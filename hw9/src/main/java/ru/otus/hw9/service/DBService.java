package ru.otus.hw9.service;


import ru.otus.hw9.dataset.User;
import ru.otus.hw9.exception.DBException;
import ru.otus.hw9.executor.DBExecutor;
import ru.otus.hw9.executor.SimpleExecutor;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBService {

    private Connection connection;

    public DBService() throws DBException {
        connection = getMySqlConnection();
    }

    public void init() throws DBException {
        try {
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
                    .append("10.1.1.1:")
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

    public void addUser(String name, int age) throws SQLException {
        SimpleExecutor exec = new SimpleExecutor(connection);
        exec.execCommand("insert into users (name, age) values ('" + name + "'," + age + ");");
    }

    public User getUser(long id) throws SQLException {
        SimpleExecutor exec = new SimpleExecutor(connection);
        return exec.execQuery("select * from users where id = " + id, result -> {
            result.next();
            return new User(result.getLong(1), result.getString(2), result.getInt(3));
        });
    }

    public List<User> getAllUsers() throws SQLException {
        SimpleExecutor exec = new SimpleExecutor(connection);
        return exec.execQuery("select * from users;", result -> {
            List<User> users = new ArrayList<>();
            while (result.next()) {
                users.add(new User(result.getLong(1), result.getString(2), result.getInt(3)));
            }
            return users;
        });
    }

    public void save(User user) throws DBException {
        DBExecutor<User> exec = new DBExecutor<>(connection);
        exec.save(user);
    }

    public User load(long id) throws DBException {
        DBExecutor<User> exec = new DBExecutor<>(connection);
        return exec.load(id, User.class);
    }

    public void cleanUp() throws DBException {
        try {
            new SimpleExecutor(connection).execCommand("drop table users;");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
