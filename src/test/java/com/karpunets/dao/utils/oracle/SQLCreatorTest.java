package com.karpunets.dao.utils.oracle;

import static com.karpunets.dao.utils.oracle.DBConst.*;

import com.karpunets.pojo.Qualification;
import com.karpunets.pojo.Task;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 10.03.2017
 */
public class SQLCreatorTest {

    @Test(expected=NullPointerException.class)
    public void select() throws Exception {
        String[] fieldsSelect = new String[]{F.OBJECT_ID, F.VALUE};
        String[] whereFields = new String[]{F.ATTR_ID, F.VALUE};
        Object[] whereValues = new Object[]{15, "text"};
        SQLCreator.select(null, T.PARAMS, fieldsSelect, whereFields, whereValues);
    }

    @Test(expected=NullPointerException.class)
    public void insert() throws Exception {
        Task t1 = new Task();
        t1.setId(1L);
        Task t2 = new Task();
        t2.setId(2L);
        Task t3 = new Task();
        t3.setId(3L);

        Set set = new LinkedHashSet<Task>();
        set.add(t1);
        set.add(t2);
        set.add(t3);

        String[] fields = new String[]{F.ATTR_ID, F.VALUE};
        Object[] value = new Object[]{15, set};
        SQLCreator.insert(null, T.PARAMS, fields, value);
    }

    @Test(expected=NullPointerException.class)
    public void getLengthField() throws Exception {
        SQLCreator.getLengthField(null, T.OBJECTS, F.OBJECT_ID, F.LAST_ID);
    }

    @Test(expected=NullPointerException.class)
    public void update() throws Exception {
        String[] fields = new String[]{F.ATTR_ID, F.VALUE};
        Object[] values = new Object[]{1, Qualification.MIDDLE};
        String[] whereFields = new String[]{F.OBJECT_ID, F.ATTR_ID};
        Object[] whereValues = new Object[]{15, 2};
        SQLCreator.update(null, T.OBJECTS, fields, values, whereFields, whereValues);

    }

    @Test(expected=NullPointerException.class)
    public void delete() throws Exception {
        String[] whereFields = new String[]{F.OBJECT_ID, F.ATTR_ID, F.VALUE};
        Object[] whereValues = new Object[]{15, 2, null};
        SQLCreator.delete(null, T.OBJECTS, whereFields, whereValues);
    }

}