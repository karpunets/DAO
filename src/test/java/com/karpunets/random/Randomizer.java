package com.karpunets.random;

import com.karpunets.pojo.Qualification;

import java.util.Random;

/**
 * @author Karpunets
 * @since 23.03.2017
 */
public class Randomizer {

    private static Random random = new Random();

    public static String getRandomString() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        return random(chars,10);
    }

    public static String getRandomNumber() {
        char[] chars = "0123456789".toCharArray();
        return random(chars, 10);
    }

    public static Boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    public static Qualification getRandomQualifications() {
        Qualification[] qualifications = Qualification.values();
        return qualifications[random.nextInt(qualifications.length)];
    }

    private static String random(char[] chars, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
