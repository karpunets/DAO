package com.karpunets.pojo;

import com.karpunets.dao.utils.UtilGenerealDAO;
import com.karpunets.dao.utils.xml.MapAdapter;
import com.karpunets.pojo.grants.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.*;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
@XmlRootElement
public class Company implements Serializable {

    private Map<Class, Map> classMap = new HashMap<>();
    private long id = 0;

    @XmlJavaTypeAdapter(MapAdapter.AdministratorAdapter.class)
    private Map<Long, Administrator> administrators = new HashMap<>();
    @XmlJavaTypeAdapter(MapAdapter.CustomerAdapter.class)
    private Map<Long, Customer> customers = new HashMap<>();
    @XmlJavaTypeAdapter(MapAdapter.EmployeeAdapter.class)
    private Map<Long, Employee> employees = new HashMap<>();
    @XmlJavaTypeAdapter(MapAdapter.ManagerAdapter.class)
    private Map<Long, Manager> managers = new HashMap<>();
    @XmlJavaTypeAdapter(MapAdapter.ProjectAdapter.class)
    private Map<Long, Project> projects = new HashMap<>();
    @XmlJavaTypeAdapter(MapAdapter.SprintAdapter.class)
    private Map<Long, Sprint> sprints = new HashMap<>();
    @XmlJavaTypeAdapter(MapAdapter.TaskAdapter.class)
    private Map<Long, Task> tasks = new HashMap<>();


    public <T extends CompanyObject> long insert(T object) {
        Map<Long, T> map = searchMap((Class<T>) object.getClass());
        if (id == 0) {
            for (Map m : classMap.values()) {
                id += m.size();
            }
        }
        map.put(++id, object);
        return id;
    }

    public <T extends CompanyObject> long insert(T object, long id) {
        searchMap((Class<T>) object.getClass()).put(id, object);
        return id;
    }

    public <T extends CompanyObject> T get(Class<T> objectClass, Long id) {
        return searchMap(objectClass).get(id);
    }

    public <T extends CompanyObject> void delete(T object) {
        searchMap(object.getClass()).remove(object.getId(), object);
    }

    public <T extends CompanyObject> Collection<T> getCollection(Class<T> objectClass) {
        return searchMap(objectClass).values();
    }

    private <T extends CompanyObject> Map<Long, T> searchMap(Class<T> objectClass) {
        if (classMap.isEmpty()) {
            classMap.put(Administrator.class, administrators);
            classMap.put(Customer.class, customers);
            classMap.put(Employee.class, employees);
            classMap.put(Manager.class, managers);
            classMap.put(Project.class, projects);
            classMap.put(Sprint.class, sprints);
            classMap.put(Task.class, tasks);
        }
        return classMap.get(objectClass);
    }

    public Grant searchGrant(String login, String password) {
        for (Map map : classMap.values()) {
            for (Object o : map.values()) {
                if (o instanceof Grant) {
                    Grant grant = (Grant) o;

                    if (grant.getLogin().equals(login) && grant.getPassword().equals(password)) {
                        return grant;
                    }
                }
            }
        }
        return null;
    }

    public boolean isLoginFree(String login) {

        for (Map map : classMap.values()) {
            for (Object o : map.values()) {
                if (o instanceof Grant) {
                    Grant grant = (Grant) o;
                    if (grant.getLogin().equals(login)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Class getClassByIdObject(long id) {
        for (Map.Entry<Class, Map> entry : classMap.entrySet()) {
            CompanyObject companyObject = (CompanyObject) entry.getValue().get(id);
            if (companyObject != null) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Manager employeeToManager(Employee employee) {
        Manager manager = new Manager();
        UtilGenerealDAO.setFields(employee, manager, Employee.class);
        delete(employee);
        insert(manager, manager.getId());
        return manager;
    }

    public Employee managerToEmployee(Manager manager) {
        Employee employee = new Employee();
        UtilGenerealDAO.setFields(manager, employee, Employee.class);
        delete(manager);
        insert(employee, employee.getId());
        return employee;
    }

    @Override
    public String toString() {
        return administrators.toString() + customers.toString() + employees.toString()
                + managers.toString() + projects.toString() + sprints.toString() + tasks.toString();
    }

    public Set<Employee> getEmployeesLike(String query) {
        Set<Employee> result = new LinkedHashSet<>();
        searchEmployeesLike(employees.values(), query, result);
        searchEmployeesLike(managers.values(), query, result);
        return result;
    }

    private void searchEmployeesLike(Collection<? extends  Employee> employees, String query, Set<Employee> result) {
        for (Employee employee: employees) {
            if ((employee.getName() + employee.getSurname()).contains(query)) {
                result.add(employee);
            }
        }
    }
}
