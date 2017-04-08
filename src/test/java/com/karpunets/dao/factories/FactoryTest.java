package com.karpunets.dao.factories;

import com.karpunets.dao.DAOFactory;
import com.karpunets.dao.GeneralDAO;
import com.karpunets.dao.GenericDAO;
import com.karpunets.pojo.grants.Employee;
import com.karpunets.pojo.grants.EmployeeTest;
import com.karpunets.pojo.grants.Manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
class FactoryTest {

    private DAOFactory daoFactory;
    private Employee employee;

    FactoryTest(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    void getGenericDAO() throws Exception {
        GenericDAO<Employee> genericDAO = daoFactory.getGenericDAO(Employee.class);
        employee = EmployeeTest.getRandomEmployee();
        genericDAO.insert(employee);
        Employee employeeGet = genericDAO.get(employee.getId());
        assertEquals(employee, employeeGet);
        genericDAO.close();
    }

    void getGeneralDAO() throws Exception {
        GeneralDAO generalDAO = daoFactory.getGeneralDAO();
        Manager manager = generalDAO.employeeToManager(employee);
        assertEquals(manager.getId(), employee.getId());
        assertEquals(manager.getName(), employee.getName());

        Employee employee = generalDAO.managerToEmployee(manager);
        assertEquals(this.employee.getId(), employee.getId());
        assertEquals(this.employee.getName(), employee.getName());

        assertEquals(generalDAO.searchGrant(employee.getLogin(), employee.getPassword()).hashCode(), employee.hashCode());
        assertEquals(generalDAO.isLoginFree(employee.getLogin()), false);
        assertTrue(generalDAO.getClassByIdObject(employee.getId()) == employee.getClass());
    }


}
