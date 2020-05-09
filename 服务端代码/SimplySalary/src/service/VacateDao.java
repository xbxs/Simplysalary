package service;
import java.util.List;

import entity.Salary;
import entity.Vacate;


public interface VacateDao {
	//查询所有请假
	public List<Vacate> queryAllVacates();
			
	//根据时间和部门开查询某一个时刻的部门请假
	public List<Vacate> queryVacateByTimeAndSec(String btime,String etime,String section);
		
	//查询某人某一时间段在某一部门的请假
	public List<Vacate> queryVacateByTimeAndId(String btime,String etime,String u_phone,String section);
	//查询商家收到的申请请假
	public List<Vacate> queryVacateByApply(String v_tuser);
	//查询自己发出去的申请请假
	public List<Vacate> queryVacateBySelfApply(String u_phone);
	//根据id查询请假
	public Vacate queryVacateById(int id);
	//根据记录的时间来删
	public List<Vacate> queryVacateByRtime(String rtime,String u_phone,String section);
	//增加某人的请假
	public boolean addVacate(Vacate vacate);
	
	//添加一个部门的请假
	public boolean addVacates(List<Vacate> vacates);
	
	//删除某个人某时刻的请假
	public boolean deleteVacate(String time,String u_phone,String section);
	
	//更新某个人某时刻的请假
	public boolean updateVacate(Vacate vacate);	
}
