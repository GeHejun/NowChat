package com.ghj.common.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author gehj
 * @date 2019/6/2517:15
 */
public class OKHttpUtil {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    /**
     * @param url getUrl
     * @return java.lang.String
     * @author xiaobu
     * @date 2019/3/4 11:20
     * @descprition
     * @version 1.0
     */
    public static String sendByGetUrl(String url) {
        String result;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            assert response.body() != null;
            result = response.body().string();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url , json
     * @return java.lang.String
     * @author xiaobu
     * @date 2019/3/4 11:13
     * @descprition
     * @version 1.0 post+json方式
     */
    public static String sendByPostJson(String url, String json) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * @author xiaobu
     * @date 2019/3/4 15:58
     * @param url , params]
     * @return java.lang.String
     * @descprition  post方式请求
     * @version 1.0
     */
    public static String sendByPostMap(String url, Map<String, String> params) {
        String result;
        OkHttpClient client = new OkHttpClient();
        StringBuilder content = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            content.append(entry.getKey()).append("=").append(entry.getValue());
            if (iterator.hasNext()) {
                content.append("&");
            }
        }

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, content.toString());
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            assert response.body() != null;
            result = response.body().string();
            System.out.println("result = " + result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
