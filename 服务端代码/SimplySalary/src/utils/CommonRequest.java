package utils;


import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.struts2.json.JSONException;
//自定义的请求体
public class CommonRequest {
    //请求码
    private String requestCode;
    //请求参数：使用hashmap来实现
    private HashMap<String,String> requestParam;

    public CommonRequest(){
        requestCode = "";
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

    public String getJsonStr(){

        JSONObject object = new JSONObject();
        JSONObject param = JSONObject.fromObject(requestParam);

        object.put("requestCode",requestCode);
		object.put("requestParam",param);

        return object.toString();

    }
}
