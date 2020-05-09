package service_imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import data.HibernateSessionFactory;
import entity.Salary;
import entity.User;
import entity.Vacate;
import service.VacateDao;

public class VacateDaoImp implements VacateDao {

	@Override
	public List<Vacate> queryAllVacates() {
		Transaction tx = null;
		List<Vacate> list =null;
		String sql = "";
		
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Vacate";
			Query query = session.createQuery(sql);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if(tx != null){
				tx = null;
			}
		}
	}



	

	@Override
	public boolean addVacate(Vacate vacate) {
		Transaction tx = null;
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(vacate);
			tx.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public boolean addVacates(List<Vacate> vacates) {
		for(Vacate vacate : vacates){
			addVacate(vacate);
		}
		if(vacates.size() > 0){
			return true;
		}
		
		return false;
	}



	@Override
	public boolean updateVacate(Vacate vacate) {
		Transaction tx = null;
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(vacate);
			tx.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public boolean deleteVacate(String time,String u_phone,String section) {
		Transaction tx = null;
		List<Vacate> salarys = null;
		
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			salarys =queryVacateByRtime(time,u_phone,section);
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
			if(tx != null){
				tx = null;
			}
		}
	}





	@Override
	public List<Vacate> queryVacateByTimeAndSec(String btime, String etime,
			String section) {
		Transaction tx = null;
		List<Vacate> list =null;
		String sql = "";
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Vacate where v_section = ? and v_rtime between =? and =?";
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
			if(tx != null){
				tx = null;
			}
		}
	}



	public List<Vacate> queryVacateByRtime(String rtime,String u_phone,String section){
		Transaction tx = null;
		List<Vacate> list =null;
		String sql = "";
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Vacate where u_phone = ? and v_section = ? and v_rtime =? and v_status =1";
			Query query = session.createQuery(sql);
			query.setParameter(0, u_phone);
			query.setParameter(1,section);
			query.setParameter(2, rtime);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if(tx != null){
				tx = null;
			}
		}
	}

	@Override
	public List<Vacate> queryVacateByTimeAndId(String btime, String etime,
			String u_phone, String section) {
		Transaction tx = null;
		List<Vacate> list =null;
		String sql = "";
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Vacate where u_phone = ? and v_section = ? and v_btime >=? and v_etime <=? and v_status =3";
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
			if(tx != null){
				tx = null;
			}
		}
	}





	@Override
	public List<Vacate> queryVacateByApply(String v_tuser) {
		Transaction tx = null;
		List<Vacate> list =null;
		String sql = "";
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Vacate where v_tuser = ?";
			Query query = session.createQuery(sql);
			query.setParameter(0, v_tuser);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if(tx != null){
				tx = null;
			}
		}
	}





	@Override
	public List<Vacate> queryVacateBySelfApply(String u_phone) {
		Transaction tx = null;
		List<Vacate> list =null;
		String sql = "";
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from Vacate where u_phone = ?";
			Query query = session.createQuery(sql);
			query.setParameter(0, u_phone);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if(tx != null){
				tx = null;
			}
		}
	}





	@Override
	public Vacate queryVacateById(int id) {
		Transaction tx = null;
		Vacate vacate = null;
		
		try{
			Session session  = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			vacate = (Vacate)session.get(Vacate.class,id);
			tx.commit();
			return vacate;
		}catch(Exception e){
			e.printStackTrace();
			tx.commit();
			return vacate;
		}finally{
			if(tx != null){
				tx = null;
			}
		}
	}

}
