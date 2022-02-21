package ar.com.fdvs.dj.test.web.webwork;

import ar.com.fdvs.dj.util.StreamUtils;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import junit.framework.TestCase;
import org.junit.Ignore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Ignore
public class WWResultTypeTest extends TestCase {

	public void testDynamicReport() throws Exception {
        ServletRunner sr = new ServletRunner( getClass().getResourceAsStream("/webwork/web.xml") );       // (1) use the web.xml file to define mappings
        ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application

        WebResponse r = client.getResponse( "http://localhost/generateReport.action" ); // (3) invoke the servlet w/o authorization
		String fname=r.getHeaderField("CONTENT-DISPOSITION");
		fname = fname.substring(fname.indexOf("=")+1);

		FileOutputStream fos = new FileOutputStream( new File(System.getProperty("user.dir")+ "/target/" + fname));
        InputStream is = r.getInputStream();
        StreamUtils.copy(is, fos);

        is.close();
        fos.close();
	}

	public void testDynamicReportWithTemplate() throws Exception {
		ServletRunner sr = new ServletRunner( getClass().getResourceAsStream("/webwork/web.xml") );       // (1) use the web.xml file to define mappings
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
