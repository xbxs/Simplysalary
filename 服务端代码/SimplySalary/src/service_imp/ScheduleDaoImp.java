package service_imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import data.HibernateSessionFactory;

import entity.Schedule;
import entity.User;
import entity.Vacate;
import service.ScheduleDao;

public class ScheduleDaoImp implements ScheduleDao {

	@Override
	public List<Schedule> queryAllSchedules() {
		Session session = null;
		Transaction tx = null;
		List<Schedule> list =null;
		String sql = "";
		
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Schedule";
			Query query = session.createQuery(sql);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if(session != null){
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public List<Schedule> querySchedulesByTime(String time) {
		Transaction tx = null;
		List<Schedule> list =null;
		String sql = "";
		Session session = null;
	try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
		
			sql = "from Schedule where  s_time= ?";
			Query query = session.createQuery(sql);
			query.setParameter(0, time);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if(session != null){
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public List<Schedule> querySchedulesByTimeUphone(String hxid, String rtime) {
		Session session = null;
		Transaction tx = null;
		List<Schedule> list =null;
		String sql = "";
		
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Schedule where  s_time= ? and u_phone = ?";
			Query query = session.createQuery(sql);
			query.setParameter(0, rtime);
			query.setParameter(1, hxid);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if(session != null){
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public boolean addSchedule(Schedule schedule) {
		Session session = null;
		Transaction tx = null;
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(schedule);
			tx.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if(session != null){
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public boolean updateSchedule(Schedule schedule) {
		Session session = null;
		Transaction tx = null;
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(schedule);
			tx.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if(session != null){
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public boolean deleteSchedule(int id){
		Session session = null;
		Transaction tx = null;
		Schedule schedule;
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			schedule = queryScheduleById(id);
			if(schedule != null){
			session.delete(schedule);
			tx.commit();	
			return true;
			}
			
			return false;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if(session != null){
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}
	public Schedule queryScheduleById(int id){
		Session session = null;
		Transaction tx = null;
		Schedule schedule = null;
		
		try{
			session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			schedule = (Schedule)session.get(Schedule.class,id);
			tx.commit();
			return schedule;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return schedule;
		}finally{
			if(session != null){
				session.close();
			}
			if(tx != null){
				tx = null;
			}
		}
	}

}
