package com.karpunets.dao.factories;

import com.karpunets.dao.DAOFactory;
import org.junit.After;
import org.junit.Test;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class CollectionDAOFactoryTest {

    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.COLLECTION);
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