package com.lil.maven.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author:lil
 * @Date: 2022-02-25
 */
public class UrlUtil {
    /**
     * 调用sendPost方法访问微信的codesession2接口获取openId等信息
     * @param url 服务器地址
     * @param paramMap 哈希表
     * @return
     */
    public static String sendPost(String url, Map<String,String> paramMap){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }
        try{
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("Accept-Charset","utf-8");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while((line = in.readLine()) != null){
                result += line;
            }

        }catch (Exception e){
            System.out.println(e);
        }
        finally {
            try{
                if (out != null){
                    out.close();
                }
                if (in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
