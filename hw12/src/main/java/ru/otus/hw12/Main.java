package ru.otus.hw12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw12.cache.CacheEngine;
import ru.otus.hw12.cache.MyCacheImpl;
import ru.otus.hw12.dataset.UserDataSet;
import ru.otus.hw12.service.AuthorizationService;
import ru.otus.hw12.service.DBService;
import ru.otus.hw12.service.DBServiceImpl;
import ru.otus.hw12.servlet.AdminServlet;
import ru.otus.hw12.servlet.DataBaseServlet;
import ru.otus.hw12.servlet.LoginServlet;

/**
 * Created by Stanislav on 27.06.2017
 */
public class Main {

    /**
     * mysql> CREATE USER stas@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_stas;
     * mysql> GRANT ALL PRIVILEGES ON db_stas.* TO stas@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */

    private static final int PORT = 8080;
    private static final String PUBLIC_HTML = "public_html";
    public static final String ADMIN_PAGE = "/admin";
    public static final String LOGIN_PAGE = "/login";
    public static final String DATABASE_PAGE = "/db";

    private static CacheEngine<Long, UserDataSet> cache;
    private static DBService dbService;
    private static AuthorizationService authService;
    private static Server server;

    public static void main(String[] args) throws Exception {
        initServices();

        initServer();

        server.start();
        System.out.println("Сервер запущен");
        server.join();
    }

    private static void initServices() {
        cache = new MyCacheImpl<>(10, 10000, 10000);
        dbService = new DBServiceImpl(cache);
        authService = new AuthorizationService();
    }

    private static void initServer() {
        AdminServlet adminServlet = new AdminServlet(cache, authService);
        LoginServlet loginServlet = new LoginServlet(authService);
        DataBaseServlet dataBaseServlet = new DataBaseServlet(dbService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(adminServlet), ADMIN_PAGE);
        context.addServlet(new ServletHolder(loginServlet), LOGIN_PAGE);
        context.addServlet(new ServletHolder(dataBaseServlet), DATABASE_PAGE);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));
    }

}
