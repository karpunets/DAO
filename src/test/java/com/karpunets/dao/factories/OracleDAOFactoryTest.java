package com.karpunets.dao.factories;

import com.karpunets.dao.DAOFactory;
import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.pojo.*;
import com.karpunets.pojo.grants.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 08.03.2017
 */
public class OracleDAOFactoryTest {

    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
    private FactoryTest factoryTest = new FactoryTest(daoFactory);

    private GenericDAO<Administrator> daoAdministrator;

    @Before
    public void setUp() throws Exception {
        daoAdministrator = daoFactory.getGenericDAO(Administrator.class);
    }

    private void getGenericDAO() throws Exception {
        factoryTest.getGenericDAO();
    }

//    @Test
    public void testLazyDownloading() throws Exception {
        Sprint sprint1 = SprintTest.getSimpleSprint();
        Sprint sprint2 = SprintTest.getSimpleSprint();
        GenericDAO<Sprint> sprintGenericDAO = daoFactory.getGenericDAO(Sprint.class);
        sprintGenericDAO.insert(sprint1);
        sprintGenericDAO.insert(sprint2);

        Project project = ProjectTest.getSimpleProject(sprint1, sprint2);
        GenericDAO<Project> projectGenericDAO = daoFactory.getGenericDAO(Project.class);
        projectGenericDAO.insert(project);

//        assertEquals(ObjectCache.getAll().toString(), "["+sprint1.getId()+", "+sprint2.getId()+", "+project.getId()+"]");

        ObjectCache.clear();

        Project projectGet = projectGenericDAO.get(project.getId());

        assertEquals(project.getId(), projectGet.getId());
        assertEquals(project.getName(), projectGet.getName());

//        assertEquals(ObjectCache.getAll().toString(), "[" +projectGet.getId()  + "]");
        for (Sprint sprintGet: projectGet.getSprints()) {
            boolean contains = false;
            for (Sprint sprint: project.getSprints()) {
                if (Objects.equals(sprintGet.getId(), sprint.getId())) {
                    contains = true;
                    break;
                }
            }
            assertTrue(contains);
        }
//        assertEquals(ObjectCache.getAll().toString(), "["+sprint1.getId()+", "+sprint2.getId()+", "+project.getId()+"]");

    }

//    @Test
    public void getGeneralDAO() throws Exception {
        getGenericDAO();
        factoryTest.getGeneralDAO();
    }

//    @Test
    public void createAdmin() throws Exception {
        Administrator administrator = AdministratorTest.getMainAdministrator();
        daoAdministrator.insert(administrator);
    }

//    @Test
    public void update() throws Exception {

        Project project = new Project();
        project.setName("name");

        daoFactory.getGenericDAO(Project.class).insert(project);

        System.out.println(project.getId());

        GenericDAO<Manager> genericDAO = daoFactory.getGenericDAO(Manager.class);
        Manager manager = genericDAO.get(2);
        manager.addNewProject(project);
        genericDAO.update(manager);
        System.out.println(manager.getNewProjects());
    }

    @After
    public void tearDown() throws Exception {
        daoAdministrator.close();
        daoFactory.close();
    }

}