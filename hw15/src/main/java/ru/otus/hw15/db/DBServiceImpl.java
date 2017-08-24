package ru.otus.hw15.db;


import ru.otus.hw15.app.CacheEngine;
import ru.otus.hw15.app.DBService;
import ru.otus.hw15.app.dataset.UserDataSet;
import ru.otus.hw15.app.dto.CacheInfo;
import ru.otus.hw15.db.exception.DBException;
import ru.otus.hw15.db.executor.DBExecutor;
import ru.otus.hw15.db.executor.SimpleExecutor;
import ru.otus.hw15.db.factory.DataSetDescriptionFactory;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.Addressee;
import ru.otus.hw15.messageSystem.MessageSystemContext;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBServiceImpl implements DBService, Addressee {

    private Connection connection;
    private DataSetDescriptionFactory descriptionFactory;
    private CacheEngine<Long, UserDataSet> cache;
    private Address address;

    public DBServiceImpl() throws DBException {
        this.connection = getMySqlConnection();
        this.descriptionFactory = DataSetDescriptionFactory.getInstance();
        this.cache = new MyCacheImpl<>(10, 1000, 1000);
        init();
    }

    public DBServiceImpl(MessageSystemContext messageSystemContext, CacheEngine<Long, UserDataSet> cache) throws DBException {
        this.connection = getMySqlConnection();
        this.descriptionFactory = DataSetDescriptionFactory.getInstance();
        this.cache = cache;
        address = messageSystemContext.getDbAddress();
        messageSystemContext.getMessageSystem().addAdressee(this);
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
        UserDataSet user = cache.get(id);
        if (user != null) {
            System.out.println("Чтение из кэша по id = " + id);
            return user;
        }
        DBExecutor<UserDataSet> exec = new DBExecutor<>(connection);
        user = exec.load(id, UserDataSet.class);
        if (user != null) {
            cache.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
            cache.dispose();
            System.out.println("Статистика использования кэша:");
            System.out.println(" Попаданий: " + cache.getHit());
            System.out.println(" Промахов:  " + cache.getMiss());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public CacheInfo getCacheInfo() {
        return new CacheInfo(cache.size(), cache.getHit(), cache.getMiss());
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void cleanUp() throws DBException {
        try {
            new SimpleExecutor(connection).execCommand("drop table users;");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
