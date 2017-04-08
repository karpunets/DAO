package com.karpunets.pojo.grants;

import com.karpunets.dao.utils.xml.SetAdapter;
import com.karpunets.pojo.Project;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 01.02.2017
 */
public class Customer extends Grant {

    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Project> projects;

    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Project> newProjects;

    public Set<Project> getNewProjects() {
        return newProjects;
    }

    public void setNewProjects(Set<Project> newProjects) {
        this.newProjects = newProjects;
    }

    public void addNewProject(Project project) {
        setNewProjects(checkNullSet(getNewProjects()));
        getNewProjects().add(project);
    }

    public void removeNewProject(Project project) {
        setNewProjects(checkNullSet(getNewProjects()));
        getNewProjects().remove(project);
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void removeProject(Project project) {
        setProjects(checkNullSet(getProjects()));
        getProjects().remove(project);
    }

    /**
     * Transfers object {@link Project} from set called newProjects to set called projects
     * In other words new customer's project went into the development process
     *
     * @param project must contains in set newProjects
     */
    public void carryOutProject(Project project) {
        if (getNewProjects().contains(project)) {
            setProjects(checkNullSet(getProjects()));
            getProjects().add(project);
            getNewProjects().remove(project);
        }
    }

    private Set<Project> checkNullSet(Set set) {
        if (set == null) {
            return new LinkedHashSet<>();
        }
        return set;
    }

}
