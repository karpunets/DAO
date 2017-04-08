package com.karpunets.dao.factories;

import com.karpunets.dao.GeneralDAO;
import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.DAOFactory;
import com.karpunets.dao.generalDAO.BinaryGeneralDAO;
import com.karpunets.dao.genericDAO.BinaryGenericDAO;
import com.karpunets.pojo.Company;
import com.karpunets.pojo.CompanyObject;

import java.io.*;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public class BinaryDAOFactory extends DAOFactory {

    private static final String FILE_NAME = "db.bin";
    private static final File FILE = createFile(FILE_NAME);

    private static Company company = new Company();
    private static GeneralDAO generalDAO;


    public BinaryDAOFactory() {
        readFile();
    }

    @Override
    public <T extends CompanyObject> GenericDAO<T> getGenericDAO(Class<T> tClass) {
        return new BinaryGenericDAO<>(tClass, company);
    }

    @Override
    public GeneralDAO getGeneralDAO() {
        if (generalDAO == null) {
            generalDAO = new BinaryGeneralDAO(company);
        }
        return generalDAO;
    }

    @Override
    public void close() throws Exception {
        writeFile();
    }

    private void readFile() {
        try (FileInputStream fis = new FileInputStream(FILE)) {
            try (ObjectInputStream oin = new ObjectInputStream(fis)) {
                company = (Company) oin.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            company = new Company();
        }
    }

    private void writeFile() {
        try (FileOutputStream fos = new FileOutputStream(FILE)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(company);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
