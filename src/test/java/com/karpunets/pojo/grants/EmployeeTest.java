package com.karpunets.pojo.grants;

import com.karpunets.random.Randomizer;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class EmployeeTest {

    public static Employee getRandomEmployee() {
        Employee employee = new Employee();
        employee.setName(Randomizer.getRandomString());
        employee.setSurname(Randomizer.getRandomString());
        employee.setLogin(Randomizer.getRandomString());
        employee.setPassword(Randomizer.getRandomString());
        employee.setEmail(Randomizer.getRandomString());
        employee.setNumber(Randomizer.getRandomNumber());
        employee.setQualification(Randomizer.getRandomQualifications());
        return employee;
    }

}