package com.thoughtworks.gaia;

import java.util.regex.Pattern;

public class Helper {
    public static boolean isValidEmail(String email) {
        if (isNull(email)) {
            return false;
        }
        String pattern = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        return Pattern.matches(pattern, email);
    }

    public static boolean isValidTel(String tel) {
        if (isNull(tel)) {
            return false;
        }
        String pattern = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
        return Pattern.matches(pattern, tel);
    }

    public static boolean isValidSchool(String school) {
        if (isNull(school)) {
            return false;
        }
        return school.length() <= 64;
    }

    public static boolean isValidMajor(String major) {
        if (isNull(major)) {
            return false;
        }
        return major.length() <= 64;
    }

    public static boolean isValidName(String name) {
        if (isNull(name)) {
            return false;
        }
        String pattern = "^[ A-Za-z]*$";
        return Pattern.matches(pattern, name);
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public final static boolean defaultGender = true;
}
