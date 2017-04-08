package com.karpunets.dao.utils.xml;

import com.karpunets.pojo.CompanyObject;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 21.02.2017
 */
public class CompanySet {
    @XmlElementWrapper(name = "hashSet")
    private Set<? extends CompanyObject> item = new HashSet<>();

    public CompanySet(Set<? extends CompanyObject> set) {
        this.item = set;
    }

    public CompanySet(){}

    public Set<? extends CompanyObject> getItem() {
        return item;
    }
}
