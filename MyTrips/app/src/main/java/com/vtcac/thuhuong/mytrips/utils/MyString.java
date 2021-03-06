package com.vtcac.thuhuong.mytrips.utils;

public class MyString {
    public static boolean isEmpty(String s) {
        if(s == null || s.trim().length() ==0 )return true;
        return false;
    }

    /**
     * Returns signed money text with thousand comma.
     *
     * @param amount
     * @return
     */
    public static String getMoneyText(double amount) {
        return String.format("%,.2f", amount).replace(".00", "");
    }
}
