package com.mayinews.mv.utils;

/**
 * 将数字转换为以万为单位的
 */


public class Number2Thousand {
    public static String number2Thousand(String number) {

        if (number.trim().length() < 4) {  //字符串的长度为3位
            return number;
        } else if (number.trim().length() < 5) {   //字符串的长度为4位
            int i = Integer.parseInt(number);
            double dou = i * 1.0 / 10000;
            String s = dou + "";
            String substring = s.substring(0, 3);
            return substring+"万";


        }else if(number.trim().length() < 6){

            int i = Integer.parseInt(number);
            double dou = i * 1.0 / 10000;
            String s = dou + "";
            String substring = s.substring(0, 4);
            return substring+"万";

        }
        return null;
    }
}
