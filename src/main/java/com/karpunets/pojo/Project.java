package com.karpunets.pojo;

import com.karpunets.dao.utils.xml.SetAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 01.02.2017
 */
public class Project extends CompanyObject {

    private String name;
    private Date startingDate;
    private Date endingDate;
    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Sprint> sprints;
    private String description;
    private Boolean finished;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = sprints;
    }

    public void addSprint(Sprint sprint) {
        if (getSprints() == null) {
            setSprints(new LinkedHashSet<>());
        }
        getSprints().add(sprint);
    }

    public void removeSprint(Sprint sprint) {
        if (getSprints() != null) {
            getSprints().remove(sprint);
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
