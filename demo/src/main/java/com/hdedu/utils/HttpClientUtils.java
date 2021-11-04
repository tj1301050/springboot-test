package com.hdedu.utils;



import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tianjian
 * @className HttpClientUtils
 * @description TODO
 * @date 2021/10/25 14:50
 */
public class HttpClientUtils {

    /**
     * httpGet请求无参形式
     * @param url api请求的url
     * @return 返回api的结果集
     * @throws IOException
     */
    public static String HttpGetWithOutParam(String url) throws IOException {
        String context = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Connection.Response connectResponse = (Connection.Response) Jsoup.connect("https://apollo.siycrm.com/").timeout(3000).execute();
        Map<String,String> cookies = connectResponse.cookies();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String setCookie = response.getFirstHeader("Set-Cookie").getValue();
        System.out.println("============="+ setCookie);
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                context = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
        return context;
    }

    /**
     * http请求(有参形式)
     *
     * @param list 参数
     * @param url  调用路径
     * @return 返回值
     * @throws IOException
     */
    public static String HttpGetWithParam(List<NameValuePair> list, String url, HttpServletRequest request) throws IOException {
        String context = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            URI uri = new URIBuilder(url).setParameters(list).build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                context = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        finally {
            if (response != null){
                response.close();
            }
            httpClient.close();
        }
        return context;
    }

    /**
     * httpPost请求(无参形式)
     *
     * @param url 请求url
     * @return 调用api的返回值
     * @throws IOException
     */
    public static String httpPostWithOutParam(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        CloseableHttpResponse response = null;
        String context = null;
        try {
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                context = EntityUtils.toString(response.getEntity(), "UTF-8");

            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
            return context;
        }
    }

    /**
     * httpPost请求(有参形式)
     * @param list 请求参数
     * @param url 请求url
     * @return
     * @throws IOException
     */
    public static String httpPostWithParam(List<NameValuePair> list, String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UrlEncodedFormEntity formEntity = null;
        CloseableHttpResponse response = null;
        String context = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            formEntity = new UrlEncodedFormEntity(list);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(formEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                context = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
        return context;
    }


    public static String httpPost(String url,Map<String,String> params){

        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.声明post请求
        HttpPost httpPost = new HttpPost(url);
        //3.设置请求类型
        httpPost.setHeader("Content-Type", "application/json");
        //4.添加参数
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {

            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        UrlEncodedFormEntity formEntity;
        try {

            formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
            httpPost.setEntity(formEntity);

            //5.发送请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){

                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");
            }
            //6.关闭资源
            response.close();
            httpClient.close();
        } catch (UnsupportedEncodingException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public static String doPostBody(String url, String paramString, String timestamp,String signature,String accessToken) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("timestamp",timestamp);
        httpPost.addHeader("signature",signature);
        httpPost.addHeader("accessToken",accessToken);
        // 为httpPost设置封装好的请求参数
        try {
            httpPost.setEntity(new StringEntity(paramString, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doGet(String url, Map<String, String> params, String cookie) throws Exception{
        String res = "";
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        // 创建uri
        URIBuilder builder = new URIBuilder(url);
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addParameter(key, params.get(key));
            }
        }
        URI uri = builder.build();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Cookie",cookie);
        try {
            // 执行请求操作，并拿到结果（同步阻塞）
            response = httpClient.execute(httpGet);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200 && entity != null) {
                //按指定编码（这里为UTF8编码）转换结果实体为String类型
                res = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpGet.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
        return res;
    }
}