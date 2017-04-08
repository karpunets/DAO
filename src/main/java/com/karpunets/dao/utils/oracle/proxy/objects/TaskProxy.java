package com.karpunets.dao.utils.oracle.proxy.objects;

import com.karpunets.dao.utils.oracle.proxy.ProxyFactory;
import com.karpunets.pojo.Task;
import com.karpunets.pojo.dialog.Dialog;

import java.util.Set;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class TaskProxy extends Task implements ProxyFactory.Proxy {

    private Long dialog;
    private Set<Long> dependencies;
    private Set<Long> subtasks;

    @Override
    public Dialog getDialog() {
        if (super.getDialog() == null) {
            super.setDialog(ProxyFactory.get(Dialog.class, this.dialog));
        }
        return super.getDialog();
    }

    @Override
    public Set<Task> getDependencies() {
        if (super.getDependencies() == null) {
            super.setDependencies(ProxyFactory.get(Task.class, this.dependencies));
        }
        return super.getDependencies();
    }

    @Override
    public Set<Task> getSubtasks() {
        if (super.getSubtasks() == null) {
            super.setSubtasks(ProxyFactory.get(Task.class, this.subtasks));
        }
        return super.getSubtasks();
    }

}
