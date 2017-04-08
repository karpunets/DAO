package com.karpunets.pojo;

import com.karpunets.random.Randomizer;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class SprintTest {

    public static Sprint getSimpleSprint() {
        Sprint sprint = new Sprint();
        sprint.setDescription(Randomizer.getRandomString());
        if (Randomizer.getRandomBoolean()) {
            sprint.finish();
        }
        return sprint;
    }

}