package com.karpunets.pojo;

import com.karpunets.random.Randomizer;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class ProjectTest {

    public static Project getSimpleProject() {
        Project project = new Project();
        project.setName(Randomizer.getRandomString());
        project.setDescription(Randomizer.getRandomString());
        return project;
    }

    public static Project getSimpleProject(Sprint... sprints) {
        Project project = getSimpleProject();
        for (Sprint sprint: sprints) {
            project.addSprint(sprint);
        }
        return project;
    }

}