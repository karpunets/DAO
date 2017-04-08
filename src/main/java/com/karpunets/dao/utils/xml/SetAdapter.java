package com.karpunets.dao.utils.xml;

import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.pojo.CompanyObject;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 20.02.2017
 */
public class SetAdapter extends XmlAdapter<CompanySet, Set<? extends CompanyObject>> {

    @Override
    public Set<? extends CompanyObject> unmarshal(CompanySet companySet) throws Exception {
        if (companySet == null) {
            return null;
        }
        Set<CompanyObject> set = new HashSet<>();
        for (CompanyObject object: companySet.getItem()) {
            if (ObjectCache.isExist(object.getId())) {
                set.add(ObjectCache.get(object.getId()));
            } else {
                ObjectCache.add(object.getId(), object);
                set.add(object);
            }
        }
        return set;
    }
    @Override
    public CompanySet marshal(Set<? extends CompanyObject> taskSet) throws Exception {
        if (taskSet == null) {
            return null;
        }
        return new CompanySet(taskSet);
    }
}
