package com.example.atry.simplysalary.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//自定义的请求体
public class CommonRequest {
    //请求码
    private String requestCode;
    //请求参数：使用hashmap来实现
    private HashMap<String,String> requestParam;
    //请求参数，请求数组参数
    private JSONArray jsonArray = new JSONArray();
    public CommonRequest(){

        requestParam = new HashMap<>();
    }
    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    //设置请求参数
    public void addRequestParam(String paramKey,String paramValue){
        requestParam.put(paramKey,paramValue);
    }
    //设置请求数组
    public void addRequestParam(Object value){
        jsonArray.put(value);
    }
    //设置请求JSONObject数组
    public void addRequestParam(JSONObject jsonObject){
        jsonArray.put(jsonObject);
    }

    public String getJsonStr(){

        JSONObject object = new JSONObject();
        JSONObject param = new JSONObject(requestParam);

        try {
            object.put("requestCode",requestCode);
            object.put("requestParam",param);
            object.accumulate("list",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();

    }
}
