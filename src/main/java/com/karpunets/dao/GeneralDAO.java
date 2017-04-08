package com.karpunets.dao;

import com.karpunets.pojo.grants.Employee;
import com.karpunets.pojo.grants.Grant;
import com.karpunets.pojo.grants.Manager;

import java.util.Set;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public interface GeneralDAO {

    Grant searchGrant(String login, String password);
    boolean isLoginFree(String login);
    Class getClassByIdObject(long id);
    Manager employeeToManager(Employee employee);
    Employee managerToEmployee(Manager manager);
    Set<Employee> getEmployeesLike(String query);

}