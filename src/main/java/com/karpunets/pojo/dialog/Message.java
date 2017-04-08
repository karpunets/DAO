package com.karpunets.pojo.dialog;

import com.karpunets.pojo.CompanyObject;
import com.karpunets.pojo.grants.Employee;

/**
 * @author Karpunets
 * @since 10.03.2017
 */
public class Message extends CompanyObject {

    private String text;
    private Employee author;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }
}
