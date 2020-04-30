package com.example.atry.simplysalary.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CommonResponse {
    private String resCode = "";
    private String resMsg = "";

    //简单信息
    private HashMap<String, String> propertyMap;

    //列表类信息
    private ArrayList<HashMap<String, String>> mapList;

    /**
     * 通用报文返回构造函数
     *
     * @param responseString Json 格式的返回字符串
     */
    public CommonResponse(String responseString) {
        //先看一下返回结果
        Log.i("Response", responseString);

        propertyMap = new HashMap<>();
        mapList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(responseString);
            resCode = root.optString("resCode");
            resMsg = root.optString("resMsg");

            JSONObject property = root.optJSONObject("property");
            if (property != null) {
                parseProperty(property, propertyMap);
            }

            JSONArray list = root.optJSONArray("list");
            if(null != list){
                parseList(list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //解析最外层的json
    private void parseProperty(JSONObject property, HashMap<String, String> propertyMap) {
        Iterator<?> it = property.keys();
        while (it.hasNext()) {
            String key = it.next().toString();
            Object value = property.opt(key);
            propertyMap.put(key, value.toString());
        }
    }
    //解析列表数据
    private void parseList(JSONArray list) {
        int i = 0;
        while (i < list.length()) {
            HashMap<String, String> map = new HashMap<>();
            try {
                parseProperty(list.getJSONObject(i++), map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mapList.add(map);
        }
    }

    public String getResCode() {
        return resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public HashMap<String, String> getPropertyMap() {
        return propertyMap;
    }
    public ArrayList<HashMap<String, String>> getMapList() {
        return mapList;
    }
}


