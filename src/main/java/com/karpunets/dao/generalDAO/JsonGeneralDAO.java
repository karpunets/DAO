package com.karpunets.dao.generalDAO;

import com.karpunets.dao.GeneralDAO;
import com.karpunets.dao.utils.UtilGenerealDAO;
import com.karpunets.pojo.CompanyObject;
import com.karpunets.pojo.grants.Employee;
import com.karpunets.pojo.grants.Grant;
import com.karpunets.pojo.grants.Manager;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class JsonGeneralDAO implements GeneralDAO {

    private Map<Long, CompanyObject> objectMap;

    public JsonGeneralDAO(Map<Long, CompanyObject> objectMap) {
        this.objectMap = objectMap;
    }

    @Override
    public Grant searchGrant(String login, String password) {
        for (CompanyObject o : objectMap.values()) {
            if (o instanceof Grant) {
                Grant grant = (Grant) o;
                if (grant.getLogin().equals(login) && grant.getPassword().equals(password)) {
                    return grant;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isLoginFree(String login) {
        for (CompanyObject o : objectMap.values()) {
            if (o instanceof Grant) {
                Grant grant = (Grant) o;
                if (grant.getLogin().equals(login)) {
                    return false;
                }
            }
        }
        return false;

    }

    @Override
    public Class getClassByIdObject(long id) {
        return objectMap.get(id).getClass();
    }

    @Override
    public Manager employeeToManager(Employee employee) {
        Manager manager = new Manager();
        UtilGenerealDAO.setFields(employee, manager, Employee.class);
        objectMap.remove(employee.getId());
        objectMap.put(manager.getId(), manager);
        return manager;
    }

    @Override
    public Employee managerToEmployee(Manager manager) {
        Employee employee = new Employee();
        UtilGenerealDAO.setFields(manager, employee, Employee.class);
        objectMap.remove(manager.getId());
        objectMap.put(employee.getId(), employee);
        return employee;
    }

    @Override
    public Set<Employee> getEmployeesLike(String query) {
        Set<Employee> result = new LinkedHashSet<>();
        for (CompanyObject companyObject: objectMap.values()) {
            if (companyObject instanceof Employee) {
                Employee employee = (Employee)companyObject;
                if ((employee.getName() + employee.getSurname()).contains(query)) {
                    result.add(employee);
                }
            }
        }
        return result;
    }

}
