package com.karpunets.dao.utils.oracle;

import com.karpunets.pojo.Qualification;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Karpunets
 * @since 07.02.2017
 */
public class Converter {

    private Converter() {
    }

    public static ConverterFactory getConverterFactory(String type) {
        if (String.class.getName().equals(type)) {
            return (ConverterFactory<String>) s -> s;
        }
        if (Long.class.getName().equals(type)) {
            return (ConverterFactory<Long>) s -> Long.parseLong(s);
        }
        if (Date.class.getName().equals(type)) {
            return (ConverterFactory<Date>) s -> {
                try {
                    return new SimpleDateFormat().parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            };
        }
        if (Set.class.getName().equals(type)) {
            return (ConverterFactory<Set<Long>>) s -> {
                Set<Long> set = new LinkedHashSet<>();
                String[] stringsLong = s.split(", ");
                for (String sLong : stringsLong) {
                    if (!sLong.equals("null")) {
                        set.add(Long.parseLong(sLong));
                    }
                }
                return set;
            };
        }
        if (Qualification.class.getName().equals(type)) {
            return (ConverterFactory<Qualification>) s -> Qualification.valueOf(s);
        }
        if (File.class.getName().equals(type)) {
            return (ConverterFactory<File>) s -> new File(s);
        }
        if (Integer.class.getName().equals(type)) {
            return (ConverterFactory<Integer>) s -> Integer.parseInt(s);
        }
        if (Boolean.class.getName().equals(type)) {
            return (ConverterFactory<Boolean>) s -> Boolean.parseBoolean(s);
        }
        return null;
    }

    public interface ConverterFactory<T> {
        T convert(String s);
    }
}
