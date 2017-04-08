package com.karpunets.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Karpunets
 * @since 02.02.2017
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyObject implements Serializable {

    @XmlAttribute
    private Long id;
    @XmlAttribute
    private Date creatingDate;

    public CompanyObject() {
        creatingDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
