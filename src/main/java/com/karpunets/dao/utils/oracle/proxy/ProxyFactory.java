package com.karpunets.dao.utils.oracle.proxy;

import com.karpunets.dao.DAOFactory;
import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.dao.utils.oracle.proxy.objects.*;
import com.karpunets.dao.utils.oracle.proxy.objects.dialog.*;
import com.karpunets.dao.utils.oracle.proxy.objects.grants.*;
import com.karpunets.pojo.*;
import com.karpunets.pojo.dialog.*;
import com.karpunets.pojo.grants.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class ProxyFactory {

    public interface Proxy {
    }

    public static Proxy getProxy(String attrTypeName) {
        if (Customer.class.getName().equals(attrTypeName)) {
            return new CustomerProxy();
        }
        if (Employee.class.getName().equals(attrTypeName)) {
            return new EmployeeProxy();
        }
        if (Manager.class.getName().equals(attrTypeName)) {
            return new ManagerProxy();
        }
        if (Project.class.getName().equals(attrTypeName)) {
            return new ProjectProxy();
        }
        if (Sprint.class.getName().equals(attrTypeName)) {
            return new SprintProxy();
        }
        if (Task.class.getName().equals(attrTypeName)) {
            return new TaskProxy();
        }
        if (Dialog.class.getName().equals(attrTypeName)) {
            return new DialogProxy();
        }
        if (Message.class.getName().equals(attrTypeName)) {
            return new MessageProxy();
        }
        return null;
    }

    public static <T extends CompanyObject> Set<T> get(Class<T> tClass, Set<Long> ids) {
        Set<T> set = new LinkedHashSet<>();
        if (ids != null) {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
            GenericDAO<T> genericDAO = daoFactory.getGenericDAO(tClass);

            for (Long id : ids) {
                if (id != null) {
                    CompanyObject companyObject;
                    if (ObjectCache.isExist(id)) {
                        companyObject = ObjectCache.get(id);
                    } else {
                        companyObject = genericDAO.get(id);
                    }
                    if (companyObject != null) {
                        set.add((T) companyObject);
                    }
                }
            }
        }
        return set;
    }

    public static <T extends CompanyObject> T get(Class<T> tClass, Long id) {
        if (id != null) {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
            GenericDAO<T> genericDAO = daoFactory.getGenericDAO(tClass);
            CompanyObject companyObject;
            if (ObjectCache.isExist(id)) {
                companyObject = ObjectCache.get(id);
            } else {
                companyObject = genericDAO.get(id);
            }
            return (T) companyObject;
        }
        return null;
    }

}
