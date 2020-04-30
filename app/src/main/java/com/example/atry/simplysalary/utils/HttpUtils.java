package com.example.atry.simplysalary.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 李维: TZZ on 2019-12-01 19:27
 * 邮箱: 3182430026@qq.com
 */
public class HttpUtils {
    public static final MediaType JSON = MediaType.parse("application/json;charset = utf-8");
    private static OkHttpClient client = new OkHttpClient();
    /**
     * get请求，不带参数
     * @param requestUrl
     * @param callback
     */
    public static void sendRequest(String requestUrl,okhttp3.Callback callback){
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * GET请求 （参数为字符串map）
     * @param reqeustUrl
     * @param params
     * @param callback
     */
    public static void sendRequest(String reqeustUrl, LinkedHashMap<String,String> params,okhttp3.Callback callback){
        reqeustUrl = attachHttpGetParams(reqeustUrl,params);
        Request request = new Request.Builder()
                .url(reqeustUrl)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * POST请求 （参数为字符串）
     * @param address
     * @param params
     * @param callback
     */
    public static void sendPost(String address,LinkedHashMap<String,String> params,okhttp3.Callback callback)  {
        FormBody.Builder builder = new FormBody.Builder();
        //builder填充参数，构造请求体
        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        for(int i = 0;i < params.size();i++){
            builder.add(keys.next(),values.next());
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);

    }

    /**
     * POST请求(参数为JSON格式)
     * @param address
     * @param json
     * @param callback
     */
    public static void sendPost(String address,String json,okhttp3.Callback callback){
        //创建一个RequestBoby
        RequestBody requestBody = RequestBody.create(JSON,json);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }




    private static String attachHttpGetParams(String url,LinkedHashMap<String,String> params){
        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for(int i=0;i < params.size();i++){
            stringBuffer.append(keys.next()+"="+values.next());
            if(i != params.size() -1){
                stringBuffer.append("&");
            }
        }
        System.out.println("url:"+url+stringBuffer.toString());
        return url + stringBuffer.toString();
    }
}
