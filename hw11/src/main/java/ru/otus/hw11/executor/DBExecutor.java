package ru.otus.hw11.executor;


import ru.otus.hw11.exception.DBException;
import ru.otus.hw11.factory.DataSetDescription;
import ru.otus.hw11.factory.DataSetDescriptionFactory;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBExecutor<T> {

    private final Connection connection;

    public DBExecutor(Connection connection) {
        this.connection = connection;
    }

    public void save(T dataSet) throws DBException {
        try (Statement stmt = connection.createStatement()) {
            StringBuilder insert = new StringBuilder("insert into ");

            DataSetDescription description = DataSetDescriptionFactory.getInstance().getDescription(dataSet.getClass());

            insert.append(description.getTableName()).append(" ")
                    .append(getQueryParameters(dataSet, description.getInsertFields())).append(";");
            System.out.println(insert.toString());
            stmt.execute(insert.toString());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private <T> String getQueryParameters(T dataSet, List<Field> fields) {

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(fields.get(i).getAnnotation(Column.class).name());
        }
        sb.append(") values (");
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            Field field = fields.get(i);
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

    public T load(long id, Class<T> clazz) throws DBException {
        try (Statement stmt = connection.createStatement()) {
            DataSetDescription description = DataSetDescriptionFactory.getInstance().getDescription(clazz);

            StringBuilder select = new StringBuilder("select * from ");
            select.append(description.getTableName()).append(" where id = ").append(id).append(";");
            System.out.println(select.toString());
            stmt.execute(select.toString());

            Object data = clazz.newInstance();
            ResultSet result = stmt.getResultSet();
            if (result.next()) {
                for (Field field : description.getSelectFields()) {
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

}
