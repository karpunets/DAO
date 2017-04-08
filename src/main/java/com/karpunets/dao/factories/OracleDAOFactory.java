package com.karpunets.dao.factories;

import com.karpunets.dao.GeneralDAO;
import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.DAOFactory;
import com.karpunets.dao.generalDAO.OracleGeneralDAO;
import com.karpunets.dao.genericDAO.OracleGenericDAO;
import com.karpunets.pojo.CompanyObject;

import java.sql.*;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public class OracleDAOFactory extends DAOFactory implements AutoCloseable {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "karpunets";
    private static final String DB_PASSWORD = "248163264";
    private Connection connection = null;
    private GeneralDAO generalDAO;


    public OracleDAOFactory() {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends CompanyObject> GenericDAO<T> getGenericDAO(Class<T> tClass) {
        return new OracleGenericDAO<>(getConnection(), tClass);
    }

    @Override
    public GeneralDAO getGeneralDAO() {
        if (generalDAO == null) {
            generalDAO = new OracleGeneralDAO(getConnection(), this);
        }
        return generalDAO;
    }


    private Connection getConnection() {
        return connection;
    }

    public boolean isClose() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
