package service_imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import data.HibernateSessionFactory;

import entity.Salary;
import entity.User;
import service.SalaryDao;

public class SalaryDaoImp implements SalaryDao {

	@Override
	public List<Salary> queryAllSalays() {
		Session session = null;
		Transaction tx = null;
		List<Salary> list =null;
		String sql = "";
		
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Salary";
			Query query = session.createQuery(sql);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if (session != null) {
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public List<Salary> querySalaryByTimeAndSec(String btime, String etime,
			String section) {
		Session session = null;
		Transaction tx = null;
		List<Salary> list =null;
		String sql = "";
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Salary where s_section = ? and s_rtime between =? and =?";
			Query query = session.createQuery(sql);
			query.setParameter(0, section);
			query.setParameter(1, btime);
			query.setParameter(2, etime);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if (session != null) {
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public List<Salary> querySalaryByTimeAndId(String btime, String etime,
			String u_phone,String section) {
		Session session = null;
		Transaction tx = null;
		List<Salary> list =null;
		String sql = "";
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Salary where u_phone = ? and s_section = ? and s_rtime >=? and s_rtime <=?";
			Query query = session.createQuery(sql);
			query.setParameter(0, u_phone);
			query.setParameter(1,section);
			query.setParameter(2, btime);
			query.setParameter(3, etime);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if (session != null) {
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public boolean addSalary(Salary salary) {
		Session session = null;
		Transaction tx = null;
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(salary);
			tx.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if (session != null) {
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public boolean addSalarys(List<Salary> salars) {
		for(Salary salary : salars){
			addSalary(salary);
		}
		if(salars.size() > 0){
			return true;
		}
		
		return false;		
	}

	@Override
	public boolean deleteSalray(String time, String u_phone,String section) {
		Session session = null;
		Transaction tx = null;
		List<Salary> salarys = null;
		
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			salarys =querySalaryByTimeAndId(time,time,u_phone,section);
			if(null !=salarys && salarys.size() > 0){
				session.delete(salarys.get(0));
				tx.commit();
				return true;
				
			}
			return false;
			
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if (session != null) {
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public boolean updateSalary(Salary salary) {
		Session session = null;
		Transaction tx = null;
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(salary);
			tx.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if (session != null) {
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

}
