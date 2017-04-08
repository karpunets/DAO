package com.karpunets.pojo.grants;

import com.karpunets.dao.utils.xml.SetAdapter;
import com.karpunets.pojo.Qualification;
import com.karpunets.pojo.Task;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 01.02.2017
 */
public class Employee extends Grant {

    private Qualification qualification;

    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Task> tasks;
    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Task> newTasks;

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Set<Task> getNewTasks() {
        return this.newTasks;
    }

    public void setNewTasks(Set<Task> newTasks) {
        this.newTasks = newTasks;
    }

    public void addNewTask(Task task) {
        setNewTasks(checkNullSet(getNewTasks()));
        getNewTasks().add(task);
    }

    public void removeNewTask(Task task) {
        setNewTasks(checkNullSet(getNewTasks()));
        getNewTasks().remove(task);
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void removeTask(Task task) {
        setTasks(checkNullSet(getTasks()));
        getTasks().remove(task);
    }

    /**
     * Transfers object {@link Task} from set called newTasks to set called tasks
     * In other words new employee's task went into the development process
     *
     * @param task must contains in set newTasks
     */
    public void carryOutTask(Task task) {
        if (getNewTasks().contains(task)) {
            setTasks(checkNullSet(getTasks()));
            getTasks().add(task);
            getNewTasks().remove(task);
        }
    }

    private Set<Task> checkNullSet(Set set) {
        if (set == null) {
            return new LinkedHashSet<>();
        }
        return set;
    }

}
