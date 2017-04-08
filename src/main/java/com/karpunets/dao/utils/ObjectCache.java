package com.karpunets.dao.utils;

import com.karpunets.pojo.CompanyObject;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Karpunets
 * @since 15.02.2017
 */
public class ObjectCache {

    private static HashMap<Long, CompanyObject> cache = new HashMap<>();

    private ObjectCache() {
    }

    public static boolean isExist(CompanyObject object) {
        return cache.containsValue(object);
    }

    public static boolean isExist(Long id) {
        return cache.containsKey(id);
    }

    public static void add(Long id, CompanyObject object) {
        cache.put(id, object);
    }

    public static CompanyObject get(Long id) {
        return cache.get(id);
    }

    public static void remove(Long id) {
        cache.remove(id);
    }

    public static Collection<CompanyObject> getAll() {
        return cache.values();
    }

    public static void clear() {
        cache.clear();
    }
}
