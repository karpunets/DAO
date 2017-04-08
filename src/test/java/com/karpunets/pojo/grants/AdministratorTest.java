package com.karpunets.pojo.grants;

import java.io.File;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class AdministratorTest {

    public static Administrator getMainAdministrator() throws Exception {
        Administrator administrator = new Administrator();
        administrator.setLogin("karpunets");
        administrator.setPassword("passkarpo");
        administrator.setName("Arthur");
        administrator.setSurname("Karpunets");
        administrator.setEmail("arthur.karpunets@gmail.com");
        administrator.setNumber("0637926099");
        administrator.setPhotoUrl(new File("1.jpg"));
        return administrator;
    }

}