package action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.ScheduleDao;
import service.UserDao;
import service_imp.ScheduleDaoImp;
import service_imp.UserDaoImp;
import utils.CommonResponse;
import utils.HpUtils;
import entity.Schedule;

public class ScheduleAction extends BaseAction {
	//查询某一天的全部排班
	public void querySchedulesByTime(){
	 try{
		JSONObject object = HpUtils.strToJson(request);
		JSONObject requestParam = object.getJSONObject("requestParam");
		String s_time = requestParam.getString("s_time");
		ScheduleDao scheduledao = new ScheduleDaoImp();
		List<Schedule> list = scheduledao.querySchedulesByTime(s_time);
		CommonResponse commonresponse = new CommonResponse();
		UserDao userdao = new UserDaoImp();
		for(Schedule schedule : list){
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("s_id",schedule.getS_id()+"");
			jsonobject.put("u_phone",schedule.getU_phone());
			jsonobject.put("u_name", userdao.queryUsersByUphone(schedule.getU_phone()).getU_name());
			jsonobject.put("s_time",schedule.getS_time());
			jsonobject.put("s_shift",schedule.getS_shift());
			jsonobject.put("s_term", schedule.getS_term());  
			commonresponse.setResult(jsonobject);
		}
		commonresponse.setResCode("0");
		commonresponse.setResMsg("查询某一天的排班");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String resStr = commonresponse.getJsonStr();			
		response.getWriter().append(resStr).flush();
	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}	
		
	}
	//员工查询排班
	public void queryScheduleByUphoneAndTime(){
		 try{
				JSONObject object = HpUtils.strToJson(request);
				JSONObject requestParam = object.getJSONObject("requestParam");
				String s_time = requestParam.getString("s_time");
				String u_phone = requestParam.getString("u_phone");
				ScheduleDao scheduledao = new ScheduleDaoImp();
				List<Schedule> list = scheduledao.querySchedulesByTimeUphone(u_phone, s_time);
				CommonResponse commonresponse = new CommonResponse();
				for(Schedule schedule : list){
					JSONObject jsonobject = new JSONObject();
					jsonobject.put("s_id",schedule.getS_id()+"");
					jsonobject.put("u_phone",schedule.getU_phone());
					jsonobject.put("s_time",schedule.getS_time());
					jsonobject.put("s_shift",schedule.getS_shift());
					jsonobject.put("s_term", schedule.getS_term());  
					commonresponse.setResult(jsonobject);
				}
				commonresponse.setResCode("0");
				commonresponse.setResMsg("查询某一天的排班");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				String resStr = commonresponse.getJsonStr();			
				response.getWriter().append(resStr).flush();
			} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}	
	}
	
	//添加排班
	public void addSchedule(){
		try{
		JSONObject object = HpUtils.strToJson(request);
		JSONArray jsonarray = object.getJSONArray("list");
		ScheduleDao scheduledao = new ScheduleDaoImp();
		boolean isSuccess = false;
		for(int i=0;i<jsonarray.size();i++){
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			String u_phone = jsonobject.getString("u_phone");
			String s_time = jsonobject.getString("s_time");
			String s_term = jsonobject.getString("s_term");
			String s_shift = jsonobject.getString("s_shift");
			Schedule schedule = new Schedule(1,u_phone,s_time,s_shift,s_term);
			isSuccess = scheduledao.addSchedule(schedule);
		}
		CommonResponse commonresponse = new CommonResponse();
		if(isSuccess){
			commonresponse.setResCode("0");
			commonresponse.setResMsg("增加排班成功");
		}else{
			commonresponse.setResCode("1");
			commonresponse.setResMsg("增加排班失败");
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
	
	public void deleteSchedule(){
		try{
			JSONObject object = HpUtils.strToJson(request);
			JSONObject requestParam = object.getJSONObject("requestParam");
			String id = requestParam.getString("id");
			ScheduleDao scheduledao = new ScheduleDaoImp();
			boolean isSuccess = scheduledao.deleteSchedule(Integer.parseInt(id));
			CommonResponse commonresponse = new CommonResponse();
			if(isSuccess){
				commonresponse.setResCode("0");
				commonresponse.setResMsg("删除排班成功");
			}else{
				commonresponse.setResCode("1");
				commonresponse.setResMsg("删除排班失败");
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
