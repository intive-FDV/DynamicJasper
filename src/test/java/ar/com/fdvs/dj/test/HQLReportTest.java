package ar.com.fdvs.dj.test;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.test.domain.db.Customer;
import ar.com.fdvs.dj.test.hibernate.HibernateUtil;
import ar.com.fdvs.dj.test.hibernate.TestSchema;

public class HQLReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		TestSchema.buildConfiguration();
		Session s = HibernateUtil.getSession();
		List l = s.createQuery("from Customer order by lastName").list();
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			Customer cust = (Customer) iterator.next();
			log.debug(cust.getFirstName() + ", " + cust.getLastName());
		}
	}

}
