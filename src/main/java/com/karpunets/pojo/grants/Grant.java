package com.karpunets.pojo.grants;


import com.karpunets.pojo.CompanyObject;

import java.io.File;

/**
 * @author Karpunets
 * @since 01.02.2017
 */

public class Grant extends CompanyObject {

    private String login;
    private String password;

    private String name;
    private String surname;

    private String email;
    private String number;
    private File photoUrl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public File getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(File photoUrl) {
        this.photoUrl = photoUrl;
    }
}
