package com.karpunets.dao.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class UtilGenerealDAO {

    public static void setFields(Object server, Object client, Class parentClass) {
        for (Field field: getFields(parentClass)) {
            try {
                field.setAccessible(true);
                Object value = field.get(server);
                field.set(client, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static Set<Field> getFields(Class oClass) {
        Set<Field> fields = new HashSet<>();
        while (true) {
            Collections.addAll(fields, oClass.getDeclaredFields());
            if (oClass.getSuperclass() != null) {
                oClass  = oClass.getSuperclass();
                continue;
            } else {
                break;
            }
        }
        return fields;
    }
}
