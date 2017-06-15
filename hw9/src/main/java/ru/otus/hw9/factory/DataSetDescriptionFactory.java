package ru.otus.hw9.factory;

import ru.otus.hw9.exception.DBException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav on 15.06.2017
 */
public class DataSetDescriptionFactory {

    static DataSetDescriptionFactory factory;

    private Map<String, DataSetDescription> descriptions;

    private DataSetDescriptionFactory() {
        descriptions = new HashMap<>();
    }

    public static DataSetDescriptionFactory getInstance() {
        if (factory == null) {
            factory = new DataSetDescriptionFactory();
        }
        return factory;
    }

    public void addAnnotatedClass(Class clazz) {
        DataSetDescription description = new DataSetDescription(clazz);
        String name = clazz.getName();
        descriptions.put(name, description);
    }

    public DataSetDescription getDescription(Class clazz) {
        DataSetDescription description = descriptions.get(clazz.getName());
        if (description == null) {
            throw new DBException("Класс не был обработан ранее");
        }
        return description;
    }

}
