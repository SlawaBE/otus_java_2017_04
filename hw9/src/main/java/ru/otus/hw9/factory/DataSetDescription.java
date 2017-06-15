package ru.otus.hw9.factory;

import ru.otus.hw9.exception.DBException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Stanislav on 15.06.2017
 */
public class DataSetDescription {

    private String tableName;

    private List<Field> insertFields;

    private List<Field> selectFields;

    public DataSetDescription(Class clazz) {

        if (!clazz.isAnnotationPresent(Entity.class) && !clazz.isAnnotationPresent(Table.class)) {
            throw new DBException("Класс должен быть помечен аннотациями @Table и @Entity");
        }

        tableName = ((Table) clazz.getAnnotation(Table.class)).name();

        selectFields = getColumnFields(clazz);

        insertFields = new ArrayList<>();
        insertFields.addAll(selectFields.stream().filter(field -> !field.isAnnotationPresent(Id.class)).collect(Collectors.toList()));
    }

    public String getTableName() {
        return tableName;
    }

    public List<Field> getInsertFields() {
        return insertFields;
    }

    public List<Field> getSelectFields() {
        return selectFields;
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

}
