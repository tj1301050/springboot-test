package com.hdedu.utils;

/**
 * @author tianjian
 * @className StringUtils
 * @description TODO
 * @date 2021/11/4 11:53
 */
public class StringUtils {

    /**
     * 字符串页数截取
     * @param str
     * @return
     */
    public static String strSub(String str) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
            str = str.substring(0, str.indexOf("页"));
            str = str.substring(1, str.length()).replaceAll(" ", "");
            return str;
        }
        return null;
    }
}