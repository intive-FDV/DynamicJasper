package ar.com.fdvs.dj.test;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import net.sf.jasperreports.view.JasperViewer;

import org.hibernate.Session;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.test.domain.db.Customer;
import ar.com.fdvs.dj.test.hibernate.HibernateUtil;
import ar.com.fdvs.dj.test.hibernate.TestSchema;

public class HQLReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {
		Style groupStyle = new StyleBuilder(false).setFont(new Font(18, Font._FONT_VERDANA, true)).build();
		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		drb
			.addColumn("City", "city", String.class.getName(),50,groupStyle)
			.addColumn("Last Name", "lastName", String.class.getName(),50)
			.addColumn("First Name", "firstName", String.class.getName(),30)
			.addColumn("Id", "id", Long.class.getName(),30)
			.addColumn("Street", "street", String.class.getName(),50)
			.addGroups(1, GroupLayout.VALUE_IN_HEADER)
			.setTitle("Customers")
			.setSubtitle("Order by city and last name")
			.setQuery("from Customer order by city, lastName", DJConstants.QUERY_LANGUAGE_HQL)
			.setTemplateFile("templates/TemplateReportTest.jrxml")
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

		Session hsession = HibernateUtil.getSession();
		params.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION, hsession);


		return dr;
	}
	
	protected JRDataSource getDataSource() {
		return null; //we use Hibernate Session
	}	
	
	public static void main(String[] args) throws Exception {
		TestSchema.buildConfiguration();
		HQLReportTest test = new HQLReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
		//JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(test.dr, new ClassicLayoutManager(),test.params));
	}
	
	public void testHibernate() {
		TestSchema.buildConfiguration();
		Session s = HibernateUtil.getSession();
		List l = s.createQuery("from Customer order by lastName").list();
		for (Object aL : l) {
			Customer cust = (Customer) aL;
			log.debug(cust.getFirstName() + ", " + cust.getLastName());
		}
	}
	

}
