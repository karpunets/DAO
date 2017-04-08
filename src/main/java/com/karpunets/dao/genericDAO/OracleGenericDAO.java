package com.karpunets.dao.genericDAO;

import static com.karpunets.dao.utils.oracle.DBConst.*;

import com.karpunets.dao.GenericDAO;
import com.karpunets.dao.utils.oracle.Converter;
import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.dao.utils.oracle.proxy.ProxyFactory;
import com.karpunets.dao.utils.oracle.proxy.objects.ProjectProxy;
import com.karpunets.pojo.CompanyObject;
import com.karpunets.dao.utils.oracle.SQLCreator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
public class OracleGenericDAO<C extends CompanyObject> implements GenericDAO<C> {

    private Connection connection;
    private Statement statement;
    private Class<C> tClass;

    public OracleGenericDAO(Connection connection, Class<C> tClass) {
        this.connection = connection;
        this.tClass = tClass;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long insert(C object) {
        Class objectClass = object.getClass();
//        if (!tClass.isAssignableFrom(objectClass)) {
//            throw new IllegalArgumentException("Params must be assignable from generic class");
//        }
        long objectTypesId;
        boolean needBind;
        long objectId = 0;
        do {
            objectTypesId = getObjectTypeId(objectClass);
            needBind = needBind(objectTypesId);
            if (objectId == 0) {
                objectId = addObject(objectTypesId);
                object.setId(objectId);
            }
            for (Field field : objectClass.getDeclaredFields()) {
                long attrTypesId = getAttrTypeId(field.getType().getName());
                long attrId = getAttrId(field.getName(), attrTypesId);
                if (needBind) {
                    addAttrBind(objectTypesId, attrId);
                }
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if (value != null && value instanceof CompanyObject
                            && ((CompanyObject) value).getId() == null) {
                        new OracleGenericDAO<>(connection, CompanyObject.class).insert((CompanyObject) value);
                    }
                    addParam(objectId, attrId, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } while ((objectClass = objectClass.getSuperclass()) != null);
        ObjectCache.add(objectId, object);
        return objectId;
    }

    @Override
    public boolean update(C object) {
        long objectId = object.getId();
        Object value;
        Set<Object[]> set = new HashSet<>();

        try (ResultSet resultSetParams = SQLCreator.getParams(statement, objectId)) {
            while (resultSetParams.next()) {
                String attrName = resultSetParams.getString(F.ATTR_NAME);
                Field field = searchFieldInClass(object instanceof ProxyFactory.Proxy
                        ? object.getClass().getSuperclass(): object.getClass(), attrName);
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                value = field.get(object);
                if (value == null && object instanceof ProxyFactory.Proxy) {
                    continue;
                }
                if (!convertValue(resultSetParams.getString(F.VALUE)).equals(convertValue(value))) {
                    set.add(new Object[]{objectId, resultSetParams.getLong(F.ATTR_ID), value});
                }
            }
            for (Object[] param: set) {
                updateParam((long) param[0], (long) param[1], param[2]);
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(C object) {
        String[] whereFields = new String[]{F.OBJECT_ID};
        Object[] whereValues = new Object[]{object.getId()};
        try {
            SQLCreator.delete(statement, T.OBJECTS, whereFields, whereValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObjectCache.remove(object.getId());
        return true;
    }

    @Override
    public C get(long objectId) {
        if (ObjectCache.isExist(objectId)) {
            return (C) ObjectCache.get(objectId);
        }
        try (ResultSet params = SQLCreator.getParams(statement, objectId)) {
            if (tClass == null || !params.next()) {
                return null;
            }
            C object;
            ProxyFactory.Proxy proxy = ProxyFactory.getProxy(tClass.getName());
            if (proxy == null) {
                object = tClass.newInstance();
            } else {
                object = (C) proxy;
            }
            do {
                String value = params.getString(F.VALUE);
                if (value != null) {
                    Field field = searchFieldInClass(object.getClass(), params.getString(F.ATTR_NAME));
                    if (field == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    String attrTypeName = params.getString(F.ATTR_TYPE_NAME);

                    Converter.ConverterFactory converterFactory = Converter.getConverterFactory(attrTypeName);
                    if (converterFactory == null) {
                        converterFactory = Converter.getConverterFactory(Long.class.getName());
                    }
                    field.set(object, converterFactory.convert(value));
                }
            } while (params.next());
            ObjectCache.add(objectId, object);
            return object;
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<C> getAll() {
        Set<C> result = new HashSet<>();
        Set<Long> objectIds = new LinkedHashSet<>();
        try (ResultSet resultSetObjects = SQLCreator.getObjects(statement, tClass.getName())){
            while (resultSetObjects.next()) {
                objectIds.add(resultSetObjects.getLong(F.OBJECT_ID));
            }
            for (Long objectId: objectIds) {
                result.add(get(objectId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        statement.close();
    }

    private long getObjectTypeId(Class objectClass) {
        String[] fieldsSelect = new String[]{F.OBJECT_TYPE_ID};
        String[] whereFields = new String[]{F.NAME};
        Object[] whereValues = new Object[]{objectClass.getName()};
        try (ResultSet resultSet = SQLCreator.select(statement, T.OBJECT_TYPES,
                fieldsSelect, whereFields, whereValues)) {
            if (resultSet.next()) {
                return resultSet.getLong(F.OBJECT_TYPE_ID);
            } else {
                return addObjectTypeId(objectClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private long addObjectTypeId(Class objectClass) {
        long parentTypeId = 0;
        if (objectClass.getSuperclass() != null) {
            parentTypeId = getObjectTypeId(objectClass.getSuperclass());
        }
        try {
            String[] fields = new String[]{F.PARENT_TYPE_ID, F.NAME};
            Object[] value = new Object[]{parentTypeId == 0 ? null: parentTypeId, objectClass.getName()};
            SQLCreator.insert(statement, T.OBJECT_TYPES, fields, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getObjectTypeId(objectClass);
    }

    private long addObject(long objectTypesId) {
        String[] fields = new String[]{F.OBJECT_TYPE_ID};
        Object[] value = new Object[]{objectTypesId};
        try {
            SQLCreator.insert(statement, T.OBJECTS, fields, value);
            try (ResultSet resultSet = SQLCreator.getLengthField(statement, T.OBJECTS,
                    F.OBJECT_ID, F.LAST_ID)) {
                if (resultSet.next()) {
                    return resultSet.getLong(F.LAST_ID);
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    private boolean needBind(long objectTypesId) {
        String[] fieldsSelect = new String[]{F.ATTR_ID};
        String[] whereFields = new String[]{F.OBJECT_TYPE_ID};
        Object[] whereValues = new Object[]{objectTypesId};
        try (ResultSet resultSet = SQLCreator.select(statement, T.ATTR_BINDS,
                fieldsSelect, whereFields, whereValues)) {
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addAttrBind(long objectTypeId, long attrId) {
        String[] fields = new String[]{F.OBJECT_TYPE_ID, F.ATTR_ID};
        Object[] value = new Object[]{objectTypeId, attrId};
        try {
            SQLCreator.insert(statement, T.ATTR_BINDS, fields, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long getAttrTypeId(String attrTypeName) {
        String[] fieldsSelect = new String[]{F.ATTR_TYPE_ID};
        String[] whereFields = new String[]{F.NAME};
        Object[] whereValues = new Object[]{attrTypeName};
        try (ResultSet resultSet = SQLCreator.select(statement, T.ATTR_TYPES,
                fieldsSelect, whereFields, whereValues)) {
            if (resultSet.next()) {
                return resultSet.getLong(F.ATTR_TYPE_ID);
            } else {
                addAttrTypeId(attrTypeName);
                return getAttrTypeId(attrTypeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void addAttrTypeId(String attrTypeName) {
        String[] fields = new String[]{F.NAME};
        Object[] value = new Object[]{attrTypeName};
        try {
            SQLCreator.insert(statement, T.ATTR_TYPES, fields, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long getAttrId(String attrName, long attrTypeId) {
        String[] fieldsSelect = new String[]{F.ATTR_ID};
        String[] whereFields = new String[]{F.ATTR_TYPE_ID, F.NAME};
        Object[] whereValues = new Object[]{attrTypeId, attrName};
        try (ResultSet resultSet = SQLCreator.select(statement, T.ATTRIBUTES,
                fieldsSelect, whereFields, whereValues)) {
            if (resultSet.next()) {
                return resultSet.getLong(F.ATTR_ID);
            } else {
                addAttrId(attrName, attrTypeId);
                return getAttrId(attrName, attrTypeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void addAttrId(String attrName, long attrTypeId) {
        String[] fields = new String[]{F.ATTR_TYPE_ID, F.NAME};
        Object[] value = new Object[]{attrTypeId, attrName};
        try {
            SQLCreator.insert(statement, T.ATTRIBUTES, fields, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addParam(long objectId, long attrId, Object fieldValue) {
        String[] fields = new String[]{F.OBJECT_ID, F.ATTR_ID, F.VALUE};
        Object[] value = new Object[]{objectId, attrId, fieldValue};
        try {
            SQLCreator.insert(statement, T.PARAMS, fields, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateParam(long objectId, long attrId, Object fieldValue) {
        String[] fields = new String[]{F.VALUE};
        Object[] values = new Object[]{fieldValue};
        String[] whereFields = new String[]{F.OBJECT_ID, F.ATTR_ID};
        Object[] whereValues = new Object[]{objectId, attrId};
        try {
            SQLCreator.update(statement, T.PARAMS, fields, values, whereFields, whereValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Field searchFieldInClass(Class objectClass, String fieldName) {
        try {
            return objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (objectClass.getSuperclass() != null) {
                return searchFieldInClass(objectClass.getSuperclass(), fieldName);
            }
        }
        return null;
    }

    private String convertValue(Object o) {
        if (o == null) {
            return "null";
        } else if (o instanceof Set) {
            return Arrays.toString(((Set) o).toArray()).replace("[", "").replace("]", "");
        } else if (o instanceof Date) {
            return new SimpleDateFormat().format(o);
        } else {
            return o.toString();
        }
    }

}
