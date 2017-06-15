package ru.otus.hw10.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw10.dao.UsersDAO;
import ru.otus.hw10.dataset.AddressDataSet;
import ru.otus.hw10.dataset.PhoneDataSet;
import ru.otus.hw10.dataset.UserDataSet;


/**
 * Created by Stanislav on 14.06.2017
 */
public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_stas");
        configuration.setProperty("hibernate.connection.username", "stas");
        configuration.setProperty("hibernate.connection.password", "Pa$$w0rd");
        configuration.setProperty("hibernate.connection.serverTimezone", "UTC");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }


    @Override
    public void save(UserDataSet user) {
        try (Session session = sessionFactory.openSession()) {
            UsersDAO dao = new UsersDAO(session);
            dao.save(user);
        }
    }

    @Override
    public UserDataSet load(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            UserDataSet user = dao.read(id);
            transaction.commit();
            return user;
        }
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }
}
