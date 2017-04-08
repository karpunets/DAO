package com.karpunets.dao.utils.oracle.proxy.objects;

import com.karpunets.dao.utils.oracle.proxy.ProxyFactory;
import com.karpunets.pojo.Sprint;
import com.karpunets.pojo.Task;

import java.util.Set;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class SprintProxy extends Sprint implements ProxyFactory.Proxy {

    Set<Long> tasks;

    @Override
    public Set<Task> getTasks() {
        if (super.getTasks() == null) {
            super.setTasks(ProxyFactory.get(Task.class, this.tasks));
        }
        return super.getTasks();
    }

}
