package com.karpunets.dao.generalDAO;

import com.karpunets.dao.GeneralDAO;
import com.karpunets.pojo.*;
import com.karpunets.pojo.grants.*;

import java.util.Set;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class BinaryGeneralDAO implements GeneralDAO {

    private Company company;

    public BinaryGeneralDAO(Company company) {
        this.company = company;
    }

    @Override
    public Grant searchGrant(String login, String password) {
        return company.searchGrant(login, password);
    }

    @Override
    public boolean isLoginFree(String login) {
        return company.isLoginFree(login);
    }

    @Override
    public Class getClassByIdObject(long id) {
        return company.getClassByIdObject(id);
    }

    @Override
    public Manager employeeToManager(Employee employee) {
        return company.employeeToManager(employee);
    }

    @Override
    public Employee managerToEmployee(Manager manager) {
        return company.managerToEmployee(manager);
    }

    @Override
    public Set<Employee> getEmployeesLike(String query) {
        return company.getEmployeesLike(query);
    }

}
