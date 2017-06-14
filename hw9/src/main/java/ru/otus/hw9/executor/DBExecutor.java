package ru.otus.hw9.executor;


import ru.otus.hw9.exception.DBException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBExecutor<T> {

    private final Connection connection;

    public DBExecutor(Connection connection) {
        this.connection = connection;
    }

    public void save(T dataSet) throws DBException {
        try (Statement stmt = connection.createStatement()) {
            StringBuilder insert = new StringBuilder("insert into ");

            Class clazz = dataSet.getClass();
            insert.append(getTableName(clazz)).append(" ")
                    .append(getQueryParameters(dataSet)).append(";");
            System.out.println(insert.toString());
            stmt.execute(insert.toString());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private <T> String getQueryParameters(T dataSet) {
        Class clazz = dataSet.getClass();

        List<Field> columns = getColumnFields(clazz);
        deleteIdColumn(columns);
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(columns.get(i).getAnnotation(Column.class).name());
        }
        sb.append(") values (");
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            Field field = columns.get(i);
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(dataSet);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            switch (field.getType().getTypeName()) {
                case "java.lang.Long":
                case "java.lang.Integer":
                case "long":
                case "int":
                    sb.append(value);
                    break;
                case "java.lang.String":
                    sb.append("'").append(value).append("'");
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported data type" + field.getType().getTypeName());
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private List<Field> getColumnFields(Class clazz) {
        List<Field> columns = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                columns.add(field);
            }
        }
        return columns;
    }

    private void deleteIdColumn(List<Field> columns) {
        for (Field field : columns) {
            if (field.isAnnotationPresent(Id.class)) {
                columns.remove(field);
                break;
            }
        }
    }

    public T load(long id, Class<T> clazz) throws DBException {
        try (Statement stmt = connection.createStatement()) {
            StringBuilder select = new StringBuilder("select * from ");
            select.append(getTableName(clazz)).append(" where id = ").append(id).append(";");
            System.out.println(select.toString());
            stmt.execute(select.toString());

            Object data = clazz.newInstance();
            ResultSet result = stmt.getResultSet();
            List<Field> columns = getColumnFields(clazz);
            if (result.next()) {
                for (Field field : columns) {
                    field.setAccessible(true);
                    String type = field.getType().getTypeName();
                    switch (type) {
                        case "java.lang.Integer":
                        case "int":
                            field.set(data, result.getInt(field.getAnnotation(Column.class).name()));
                            break;
                        case "java.lang.Long":
                        case "long":
                            field.set(data, result.getLong(field.getAnnotation(Column.class).name()));
                            break;
                        case "java.lang.String":
                            field.set(data, result.getString(field.getAnnotation(Column.class).name()));
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported data type" + field.getType().getTypeName());
                    }
                }
                return (T) data;
            }
            return null;

        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new DBException(e);
        }
    }


    private String getTableName(Class clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        String tableName = "";
        boolean isEntity = false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Table.class)) {
                tableName = ((Table) annotation).name();
            } else if (annotation.annotationType().equals(Entity.class)) {
                isEntity = true;
            }
        }
        if (!isEntity || "".equals(tableName)) {
            throw new IllegalArgumentException("");
        }
        return tableName;
    }

}
