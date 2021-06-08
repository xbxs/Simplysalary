package action;

import java.util.ArrayList;
import java.util.List;

//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.UserDao;
import service.VacateDao;
import service_imp.UserDaoImp;
import service_imp.VacateDaoImp;
import utils.CommonResponse;
import utils.HpUtils;
import entity.User;
import entity.Vacate;

public class VacateAction extends BaseAction {

	// 查询请假
	public void queryVacateByTimeAndId() {
		try {
			JSONObject object = HpUtils.strToJson(request);
			String requestCode = object.getString("requestCode");
			JSONObject requestParam = object.getJSONObject("requestParam");
			String btime = requestParam.getString("btime");
			String etime = requestParam.getString("etime");
			String section = requestParam.getString("s_section");
			JSONArray jsonarray = object.getJSONArray("list");

			VacateDao vacatedao = new VacateDaoImp();
			CommonResponse commonresponse = new CommonResponse();
			if (requestCode.equals("section_Vacate")) {
				// 得到总请假
				for (int i = 0; i < jsonarray.size(); i++) {
					List<Vacate> list = vacatedao.queryVacateByTimeAndId(btime,
							etime, jsonarray.getString(i), section);
					double allterm = 0;
					for (int j = 0; j < list.size(); j++) {
						allterm += (Double.parseDouble(list.get(j).getV_term()));
					}
					UserDao userdao = new UserDaoImp();
					User user = userdao.queryUsersByUphone(jsonarray
							.getString(i));
					JSONObject resobject = new JSONObject();
					resobject.put("u_name", user.getU_name());
					resobject.put("phonenumber", jsonarray.getString(i));
					resobject.put("allterm", allterm + "");
					System.out.println("allterm:" + allterm);
					// resarray.add(i, allterm);
					commonresponse.setResult(resobject);
				}
				commonresponse.setResCode("0");
				commonresponse.setResMsg("查询总请假");
				// 查询个人请假
			} else if (requestCode.equals("person_Vacate")) {
				String u_phone = requestParam.getString("phonenumber");
				UserDao userdao = new UserDaoImp();

				User user = userdao.queryUsersByUphone(u_phone);

				if ("1".equals(section)) {
					section = user.getU_section();
				}

				System.out
						.println("section:-----------------------------------------"
								+ section + " user " + user.toString());
				List<Vacate> list = vacatedao.queryVacateByTimeAndId(btime,
						etime, u_phone, section);
				commonresponse.setResCode("0");
				commonresponse.setResMsg("查询个人请假");
				commonresponse.setResult("u_name", user.getU_name());
				double allterm = 0;
				for (Vacate vacate : list) {
					JSONObject jsonobject = new JSONObject();
					jsonobject.put("id", vacate.getV_id() + "");
					jsonobject.put("s_rtime", vacate.getV_rtime());
					jsonobject.put("s_term", vacate.getV_term() + "");
					jsonobject.put("s_shift", vacate.getV_type());
					jsonobject.put("s_wage", "-1");
					allterm += (Double.parseDouble(vacate.getV_term()));
					commonresponse.setResult(jsonobject);
				}
				commonresponse.setResult("allterm", allterm + "");

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

	// 删除请假信息
	public void deleteVacate() {
		try {
			JSONObject object = HpUtils.strToJson(request);
			JSONObject requestParam = object.getJSONObject("requestParam");
			String u_phone = requestParam.getString("u_phone");
			String s_section = requestParam.getString("s_section");
			String s_rtime = requestParam.getString("s_rtime");
			VacateDao vacatedao = new VacateDaoImp();
			CommonResponse commonresponse = new CommonResponse();
			if (vacatedao.deleteVacate(s_rtime, u_phone, s_section)) {
				commonresponse.setResCode("0");
				commonresponse.setResMsg("删除成功");
			} else {
				commonresponse.setResCode("1");
				commonresponse.setResMsg("数据库繁忙，请稍后再试");
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String resStr = commonresponse.getJsonStr();
			response.getWriter().append(resStr).flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入请假
	public void addVacate() {
		try {
			JSONObject object = HpUtils.strToJson(request);
			JSONObject requestParam = object.getJSONObject("requestParam");
			String u_phone = requestParam.getString("u_phone");
			String v_btime = requestParam.getString("v_btime");
			String v_term = requestParam.getString("v_term");
			String v_shift = requestParam.getString("v_shift");
			String v_reason = requestParam.getString("v_reason");
			String v_status = requestParam.getString("v_status");
			String v_etime = requestParam.getString("v_etime");
			String v_type = requestParam.getString("v_type");
			String v_rtime = requestParam.getString("v_rtime");
			UserDao userdao = new UserDaoImp();
			User user = userdao.queryUsersByUphone(u_phone);
			VacateDao vacatedao = new VacateDaoImp();
			CommonResponse commonresponse = new CommonResponse();
			if ("0".equals(user.getU_ower())) {
				commonresponse.setResCode("1");
				commonresponse.setResMsg("请先添加leader");
			} else {

				Vacate vacate = new Vacate(1, u_phone, v_rtime, v_term,
						v_shift, user.getU_section(), v_reason,
						user.getU_ower(), v_status, v_btime, v_etime, v_type);
				if (vacatedao.addVacate(vacate)) {
					commonresponse.setResCode("0");
					commonresponse.setResMsg("请假记录成功");
				} else {
					commonresponse.setResCode("1");
					commonresponse.setResMsg("数据库忙，请稍后再试");
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

	// 根据id查询请假
	public void queryVacateById() {
		VacateDao vacatedao = new VacateDaoImp();
		JSONObject object = HpUtils.strToJson(request);
		JSONObject requestParam = object.getJSONObject("requestParam");
		String id = requestParam.getString("v_id");
		String v_status = requestParam.getString("v_status");
		Vacate vacate = vacatedao.queryVacateById(Integer.parseInt(id));
		System.out.println(vacate.toString());
		vacate.setV_status(v_status);
		System.out.println(vacate.toString());
		updateVacate(vacate);

	}

	// 查询请假申请列表
	public void queryVacateByApply() {
		try {
			JSONObject object = HpUtils.strToJson(request);
			JSONObject requestParam = object.getJSONObject("requestParam");
			String v_tuser = requestParam.getString("v_tuser");
			String flag = requestParam.getString("flag");
			VacateDao vacatedao = new VacateDaoImp();

			List<Vacate> list = new ArrayList<>();
			if ("0".equals(flag)) {
				list = vacatedao.queryVacateBySelfApply(v_tuser);
			} else {
				list = vacatedao.queryVacateByApply(v_tuser);
			}
			
			CommonResponse commonresponse = new CommonResponse();
			commonresponse.setResCode("0");
			commonresponse.setResMsg("查询申请假期列表");
			UserDao userdao = new UserDaoImp();
			for (Vacate vacate : list) {
				JSONObject jsonobject = new JSONObject();
				jsonobject.put("v_id", vacate.getV_id() + "");
				jsonobject.put("u_phone", vacate.getU_phone());
				User user = userdao.queryUsersByUphone(vacate.getU_phone());
				jsonobject.put("u_name", user.getU_name());
				jsonobject.put("v_rtime", vacate.getV_rtime());
				jsonobject.put("v_term", vacate.getV_term() + "");
				jsonobject.put("v_type", vacate.getV_type());
				jsonobject.put("v_btime", vacate.getV_btime());
				jsonobject.put("v_etime", vacate.getV_etime());
				jsonobject.put("v_status", vacate.getV_status());
				commonresponse.setResult(jsonobject);
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String resStr = commonresponse.getJsonStr();

			response.getWriter().append(resStr).flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新请假状态
	public void updateVacate(Vacate vacate) {
		try {
			VacateDao vacatedao = new VacateDaoImp();

			// Vacate vacate = new
			// Vacate(1,u_phone,v_rtime,v_term,v_shift,v_section,v_reason,v_tuser,v_status,v_btime,v_etime,v_type);
			CommonResponse commonresponse = new CommonResponse();
			if (vacatedao.updateVacate(vacate)) {
				commonresponse.setResCode("0");
				commonresponse.setResMsg("请假记录更新成功");
			} else {
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

}
