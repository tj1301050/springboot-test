package com.hdedu.utils;

import java.io.*;

/**
 * @author tianjian
 * @className GetResourceFileUtils
 * @description TODO
 * @date 2021/10/26 18:44
 */
public class GetResourceFileUtils {

    /**
     * 读取resource中的配置文件
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readFileByLines(String fileName) throws IOException {
        FileInputStream fileInputStream;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader;
        String context = "";
        String tempString;
        try {
            fileInputStream = new FileInputStream(fileName);
            inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((tempString = bufferedReader.readLine()) != null) {
                context += tempString;
            }
            bufferedReader.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return context;
    }
}