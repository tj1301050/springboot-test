package com.hdedu.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * @author tianjian
 * @className MD5Utils
 * @description TODO
 * @date 2021/10/28 9:18
 */
public class MD5Utils {

    /**
     * 将字符串进行MD5加密后大写输出
     *
     * @param s 需要加密的字符串
     * @return 加密后的MD5值
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
           if (StringUtils.isNotBlank(s)){
               byte[] btInput = s.getBytes();
               // 获得MD5摘要算法的 MessageDigest 对象
               MessageDigest mdInst = MessageDigest.getInstance("MD5");
               // 使用指定的字节更新摘要
               mdInst.update(btInput);
               // 获得密文
               byte[] md = mdInst.digest();
               // 把密文转换成十六进制的字符串形式
               int j = md.length;
               char str[] = new char[j * 2];
               int k = 0;
               for (int i = 0; i < j; i++) {
                   byte byte0 = md[i];
                   str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                   str[k++] = hexDigits[byte0 & 0xf];
               }
               return new String(str);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

//        MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEjkCBrgH4r5vCFjjw4rl8e325QAaQwfrIaxHe5g/Bk9sxG/JJQW+SqbEl/gwsoqF2C4caE3DtSfFAJuC4OCkyHg==%%1%%ME0CAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEMzAxAgEBBCDiQaFEbG16suTjRcPtv9IiM3bE7JHbs2srgv3WcB7BFqAKBggqhkjOPQMBBw==%%1635387057
//
//        0DDAE3F8441B15FFC6AC13C6A070E8CE


        System.out.println(MD5("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEjkCBrgH4r5vCFjjw4rl8e325QAaQwfrIaxHe5g/Bk9sxG/JJQW+SqbEl/gwsoqF2C4caE3DtSfFAJuC4OCkyHg==%%1%%ME0CAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEMzAxAgEBBCDiQaFEbG16suTjRcPtv9IiM3bE7JHbs2srgv3WcB7BFqAKBggqhkjOPQMBBw==%%1635387057"));
    }
}