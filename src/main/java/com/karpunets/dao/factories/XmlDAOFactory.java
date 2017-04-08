package com.karpunets.dao.factories;

import com.karpunets.dao.GeneralDAO;
import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.DAOFactory;
import com.karpunets.dao.generalDAO.XmlGeneralDAO;
import com.karpunets.dao.genericDAO.XmlGenericDAO;
import com.karpunets.pojo.Company;
import com.karpunets.pojo.CompanyObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public class XmlDAOFactory extends DAOFactory {

    private static final String FILE_NAME = "db.xml";
    private static GeneralDAO generalDAO;

    private final static File FILE = createFile(FILE_NAME);
    private static Company company;

    public XmlDAOFactory() {
        readFile();
    }

    @Override
    public <T extends CompanyObject> GenericDAO<T> getGenericDAO(Class<T> tClass) {
        return new XmlGenericDAO<>(tClass, company);
    }

    @Override
    public GeneralDAO getGeneralDAO() {
        if (generalDAO == null) {
            generalDAO = new XmlGeneralDAO(company);
        }
        return generalDAO;
    }

    @Override
    public void close() throws Exception {
        writeFile();
    }

    private void readFile() {
//        company = new Company();
        if (company == null) {
            try {
                JAXBContext context = JAXBContext.newInstance(Company.class);
                Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
                company = (Company) jaxbUnmarshaller.unmarshal(FILE);
            } catch (JAXBException e) {
                company = new Company();
            }
        }
    }

    private void writeFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(Company.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(company, FILE);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
