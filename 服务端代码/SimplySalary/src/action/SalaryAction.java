package action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.SalaryDao;
import service.UserDao;
import service_imp.SalaryDaoImp;
import service_imp.UserDaoImp;
import utils.CommonResponse;
import utils.HpUtils;

import com.opensymphony.xwork2.ModelDriven;

import entity.Salary;
import entity.User;

public class SalaryAction extends BaseAction implements ModelDriven<Salary>{
	private static final long serialVersionUID = 1L;
	Salary salary = new Salary();
	
	@Override
	public Salary getModel() {
		// TODO Auto-generated method stub
		return salary;
	}
	
	//增加工资
	public void addSalary(){
		try {
		JSONObject object = HpUtils.strToJson(request);
		JSONObject requestParam = object.getJSONObject("requestParam");
		String u_phone = requestParam.getString("u_phone");
		String s_rtime = requestParam.getString("s_rtime");
		String s_term = requestParam.getString("s_term");
		String s_shift = requestParam.getString("s_shift");
		String s_section = requestParam.getString("s_section");
		String s_wage = requestParam.getString("s_wage");
		
		Salary salary = new Salary(0,u_phone,s_rtime,s_term,s_shift,s_section,s_wage);
		SalaryDao salarydao = new SalaryDaoImp();
		CommonResponse commonresponse = new CommonResponse();
		if(salarydao.addSalary(salary)){
			commonresponse.setResCode("0");
			commonresponse.setResMsg("记录成功");
		}else{
			commonresponse.setResCode("1");
			commonresponse.setResMsg("数据库忙，请稍后再试");
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
	//查询工资
	public void querySalaryByTimeAndId(){
		try{
		JSONObject object = HpUtils.strToJson(request);
		String requestCode = object.getString("requestCode");
		JSONObject requestParam = object.getJSONObject("requestParam");
		String btime = requestParam.getString("btime");
		String etime = requestParam.getString("etime");
		String section = requestParam.getString("s_section");
		JSONArray jsonarray = object.getJSONArray("list");
		 
		SalaryDao salarydao = new SalaryDaoImp();
		CommonResponse commonresponse = new CommonResponse();
		if(requestCode.equals("section_salary")){
		//得到总工资
		for(int i = 0;i< jsonarray.size();i++){
			List<Salary> list = salarydao.querySalaryByTimeAndId(btime, etime, jsonarray.getString(i), section);
			double allterm = 0;
			for(int j=0;j < list.size();j++){
				allterm += (Double.parseDouble(list.get(j).getS_term()) * Double.parseDouble(list.get(j).getS_wage()));
			}
			UserDao userdao = new UserDaoImp();
			User user = userdao.queryUsersByUphone(jsonarray.getString(i));
			JSONObject resobject = new JSONObject();
			resobject.put("u_name",user.getU_name());
			allterm += user.getU_bas();
			resobject.put("phonenumber", jsonarray.getString(i));
			resobject.put("allterm", allterm+"");
			System.out.println("allterm:"+allterm);
			//resarray.add(i, allterm);
			commonresponse.setResult(resobject);
		}
		
		commonresponse.setResCode("0");
		commonresponse.setResMsg("查询总工资");
		//查询个人工资
		}else if(requestCode.equals("person_salary")){
			String u_phone = requestParam.getString("phonenumber");
			UserDao userdao = new UserDaoImp();
			User user = userdao.queryUsersByUphone(u_phone);
			if("1".equals(section)){
				section = user.getU_section();
			}
			List<Salary> list = salarydao.querySalaryByTimeAndId(btime, etime, u_phone, section);
			double allterm = 0;
			double term = 0;
			for(int j=0;j < list.size();j++){
				term += Double.parseDouble(list.get(j).getS_term());
				allterm += (Double.parseDouble(list.get(j).getS_term()) * Double.parseDouble(list.get(j).getS_wage()));
			}
			System.out.println("allmoney:"+allterm+"  term:"+term);
			commonresponse.setResCode("0");
			commonresponse.setResMsg("查询个人工资");
			commonresponse.setResult("u_bas", user.getU_bas()+"");
			commonresponse.setResult("u_wage", user.getU_wage()+"");
			commonresponse.setResult("u_name",user.getU_name());
			commonresponse.setResult("term",term+"");
			commonresponse.setResult("allterm",allterm+"");
			for(Salary salary : list){
				JSONObject jsonobject = new JSONObject();
				jsonobject.put("id",salary.getS_id()+"");
				jsonobject.put("s_rtime",salary.getS_rtime());
				jsonobject.put("s_term", salary.getS_term()+"");
				jsonobject.put("s_shift",salary.getS_shift());
				jsonobject.put("s_wage",salary.getS_wage());
				commonresponse.setResult(jsonobject);
			}
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
	//删除工资
	public void deleteSalary(){
		try{
			JSONObject object = HpUtils.strToJson(request);
			JSONObject requestParam = object.getJSONObject("requestParam");
			String u_phone = requestParam.getString("u_phone");
			String s_section = requestParam.getString("s_section");
			String s_rtime = requestParam.getString("s_rtime");
			SalaryDao salarydao = new SalaryDaoImp();
			CommonResponse commonresponse = new CommonResponse();
			if(salarydao.deleteSalray(s_rtime, u_phone, s_section)){
				commonresponse.setResCode("0");
				commonresponse.setResMsg("删除成功");
			}else {
				commonresponse.setResCode("1");
				commonresponse.setResMsg("数据库繁忙，请稍后再试");
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String resStr = commonresponse.getJsonStr();			
			response.getWriter().append(resStr).flush();
		}catch(Exception e){
			e.printStackTrace();			
		}	
	}
	

}
