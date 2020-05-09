package utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.JSONException;

public class CommonResponse {
    public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	private String resCode = "";
    private String resMsg = "";

    //简单信息
    private HashMap<String, String> propertyMap;
    //对JSONArray 的封装
    JSONArray jsonarray;
    public CommonResponse() {

        propertyMap = new HashMap<>();
        jsonarray = new JSONArray();
     
    }
    public void setResult(String key,String value){
    	propertyMap.put(key, value);
    }
    
    public void setResult(JSONObject value){
    	jsonarray.add(value);
    }
    public void setResult(Object value){
    	jsonarray.add(value);
    }
    
    public String getJsonStr(){
    	JSONObject object = new JSONObject();
    	JSONObject param = JSONObject.fromObject(propertyMap);
    	object.put("resCode", resCode);
    	object.put("resMsg",resMsg);
    	object.put("property",param);
    	object.accumulate("list", jsonarray);
    	return object.toString();
    }
	@Override
	public String toString() {
		return "CommonResponse [resCode=" + resCode + ", resMsg=" + resMsg
				+ ", propertyMap=" + propertyMap + "]";
	}
    

}


