package ar.com.fdvs.dj.test.web.struts2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.lf5.util.StreamUtils;

import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class Struts2ResultTypeTest extends TestCase {
	
    public Struts2ResultTypeTest(String string) {
		super(string);
	}




	public static Test suite() {

        TestSuite suite = new TestSuite();
  
//        suite.addTest(new Struts2ResultTypeTest("testDynamicReport"));
//        suite.addTest(new Struts2ResultTypeTest("testDynamicReportWithTemplate"));


        return suite;
    }

	
	

	public void testDynamicReport() throws Exception {
        ServletRunner sr = new ServletRunner( getClass().getResourceAsStream("/struts2/web.xml") );       // (1) use the web.xml file to define mappings
        ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application

        WebResponse r = client.getResponse( "http://localhost/example/generateReport.action" ); // (3) invoke the servlet w/o authorization
		String fname=r.getHeaderField("CONTENT-DISPOSITION");
		fname = fname.substring(fname.indexOf("=")+1);

		FileOutputStream fos = new FileOutputStream( new File(System.getProperty("user.dir")+ "/target/" + fname));
        InputStream is = r.getInputStream();
        StreamUtils.copy(is, fos);

        is.close();
        fos.close();
	}

	public void testDynamicReportWithTemplate() throws Exception {
		ServletRunner sr = new ServletRunner( getClass().getResourceAsStream("/struts2/web.xml") );       // (1) use the web.xml file to define mappings
		ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application

		WebResponse r = client.getResponse( "http://localhost/generateReportTemplate.action" ); // (3) invoke the servlet w/o authorization
		String fname=r.getHeaderField("CONTENT-DISPOSITION");
		fname = fname.substring(fname.indexOf("=")+1);

		FileOutputStream fos = new FileOutputStream( new File(System.getProperty("user.dir")+ "/target/" + fname));
		InputStream is = r.getInputStream();
		StreamUtils.copy(is, fos);

		is.close();
		fos.close();
	}

}