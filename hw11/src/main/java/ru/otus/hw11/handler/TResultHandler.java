package ru.otus.hw11.handler;


import java.sql.ResultSet;
import java.sql.SQLException;


public interface TResultHandler<T> {

    T handle(ResultSet result) throws SQLException;

}
