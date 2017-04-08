package com.karpunets.pojo;

import com.karpunets.dao.utils.xml.SetAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 01.02.2017
 */
public class Sprint extends CompanyObject {

    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Task> tasks;
    private String description;
    private Boolean finished = false;

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (getTasks() == null) {
            setTasks(new LinkedHashSet<>());
        }
        getTasks().add(task);
    }

    public void removeTask(Task task) {
        if (getTasks() != null) {
            getTasks().remove(task);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        if (finished == null) {
            return false;
        }
        return finished;
    }

    public void finish() {
        finished = true;
    }
}
