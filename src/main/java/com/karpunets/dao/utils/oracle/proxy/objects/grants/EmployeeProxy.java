package com.karpunets.dao.utils.oracle.proxy.objects.grants;

import com.karpunets.dao.utils.oracle.proxy.ProxyFactory;
import com.karpunets.pojo.Task;
import com.karpunets.pojo.grants.Employee;

import java.util.Set;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class EmployeeProxy extends Employee implements ProxyFactory.Proxy {

    private Set<Long> tasks;
    private Set<Long> newTasks;

    @Override
    public Set<Task> getNewTasks() {
        if (super.getNewTasks() == null) {
            super.setNewTasks(ProxyFactory.get(Task.class, this.newTasks));
        }
        return super.getNewTasks();
    }

    @Override
    public Set<Task> getTasks() {
        if (super.getTasks() == null) {
            super.setTasks(ProxyFactory.get(Task.class, this.tasks));
        }
        return super.getTasks();
    }
}
