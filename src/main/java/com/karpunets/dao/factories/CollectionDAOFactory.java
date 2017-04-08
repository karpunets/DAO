package com.karpunets.dao.factories;

import com.karpunets.dao.DAOFactory;
import com.karpunets.dao.GeneralDAO;
import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.dao.utils.UtilGenerealDAO;
import com.karpunets.pojo.CompanyObject;
import com.karpunets.pojo.grants.Employee;
import com.karpunets.pojo.grants.Grant;
import com.karpunets.pojo.grants.Manager;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public class CollectionDAOFactory extends DAOFactory {

    private static long index = 1;

    @Override
    public <T extends CompanyObject> GenericDAO<T> getGenericDAO(Class<T> tClass) {
        return new GenericDAO<T>() {
            @Override
            public long insert(T object) {
                object.setId(index);
                ObjectCache.add(index, object);
                return index++;
            }

            @Override
            public boolean update(T object) {
                return true;
            }

            @Override
            public T get(long objectId) {
                return (T) ObjectCache.get(objectId);
            }

            @Override
            public Set<T> getAll() {
                Set<T> result = new HashSet<T>();
                for (CompanyObject object : ObjectCache.getAll()) {
                    if (tClass.isAssignableFrom(object.getClass())) {
                        result.add((T) object);
                    }
                }
                return result;
            }

            @Override
            public boolean delete(T object) {
                ObjectCache.remove(object.getId());
                return true;
            }

            @Override
            public void close() throws Exception {
            }
        };
    }

    @Override
    public GeneralDAO getGeneralDAO() {
        return new GeneralDAO() {
            @Override
            public Grant searchGrant(String login, String password) {
                for (CompanyObject companyObject : ObjectCache.getAll()) {
                    if (companyObject instanceof Grant) {
                        Grant grant = (Grant) companyObject;
                        if (grant.getLogin().equals(login) && grant.getPassword().equals(password)) {
                            return grant;
                        }
                    }
                }
                return null;
            }

            @Override
            public boolean isLoginFree(String login) {
                for (CompanyObject companyObject : ObjectCache.getAll()) {
                    if (companyObject instanceof Grant) {
                        Grant grant = (Grant) companyObject;
                        if (grant.getLogin().equals(login)) {
                            return false;
                        }
                    }
                }
                return true;
            }

            @Override
            public Class getClassByIdObject(long id) {
                CompanyObject companyObject = ObjectCache.get(id);
                if (companyObject != null) {
                    return companyObject.getClass();
                }
                return null;
            }

            @Override
            public Manager employeeToManager(Employee employee) {
                Manager manager = new Manager();
                UtilGenerealDAO.setFields(employee, manager, Employee.class);
                ObjectCache.remove(employee.getId());
                ObjectCache.add(manager.getId(), manager);
                return manager;
            }

            @Override
            public Employee managerToEmployee(Manager manager) {
                Employee employee = new Employee();
                UtilGenerealDAO.setFields(manager, employee, Employee.class);
                ObjectCache.remove(manager.getId());
                ObjectCache.add(employee.getId(), employee);
                return employee;
            }

            @Override
            public Set<Employee> getEmployeesLike(String query) {
                Set<Employee> result = new LinkedHashSet<>();
                for (CompanyObject companyObject: ObjectCache.getAll()) {
                    if (companyObject instanceof Employee) {
                        Employee employee = (Employee)companyObject;
                        if ((employee.getName() + employee.getSurname()).contains(query)) {
                            result.add(employee);
                        }
                    }
                }
                return result;
            }
        };
    }


    public void close() throws Exception {
    }
}
