package com.karpunets.dao.utils.oracle.proxy.objects.grants;

import com.karpunets.dao.utils.oracle.proxy.ProxyFactory;
import com.karpunets.pojo.Project;
import com.karpunets.pojo.Task;
import com.karpunets.pojo.grants.Manager;

import java.util.Set;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class ManagerProxy extends Manager implements ProxyFactory.Proxy {

    private Set<Long> projects;
    private Set<Long> newProjects;
    private Set<Long> tasks;
    private Set<Long> newTasks;

    @Override
    public Set<Project> getNewProjects() {
        if (super.getNewProjects() == null) {
            super.setNewProjects(ProxyFactory.get(Project.class, this.newProjects));
        }
        return super.getNewProjects();
    }

    @Override
    public Set<Project> getProjects() {
        if (super.getProjects() == null) {
            super.setProjects(ProxyFactory.get(Project.class, this.projects));
        }
        return super.getProjects();
    }

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
