package com.karpunets.dao.factories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.karpunets.dao.GeneralDAO;
import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.DAOFactory;
import com.karpunets.dao.generalDAO.JsonGeneralDAO;
import com.karpunets.dao.genericDAO.JsonGenericDAO;
import com.karpunets.dao.utils.json.CompanyObjectAdapter;
import com.karpunets.dao.utils.json.SetAdapter;
import com.karpunets.pojo.CompanyObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public class JsonDAOFactory extends DAOFactory {

    private static final String FILE_NAME = "db.json";
    private static GeneralDAO generalDAO;

    private static final File FILE = createFile(FILE_NAME);
    private static Map<Long, CompanyObject> objectMap;
    private Type type = new TypeToken<HashMap<Long, CompanyObject>>() {
    }.getType();
    private Gson gson;

    public JsonDAOFactory() {
        gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(CompanyObject.class, new CompanyObjectAdapter())
                .registerTypeAdapter(Set.class, new SetAdapter())
                .create();
        readFile();
    }

    @Override
    public <T extends CompanyObject> GenericDAO<T> getGenericDAO(Class<T> tClass) {
        return new JsonGenericDAO<>(tClass, objectMap);
    }

    @Override
    public GeneralDAO getGeneralDAO() {
        if (generalDAO == null) {
            generalDAO = new JsonGeneralDAO(objectMap);
        }
        return generalDAO;
    }

    @Override
    public void close() throws Exception {
        writeFile();
    }

    private void readFile() {
        if (objectMap == null) {
            try (FileReader reader = new FileReader(FILE)) {
                objectMap = gson.fromJson(reader, type);
                if (objectMap == null) {
                    objectMap = new HashMap<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeFile() {
        try (FileWriter writer = new FileWriter(FILE)) {
            gson.toJson(objectMap, type, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
