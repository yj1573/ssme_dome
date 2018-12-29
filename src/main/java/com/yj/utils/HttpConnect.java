package com.yj.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnect {


    public static String post(String url, String param) {
        StringBuffer sb = new StringBuffer();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json;charset:utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(1000*10);
            conn.setConnectTimeout(1000*10);
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String s = null;
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            close(in, out);
        }
        return sb.toString();
    }


    public static String get(String url, String param) {
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url + "?" + param);
            URLConnection c = realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) c;
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setReadTimeout(1000*10);
            conn.setConnectTimeout(1000*10);
            if (conn.getResponseCode() >= 300) {
                throw new RuntimeException(
                        "HTTP Request is not success, Response code is " + conn.getResponseCode());
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String s = null;
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            close(in, null);
        }
        return sb.toString();
    }


    private static void close(Reader r, Writer w) {
        try {
            if (r != null) {
                r.close();
            }
            if (w != null) {
                w.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
