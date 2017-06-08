package ru.otus.hw9.executor;


import ru.otus.hw9.handler.ResultHandler;
import ru.otus.hw9.handler.TResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleExecutor {

    private final Connection connection;

    public SimpleExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException {
        T value;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            value = handler.handle(result);
        }
        return value;
    }

    public void execCommand(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
        }
    }


}
