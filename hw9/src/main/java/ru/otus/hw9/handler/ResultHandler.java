package ru.otus.hw9.handler;


import java.sql.ResultSet;
import java.sql.SQLException;


public interface ResultHandler{

    void handle(ResultSet result) throws SQLException;

}
