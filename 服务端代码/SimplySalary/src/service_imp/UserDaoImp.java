package service_imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import data.HibernateSessionFactory;
import entity.User;
import service.UserDao;

public class UserDaoImp implements UserDao {

	public String userLogin(User u) {
		Transaction tx = null;
		String sql = "";
		Session session = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().openSession();
			sql = "from User user where user.u_phone = ? and user.u_pw =?";
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter(0, u.getU_phone());
			query.setParameter(1, u.getU_pw());
			List list = query.list();
			tx.commit();
			if (list.size() > 0) {
				System.out.println("" + list.get(0).toString());
				return "100";
			} else {
				return "200";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "300";
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null) {
				tx = null;
			}
		}
	}

	@Override
	public List<User> queryAllUsers() {
		Transaction tx = null;
		List<User> list = null;
		String sql = "";
		Session session = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from User";
			Query query = session.createQuery(sql);
			list = query.list();
			tx.commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			return list;
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null) {
				tx = null;
			}
		}
	}

	@Override
	public User queryUsersByUphone(String hxid) {
		Transaction tx = null;
		User user = null;
		Session session = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			user = (User) session.get(User.class, hxid);
			tx.commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			return user;
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null) {
				tx = null;
			}
		}

	}

	public List<User> querySection(String section) {
		Transaction tx = null;
		List<User> list = null;
		String sql = "";
		Session session = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from User where u_section =?";
			Query query = session.createQuery(sql);
			query.setParameter(0, section);
			list = query.list();
			tx.commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			return list;
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null) {
				tx = null;
			}
		}
	}

	@Override
	public boolean addUser(User user) {
		Transaction tx = null;
		Session session = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null) {
				tx = null;
			}
		}

	}

	@Override
	public boolean updateUser(User user) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(user);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null) {
				tx = null;
			}
		}

	}

	@Override
	public boolean deleteUser(String u_phone) {
		Transaction tx = null;
		User user = null;
		Session session = null;

		try {
			session = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			user = queryUsersByUphone(u_phone);
			if (null != user) {
				session.delete(user);
				tx.commit();
				return true;

			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null) {
				tx = null;
			}
		}
	}

	@Override
	public List<User> querySectionUser(String s_id, String u_ower) {
		Session session = null;
		Transaction tx = null;
		List<User> list = null;
		String sql = "";

		try {
			session = HibernateSessionFactory.getSessionFactory().openSession();
			tx = session.beginTransaction();
			sql = "from User where u_section = ? and u_ower = ?";
			Query query = session.createQuery(sql);
			query.setParameter(0, s_id);
			query.setParameter(1, u_ower);
			list = query.list();
			tx.commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			return list;
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null) {
				tx = null;
			}
		}
	}

}
