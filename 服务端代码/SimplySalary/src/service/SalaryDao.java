package service;
import java.util.List;
import entity.Salary;

public interface SalaryDao {
	//查询所有工资
	public List<Salary> queryAllSalays();
			
	//根据时间和部门开查询某一个时刻的部门工资
	public List<Salary> querySalaryByTimeAndSec(String btime,String etime,String section);
	
	//查询某人某一时间段在某一部门的工资
	public List<Salary> querySalaryByTimeAndId(String btime,String etime,String u_phone,String section);
	
	//增加某人的工资
	public boolean addSalary(Salary salary);
	
	//添加一个部门的工资
	public boolean addSalarys(List<Salary> salars);
	
	//删除某个人某时刻在某部门的工资
	public boolean deleteSalray(String time,String u_phone,String section);
	
	//更新某个人某时刻的工资
	public boolean updateSalary(Salary salary);	
}
