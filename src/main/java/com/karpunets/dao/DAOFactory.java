package com.karpunets.dao;

import com.karpunets.dao.factories.*;
import com.karpunets.pojo.CompanyObject;

import java.io.File;
import java.io.IOException;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public abstract class DAOFactory implements AutoCloseable {

    public static final int ORACLE = 1;
    public static final int JSON = 2;
    public static final int BINARY = 3;
    public static final int XML = 4;
    public static final int COLLECTION = 5;

    private static OracleDAOFactory oracleDAOFactory;
    private static JsonDAOFactory jsonDAOFactory;
    private static BinaryDAOFactory binaryDAOFactory;
    private static XmlDAOFactory xmlDAOFactory;
    private static CollectionDAOFactory collectionDAOFactory;

    private static final String FOLDER_SRC = "./target/fileDB/";

    public abstract <T extends CompanyObject> GenericDAO<T> getGenericDAO(Class<T> c);

    public abstract GeneralDAO getGeneralDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
            case ORACLE:
                if (oracleDAOFactory == null || oracleDAOFactory.isClose()) {
                    oracleDAOFactory = new OracleDAOFactory();
                }
                return oracleDAOFactory;
            case JSON:
                if (jsonDAOFactory == null) {
                    jsonDAOFactory = new JsonDAOFactory();
                }
                return jsonDAOFactory;
            case BINARY:
                if (binaryDAOFactory == null) {
                    binaryDAOFactory = new BinaryDAOFactory();
                }
                return binaryDAOFactory;
            case XML:
                if (xmlDAOFactory == null) {
                    xmlDAOFactory = new XmlDAOFactory();
                }
                return xmlDAOFactory;
            case COLLECTION:
                if (collectionDAOFactory == null) {
                    collectionDAOFactory = new CollectionDAOFactory();
                }
                return collectionDAOFactory;
            default:
                return null;
        }
    }

    protected static File createFile(String fileName) {
        File folder = new File(FOLDER_SRC);
        File file = new File(FOLDER_SRC + fileName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}