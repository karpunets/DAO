package com.karpunets.dao.generalDAO;

import static com.karpunets.dao.utils.oracle.DBConst.*;

import com.karpunets.dao.GeneralDAO;
import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.factories.OracleDAOFactory;
import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.dao.utils.oracle.DBConst;
import com.karpunets.dao.utils.oracle.SQLCreator;
import com.karpunets.pojo.grants.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class OracleGeneralDAO implements GeneralDAO {

    private Connection connection;
    private OracleDAOFactory oracleDAOFactory;

    public OracleGeneralDAO(Connection connection, OracleDAOFactory oracleDAOFactory) {
        this.connection = connection;
        this.oracleDAOFactory = oracleDAOFactory;
    }

    @Override
    public Grant searchGrant(String login, String password) {
        try (ResultSet resultSet = SQLCreator.searchGrant(connection, login, password)) {
            if (resultSet.next()) {
                Long id = resultSet.getLong(F.OBJECT_ID);
                String type = resultSet.getString(F.TYPE);
                if (ObjectCache.isExist(id)) {
                    return (Grant) ObjectCache.get(id);
                } else {
                    Grant people = null;
                    switch (type) {
                        case "com.karpunets.pojo.grants.Administrator":
                            people = oracleDAOFactory.getGenericDAO(Administrator.class).get(id);
                            break;
                        case "com.karpunets.pojo.grants.Customer":
                            people = oracleDAOFactory.getGenericDAO(Customer.class).get(id);
                            break;
                        case "com.karpunets.pojo.grants.Employee":
                            people = oracleDAOFactory.getGenericDAO(Employee.class).get(id);
                            break;
                        case "com.karpunets.pojo.grants.Manager":
                            people = oracleDAOFactory.getGenericDAO(Manager.class).get(id);
                            break;
                    }
                    ObjectCache.add(id, people);
                    return people;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isLoginFree(String login) {
        try (ResultSet resultSet = SQLCreator.searchGrant(connection, login)) {
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Class getClassByIdObject(long id) {
        if (ObjectCache.isExist(id)) {
            return ObjectCache.get(id).getClass();
        }
        try (ResultSet resultSet = SQLCreator.getObjectType(connection, id)) {
            if (resultSet.next()) {
                return Class.forName(resultSet.getString(F.NAME));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Manager employeeToManager(Employee employee) {
        try {
            SQLCreator.updateObjectType(connection, employee.getId(), Manager.class.getName());
            SQLCreator.insertNewParams(connection, employee.getId(), Manager.class.getDeclaredFields());
            ObjectCache.remove(employee.getId());
            return oracleDAOFactory.getGenericDAO(Manager.class).get(employee.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee managerToEmployee(Manager manager) {
        try {
            SQLCreator.updateObjectType(connection, manager.getId(), Employee.class.getName());
            SQLCreator.deleteParams(connection, manager.getId(), Manager.class.getDeclaredFields());
            ObjectCache.remove(manager.getId());
            return oracleDAOFactory.getGenericDAO(Employee.class).get(manager.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Employee> getEmployeesLike(String query) {
        Set<Employee> result = new LinkedHashSet<>();
        try (ResultSet resultSet = SQLCreator.getEmployeesLike(connection, query)) {
            while (resultSet.next()) {
                Long employeeId = resultSet.getLong(F.OBJECT_ID);
                String employeeType = resultSet.getString(F.NAME);
                if (Manager.class.getName().equals(employeeType)) {
                    result.add(oracleDAOFactory.getGenericDAO(Manager.class).get(employeeId));
                } else if (Employee.class.getName().equals(employeeType)) {
                    result.add(oracleDAOFactory.getGenericDAO(Employee.class).get(employeeId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
