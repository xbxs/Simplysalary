package action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.UserDao;
import service_imp.UserDaoImp;
import utils.CommonResponse;
import utils.HpUtils;

import com.opensymphony.xwork2.ModelDriven;

import entity.User;
public class UserAction extends BaseAction implements ModelDriven<User>{
	private static final long serialVersionUID = 1L;
	User user = new User();
	
	private UserDao userdao = new UserDaoImp();
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	
	//用户登录的方法
	public void login(){
		UserDao udao = new UserDaoImp();
		JSONObject object = HpUtils.strToJson(request);
		JSONObject requestParam = object.getJSONObject("requestParam");
		String account = requestParam.getString("phonenumber");
		String pwd = requestParam.getString("password");
		User user = new User();
		user.setU_phone(account);
		user.setU_pw(pwd);
		CommonResponse commonresponse = new CommonResponse();
		String flag = udao.userLogin(user);
		if(flag.equals("100")){
			commonresponse.setResCode("100");
			commonresponse.setResMsg("登录成功");
			User queryUser = udao.queryUsersByUphone(account);
			if(null != queryUser){
				commonresponse.setResult("u_phone",account);
				commonresponse.setResult("u_password", pwd);
				commonresponse.setResult("u_name", queryUser.getU_name());
				commonresponse.setResult("u_head", queryUser.getU_head());
				commonresponse.setResult("u_flag", queryUser.getU_flag());
				commonresponse.setResult("u_bas", queryUser.getU_bas()+"");
				commonresponse.setResult("u_wage", queryUser.getU_wage()+"");
			}
		}else if(flag.equals("200")){
			commonresponse.setResCode("200");
			commonresponse.setResMsg("请检查账号和密码");
		}else if(flag.equals("300")){
			commonresponse.setResCode("300");
			commonresponse.setResMsg("数据库异常");
		}else{
			commonresponse.setResCode("400");
			commonresponse.setResMsg("服务器拥堵，请稍后再试");
		}
		
		String resStr = commonresponse.getJsonStr();
		
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(resStr).flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//添加用户
	public void add(){
		User user  = new User();
		try {
		JSONObject object = HpUtils.strToJson(request);
		JSONObject requestParam = object.getJSONObject("requestParam");
		
		String account = requestParam.getString("account");
		String pwd = requestParam.getString("password");
		String flag = requestParam.getString("flag");
		String name = requestParam.getString("u_name");
		user.setU_phone(account);
		user.setU_pw(pwd);
		user.setU_flag(flag);
		user.setU_section("1");
		user.setU_name(name);
		User queryuser = userdao.queryUsersByUphone(account);
		CommonResponse commonresponse = new CommonResponse();
		
			if(null == queryuser){
				if(userdao.addUser(user)){
					commonresponse.setResCode("0");
					commonresponse.setResMsg("注册成功");
				}else{
					commonresponse.setResCode("1");
					commonresponse.setResMsg("请重试");
				}
	
			}else{
				commonresponse.setResCode("2");
				commonresponse.setResMsg("已经注册");
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String resStr = commonresponse.getJsonStr();			
			response.getWriter().append(resStr).flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}	
	
	//查询老板的好友
	public void queryBossContact(){
		JSONObject object = HpUtils.strToJson(request);
		JSONArray jsonarray = object.getJSONArray("list");
		CommonResponse commonresponse = new CommonResponse();
		UserDao userdao = new UserDaoImp();
		for(int i=0;i<jsonarray.size();i++){
			User user = userdao.queryUsersByUphone(jsonarray.getString(i));
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("u_phone", user.getU_phone());
			jsonobject.put("u_name",user.getU_name());
			commonresponse.setResult(jsonobject);
		}
		
		try{
			commonresponse.setResCode("0");
			commonresponse.setResMsg("查询好友");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String resStr = commonresponse.getJsonStr();			
			response.getWriter().append(resStr).flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	//查询用户是否存在
	public void queryUserById(){
		
		UserDao udao = new UserDaoImp();
		JSONObject object = HpUtils.strToJson(request);
		JSONObject requestParam = object.getJSONObject("requestParam");
		String account = requestParam.getString("phonenumber");
		User user = udao.queryUsersByUphone(account);
		CommonResponse commonresponse = new CommonResponse();
		if(null != user){
			commonresponse.setResCode("0");
			commonresponse.setResMsg("存在用户");
			commonresponse.setResult("u_phone",account);
			commonresponse.setResult("u_name", user.getU_name());
			commonresponse.setResult("u_head", user.getU_head());
			commonresponse.setResult("u_flag", user.getU_flag());
			commonresponse.setResult("u_bas", user.getU_bas()+"");
			commonresponse.setResult("u_wage", user.getU_wage()+"");
		}else{
			commonresponse.setResCode("1");
			commonresponse.setResMsg("该存在用户未注册");
		}
		try{
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String resStr = commonresponse.getJsonStr();			
		response.getWriter().append(resStr).flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//修改用户资料
	public void updateUser(){
		UserDao udao = new UserDaoImp();
		JSONObject object = HpUtils.strToJson(request);
		JSONObject requestParam = object.getJSONObject("requestParam");
		CommonResponse commonresponse = new CommonResponse();
		//先查询是否存在此用户

		if("salary".equals(object.get("requestCode"))){
			String account = requestParam.getString("phonenumber");
			String u_bas = requestParam.getString("u_bas");
			String u_wage = requestParam.getString("u_wage");
			User user = udao.queryUsersByUphone(account);
			if(null != user){
			user.setU_bas(Integer.parseInt(u_bas));
			user.setU_wage(u_wage);		
				if(udao.updateUser(user)){
					commonresponse.setResCode("0");
					commonresponse.setResMsg("更新成功");
				}else{
					commonresponse.setResCode("1");
					commonresponse.setResMsg("数据库异常，请稍后再试");
				}
				
			}else{
			commonresponse.setResCode("2");
			commonresponse.setResMsg("不存在此用户");
			}
		}else if("newgroup".equals(object.get("requestCode"))){
			JSONArray jsonarray = object.getJSONArray("list");
			String groupId = requestParam.getString("groupId");
			String ower = requestParam.getString("ower");
			for(int i=0;i < jsonarray.size();i++){
				User user = udao.queryUsersByUphone((String)jsonarray.get(i));
				user.setU_section(groupId);
				user.setU_ower(ower);
				udao.updateUser(user);
			}
			commonresponse.setResCode("0");
			commonresponse.setResMsg("更新用户信息成功");
		}
		
		try{
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String resStr = commonresponse.getJsonStr();			
			response.getWriter().append(resStr).flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	public void queryUserSection(){
		UserDao udao = new UserDaoImp();
		JSONObject object = HpUtils.strToJson(request);
		List<User> list = udao.querySection("1");
		CommonResponse commonresponse = new CommonResponse();
		for(User user : list){
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("u_phone", user.getU_phone());
			jsonobject.put("u_name",user.getU_name());
			commonresponse.setResult(jsonobject);
		}
		commonresponse.setResCode("0");
		commonresponse.setResMsg("查询没有加入部门的好友");
		try{
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String resStr = commonresponse.getJsonStr();			
			response.getWriter().append(resStr).flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		
	}

}
