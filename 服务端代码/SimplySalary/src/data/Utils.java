package data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Utils {
	private SessionFactory sessionFactory;
	private Utils(){}
	
	private static Utils instance = new Utils();
	public static Utils getInstance(){
		return instance;
	}
	
	
	public SessionFactory getSessionFactory(){
		
		if(sessionFactory == null){
			
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
			.applySettings(configuration.getProperties()).buildServiceRegistry();
			
			
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}
	
	
	
	public Session getSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	
}
