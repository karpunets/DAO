package com.karpunets.pojo;

import com.karpunets.dao.utils.xml.SetAdapter;
import com.karpunets.pojo.dialog.Dialog;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 01.02.2017
 */
public class Task extends CompanyObject {

    private Date startingDate;
    private Integer estimate;
    private Date endingDate;
    private Qualification qualification;
    private Dialog dialog;
    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Task> dependencies;
    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Task> subtasks;
    private String description;
    private Boolean finished;

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public void start() {
        startingDate = new Date();
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void end() {
        endingDate = new Date();
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Set<Task> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<Task> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(Task dependency) {
        if (getDependencies() == null) {
            setDependencies(new LinkedHashSet<>());
        }
        getDependencies().add(dependency);
    }

    public void removeDependency(Task dependency) {
        if (getDependencies() != null) {
            getDependencies().remove(dependency);
        }
    }

    public Set<Task> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Set<Task> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtasks(Task subtask) {
        if (getSubtasks() == null) {
            setSubtasks(new LinkedHashSet<>());
        }
        getSubtasks().add(subtask);
    }

    public void removeSubtasks(Task subtask) {
        if (getSubtasks() != null) {
            getSubtasks().remove(subtask);
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
