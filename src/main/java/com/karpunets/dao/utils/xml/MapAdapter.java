package com.karpunets.dao.utils.xml;

import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.pojo.CompanyObject;
import com.karpunets.pojo.Project;
import com.karpunets.pojo.Sprint;
import com.karpunets.pojo.Task;
import com.karpunets.pojo.grants.Administrator;
import com.karpunets.pojo.grants.Customer;
import com.karpunets.pojo.grants.Employee;
import com.karpunets.pojo.grants.Manager;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Karpunets
 * @since 20.02.2017
 */
public class MapAdapter {

    private static <T extends CompanyObject> Map<Long, T> unmarshal(Class<T> tClass, T[] array) {
        Map<Long, T> map = new HashMap<>();
        for (T object : array) {
            if (ObjectCache.isExist(object.getId())) {
                map.put(object.getId(), (T) ObjectCache.get(object.getId()));
            } else {
                ObjectCache.add(object.getId(), object);
                map.put(object.getId(), object);
            }
        }
        return map;
    }

    private static <T extends CompanyObject> T[] marshal(Class<T> tClass, Map<Long, T> map) {
        if (map == null) {
            return null;
        }
        T[] array = (T[]) Array.newInstance(tClass, map.size());
        map.values().toArray(array);
        return array;
    }

    public static class AdministratorAdapter extends XmlAdapter<Administrator[], Map<Long, Administrator>> {
        @Override
        public Map<Long, Administrator> unmarshal(Administrator[] array) throws Exception {
            return MapAdapter.unmarshal(Administrator.class, array);
        }
        @Override
        public Administrator[] marshal(Map<Long, Administrator> map) throws Exception {
            return MapAdapter.marshal(Administrator.class, map);
        }
    }

    public static class CustomerAdapter extends XmlAdapter<Customer[], Map<Long, Customer>> {
        @Override
        public Map<Long, Customer> unmarshal(Customer[] array) throws Exception {
            return MapAdapter.unmarshal(Customer.class, array);
        }
        @Override
        public Customer[] marshal(Map<Long, Customer> map) throws Exception {
            return MapAdapter.marshal(Customer.class, map);
        }
    }

    public static class EmployeeAdapter extends XmlAdapter<Employee[], Map<Long, Employee>> {
        @Override
        public Map<Long, Employee> unmarshal(Employee[] array) throws Exception {
            return MapAdapter.unmarshal(Employee.class, array);
        }
        @Override
        public Employee[] marshal(Map<Long, Employee> map) throws Exception {
            return MapAdapter.marshal(Employee.class, map);
        }
    }

    public static class ManagerAdapter extends XmlAdapter<Manager[], Map<Long, Manager>> {
        @Override
        public Map<Long, Manager> unmarshal(Manager[] array) throws Exception {
            return MapAdapter.unmarshal(Manager.class, array);
        }
        @Override
        public Manager[] marshal(Map<Long, Manager> map) throws Exception {
            return MapAdapter.marshal(Manager.class, map);
        }
    }

    public static class ProjectAdapter extends XmlAdapter<Project[], Map<Long, Project>> {
        @Override
        public Map<Long, Project> unmarshal(Project[] array) throws Exception {
            return MapAdapter.unmarshal(Project.class, array);
        }
        @Override
        public Project[] marshal(Map<Long, Project> map) throws Exception {
            return MapAdapter.marshal(Project.class, map);
        }
    }

    public static class SprintAdapter extends XmlAdapter<Sprint[], Map<Long, Sprint>> {
        @Override
        public Map<Long, Sprint> unmarshal(Sprint[] array) throws Exception {
            return MapAdapter.unmarshal(Sprint.class, array);
        }
        @Override
        public Sprint[] marshal(Map<Long, Sprint> map) throws Exception {
            return MapAdapter.marshal(Sprint.class, map);
        }
    }

    public static class TaskAdapter extends XmlAdapter<Task[], Map<Long, Task>> {
        @Override
        public Map<Long, Task> unmarshal(Task[] array) throws Exception {
            return MapAdapter.unmarshal(Task.class, array);
        }
        @Override
        public Task[] marshal(Map<Long, Task> map) throws Exception {
            return MapAdapter.marshal(Task.class, map);
        }
    }

}
