package com.karpunets.dao;

import com.karpunets.pojo.CompanyObject;

import java.util.Set;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public interface GenericDAO<T extends CompanyObject> extends AutoCloseable {

    long insert(T object);

    boolean update(T object);

    T get(long objectId);

    Set<T> getAll();

    boolean delete(T object);

}