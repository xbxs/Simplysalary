package service;

import java.util.List;
import entity.Schedule;

public interface ScheduleDao {
		//查询所有排班
		public List<Schedule> queryAllSchedules();
		
		//根据查询某个时刻的所有排班
		public List<Schedule> querySchedulesByTime(String time);
		//查询某个人某个时间段分排班
		public List<Schedule> querySchedulesByTimeUphone(String hxid,String rtime);
		//根据id查询
		public Schedule queryScheduleById(int id);
		//添加排班
		public boolean addSchedule(Schedule schedule);
		//修改排班 
		public boolean updateSchedule(Schedule schedule);
		//删除排班
		public boolean deleteSchedule(int id);
}
