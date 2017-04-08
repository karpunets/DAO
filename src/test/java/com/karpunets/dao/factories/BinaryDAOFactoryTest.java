package com.karpunets.dao.factories;

import com.karpunets.dao.DAOFactory;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class BinaryDAOFactoryTest {

    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.BINARY);
    private FactoryTest factoryTest = new FactoryTest(daoFactory);

    private void getGenericDAO() throws Exception {
        factoryTest.getGenericDAO();
    }

    @Test
    public void getGeneralDAO() throws Exception {
        getGenericDAO();
        factoryTest.getGeneralDAO();
    }

    @After
    public void tearDown() throws Exception {
        daoFactory.close();
    }

}