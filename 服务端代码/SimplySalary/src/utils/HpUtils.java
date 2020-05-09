package utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;


public class HpUtils {
	//将字符串转换为JSON对象
	public static JSONObject strToJson(HttpServletRequest request){
		JSONObject object = null;
		try {
			BufferedReader read = request.getReader();
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = read.readLine()) != null){
				sb.append(line);
			}
			String req = sb.toString();
			if(null != req)
			object = JSONObject.fromObject(req);
			System.out.println("result1:"+req);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return object;
	}

}
