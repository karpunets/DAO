package com.karpunets.dao.genericDAO;

import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.pojo.CompanyObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public class JsonGenericDAO<T extends CompanyObject> implements GenericDAO<T> {

    private Class<T> tClass;
    private Map<Long, CompanyObject> objectMap;


    public JsonGenericDAO(Class<T> tClass, Map<Long, CompanyObject> objectMap) {
        this.objectMap = objectMap;
        this.tClass = tClass;
    }

    @Override
    public long insert(T object) {

        long maxId = 0;
        for (Long id : objectMap.keySet()) {
            if (id > maxId) {
                maxId = id;
            }
        }
        object.setId(++maxId);
        objectMap.put(maxId, object);
        ObjectCache.add(maxId, object);
        return maxId;
    }

    @Override
    public boolean update(T object) {
        objectMap.replace(object.getId(), object);
        return true;
    }

    @Override
    public T get(long objectId) {
        return (T) objectMap.get(objectId);
    }

    @Override
    public Set<T> getAll() {
        Set<T> result = new HashSet<T>();
        for (CompanyObject companyObject : objectMap.values()) {
            if (tClass.isAssignableFrom(companyObject.getClass())) {
                result.add((T) companyObject);
            }
        }
        return result;
    }

    @Override
    public boolean delete(T object) {
        objectMap.remove(object.getId());
        object = null;
        return true;
    }

    @Override
    public void close() throws Exception {

    }


}
