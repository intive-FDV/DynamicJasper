package ar.com.fdvs.dj.test.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	   private static SessionFactory factory;

	    public static synchronized Session getSession() {
	        if (factory == null) {
	            factory = new Configuration().configure().buildSessionFactory();
	        }
	        return factory.openSession();
	    }

	    public static void setSessionFactory(SessionFactory factory) {
	        HibernateUtil.factory = factory;
	    }

		public static SessionFactory getFactory() {
			return factory;
		}

}
