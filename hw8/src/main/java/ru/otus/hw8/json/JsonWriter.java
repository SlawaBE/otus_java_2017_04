package ru.otus.hw8.json;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by stas on 30.05.2017
 */
public class JsonWriter {

    public String toJsonString(Object obj) {
        if (obj == null) {
            return "null";
        }
        StringBuilder result = new StringBuilder();

        try {
            result.append(getValue(obj));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }

    private String getName(Field field) {
        return "\"" + field.getName() + "\"";
    }

    private String getValue(Object o) throws IllegalAccessException {
        if (o == null) {
            return null;
        } else if (o instanceof String || o instanceof Character || o.getClass().equals(char.class)) {
            return "\"" + o + "\"";
        } else if (o.getClass().isPrimitive() || o instanceof Number) {
            return o.toString();
        } else if (o.getClass().isArray()) {
            return "[" + serializeArray(o) + "]";
        } else if (o instanceof Collection) {
            return "[" + serializeCollection(o) + "]";
        } else {
            return "{" + serializeFields(o) + "}";
        }
    }

    private String serializeFields(Object obj) throws IllegalAccessException {
        StringBuilder res = new StringBuilder();
        List<Field> fields = getAllFields(obj);

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);
            Object o = field.get(obj);
            String value = getValue(o);
            if (value != null) {
                if (i > 0) {
                    res.append(",");
                }
                res.append(getName(field)).append(":").append(getValue(o));
            }
        }

        return res.toString();
    }

    private String serializeArray(Object array) throws IllegalAccessException {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < Array.getLength(array); i++) {
            if (i > 0) {
                res.append(",");
            }
            Object item = Array.get(array, i);
            res.append(getValue(item));
        }

        return res.toString();
    }

    private String serializeCollection(Object obj) throws IllegalAccessException {
        StringBuilder res = new StringBuilder();
        Collection col = (Collection) obj;
        for (Object item : col) {
            res.append(getValue(item)).append(",");
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }

    private List<Field> getAllFields(Object obj) {
        Class clazz = obj.getClass();
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.asList(obj.getClass().getDeclaredFields()));
            clazz = clazz.getSuperclass();
        } while (!clazz.equals(Object.class));

        return fields;
    }


}
