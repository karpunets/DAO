package com.karpunets.dao.utils.oracle;

import com.karpunets.pojo.Qualification;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author Karpunets
 * @since 04.02.2017
 */
public class SQLCreator {

    private static final String SELECT = "SELECT %s FROM %s";
    private static final String SELECT_WHERE = SELECT + " WHERE %s";
    private static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String UPDATE = "UPDATE %s SET %s WHERE %s";
    private static final String DELETE = "DELETE FROM %s WHERE %s";

    private static final String AND = " AND ";
    private static final String COMMA = ",";

    private static final String GET_PARAMS =
            "SELECT attr_id, value, attributes.name AS attr_name, attr_types.name AS attr_type_name\n" +
                    "FROM params INNER JOIN attributes USING (attr_id) INNER JOIN attr_types USING (attr_type_id)\n" +
                    "WHERE object_id = %d";

    private static final String SEARCH_GRANT_WITH_PASSWORD =
            "SELECT object_id, name AS type\n" +
                    "FROM objects INNER JOIN object_types USING(object_type_id)\n" +
                    "WHERE object_id = (\n" +
                    "  SELECT OBJECT_ID\n" +
                    "  FROM params\n" +
                    "  WHERE\n" +
                    "    object_id = (\n" +
                    "      SELECT object_id\n" +
                    "      FROM params\n" +
                    "      WHERE attr_id = (SELECT attr_id FROM attributes WHERE name = 'login') AND\n" +
                    "            value = ?\n" +
                    "    ) AND\n" +
                    "    attr_id = (SELECT attr_id FROM attributes WHERE name = 'password') AND\n" +
                    "    value = ?\n" +
                    ")";

    private static final String GET_OBJECTS = "SELECT object_id\n" +
            "FROM objects INNER JOIN object_types USING(object_type_id)\n" +
            "WHERE name = '%s'";

    private static final String SEARCH_TYPE = "SELECT name\n" +
            "FROM object_types INNER JOIN objects USING (object_type_id)\n" +
            "WHERE object_id = ?";

    private static final String SEARCH_GRANT = "SELECT object_id\n" +
            "FROM PARAMS\n" +
            "WHERE value = ?\n" +
            "  AND attr_id = (SELECT attr_id FROM attributes WHERE name='login')";


    private static final String UPDATE_OBJECT_TYPE = "UPDATE objects\n" +
            "SET object_type_id = (SELECT object_type_id FROM object_types WHERE name = ?)\n" +
            "WHERE object_id = ?";

    private static final String INSERT_NEW_PARAM = "INSERT INTO params (object_id, attr_id)\n" +
            "VALUES (?, (SELECT attr_id FROM attributes WHERE name = ?))";

    private static final String DELETE_PARAM = "DELETE FROM params\n" +
            "WHERE object_id = ? AND attr_id = (SELECT attr_id FROM attributes WHERE name = ?)";

    private static final String GET_EMPLOYEES_LIKE = "SELECT objects.object_id, object_types.name\n" +
            "FROM objects INNER JOIN object_types ON objects.object_type_id = object_types.object_type_id\n" +
            "            INNER JOIN params ON objects.object_id = params.object_id\n" +
            "            INNER JOIN attributes ON params.attr_id = attributes.attr_id\n" +
            "WHERE (object_types.name = 'com.karpunets.pojo.grants.Employee' OR\n" +
            "      object_types.name = 'com.karpunets.pojo.grants.Manager') AND\n" +
            "      (attributes.name = 'name' OR attributes.name = 'surname') AND lower(params.value) LIKE ?";

    public static ResultSet select(Statement statement, String table, String[] fieldsSelect, String[] whereFields, Object[] whereValues) throws SQLException {
        String where = arraysToString(whereFields, whereValues, AND);
//        System.out.println(String.format(SELECT_WHERE, String.join(COMMA, fieldsSelect), table, where));
        return statement.executeQuery(String.format(SELECT_WHERE, String.join(COMMA, fieldsSelect), table, where));
    }

    public static void insert(Statement statement, String table, String[] fields, Object[] values) throws SQLException {
        System.out.println(String.format(INSERT, table, String.join(COMMA, fields), objectsToValues(values)));
        statement.executeUpdate(String.format(INSERT, table, String.join(COMMA, fields), objectsToValues(values)));
    }

    public static ResultSet getLengthField(Statement statement, String table, String field, String as) throws SQLException {
//        System.out.println(String.format(SELECT, "max(" + field + ") AS " + as, table));
        return statement.executeQuery(String.format(SELECT, "max(" + field + ") AS " + as, table));
    }

    public static void update(Statement statement, String table, String[] fields, Object[] values, String[] whereFields, Object[] whereValues) throws SQLException {
        String set = arraysToString(fields, values, COMMA);
        String where = arraysToString(whereFields, whereValues, AND);
        System.out.println(String.format(String.format(UPDATE, table, set, where)));
        statement.executeUpdate(String.format(UPDATE, table, set, where));
    }

    public static void delete(Statement statement, String table, String[] whereFields, Object[] whereValues) throws SQLException {
        String where = arraysToString(whereFields, whereValues, AND);
        System.out.println(String.format(DELETE, table, where));
        statement.executeUpdate(String.format(DELETE, table, where));
    }

    public static ResultSet getParams(Statement statement, long objectId) throws SQLException {
        return statement.executeQuery(String.format(GET_PARAMS, objectId));
    }

    public static ResultSet getObjects(Statement statement, String objectType) throws SQLException {
        return statement.executeQuery(String.format(GET_OBJECTS, objectType));
    }

    public static ResultSet searchGrant(Connection connection, String login, String password) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(SEARCH_GRANT_WITH_PASSWORD);

        pstmt.setString(1, login);
        pstmt.setString(2, password);

        return pstmt.executeQuery();
    }

    public static ResultSet searchGrant(Connection connection, String login) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(SEARCH_GRANT);

        pstmt.setString(1, login);

        return pstmt.executeQuery();
    }


    public static ResultSet getObjectType(Connection connection, long id) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(SEARCH_TYPE);

        pstmt.setString(1, String.valueOf(id));

        return pstmt.executeQuery();
    }

    public static void updateObjectType(Connection connection, long id, String type) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(UPDATE_OBJECT_TYPE);

        pstmt.setString(1, type);
        pstmt.setLong(2, id);

        pstmt.executeUpdate();
    }

    public static void insertNewParams(Connection connection, Long id, Field[] fields) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(INSERT_NEW_PARAM);
        pstmt.setLong(1, id);
        for (Field field: fields) {
            pstmt.setString(2, field.getName());
            pstmt.executeUpdate();
        }
    }

    public static void deleteParams(Connection connection, Long id, Field[] fields) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(DELETE_PARAM);
        pstmt.setLong(1, id);
        for (Field field: fields) {
            pstmt.setString(2, field.getName());
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getEmployeesLike(Connection connection, String query) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(GET_EMPLOYEES_LIKE);
        pstmt.setString(1, "%"+query+"%");
        return pstmt.executeQuery();
    }

    private static String objectsToValues(Object... objects) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            stringBuilder.append(valueToString(objects[i]));
            if (i != objects.length - 1) {
                stringBuilder.append(COMMA);
            }
        }
        return stringBuilder.toString();
    }

    private static String arraysToString(String[] field, Object[] value, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            stringBuilder.append(field[i]);
            stringBuilder.append('=');
            stringBuilder.append(valueToString(value[i]));
            if (i != field.length - 1) {
                stringBuilder.append(separator);
            }
        }
        return stringBuilder.toString();
    }

    private static String valueToString(Object value) {
        if (value == null) {
            return "NUll";
        } else if (value instanceof  String
                || value instanceof  Qualification
                || value instanceof  File
                || value instanceof  Boolean) {
            return "'" + value.toString().replace("'", "''") + "'";
        } else if (value instanceof  Set) {
            return "'" + Arrays.toString(((Set) value).toArray()).replace("[", "").replace("]", "") + "'";
        } else if (value instanceof  Date) {
            return "'" + new SimpleDateFormat().format(value) + "'";
        } else {
            return value.toString();
        }
    }


}
