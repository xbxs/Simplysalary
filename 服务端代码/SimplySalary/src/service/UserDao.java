package service;

import java.util.List;
import entity.User;

public interface UserDao {
		//用户登录
		public String userLogin(User u);
		//查询所有用户资料
		public List<User> queryAllUsers();
		
		//根据环信编号查询用户资料
		public User queryUsersByUphone(String hxid);
		
		//查询用户section状态的
		public List<User> querySection(String section);
		//添加用户资料
		public boolean addUser(User user);
		
		//修改用户资料 
		public boolean updateUser(User user);
		
		//删除用户资料
		public boolean deleteUser(String u_phone);
		
		//查询同一部门的职员
		public List<User> querySectionUser(String s_id);
}
