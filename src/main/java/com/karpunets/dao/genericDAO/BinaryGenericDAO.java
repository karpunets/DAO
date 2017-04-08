package com.karpunets.dao.genericDAO;

import com.karpunets.dao.GenericDAO;
import com.karpunets.pojo.Company;
import com.karpunets.pojo.CompanyObject;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public class BinaryGenericDAO<T extends CompanyObject> implements GenericDAO<T> {

    private Class<T> tClass;
    private Company company;

    public BinaryGenericDAO(Class<T> tClass, Company company) {
        this.tClass = tClass;
        this.company = company;
    }

    @Override
    public long insert(T object) {
        long id = company.insert(object);
        object.setId(id);
        return id;
    }

    @Override
    public boolean update(T object) {
        return true;
    }

    @Override
    public T get(long objectId) {
        return company.get(tClass, objectId);
    }

    @Override
    public Set<T> getAll() {
        return new HashSet<>(company.getCollection(tClass));
    }

    @Override
    public boolean delete(T object) {
        company.delete(object);
        return true;
    }

    @Override
    public void close() throws Exception {

    }

}
