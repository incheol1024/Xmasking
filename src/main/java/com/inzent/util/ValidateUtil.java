package com.inzent.util;

public class ValidateUtil {

    public static boolean validateStringsIsNull(String... strings) {
        for (String string : strings) {
            if (string == null || string.equals(""))
                return false;
        }
        return true;
    }
}
