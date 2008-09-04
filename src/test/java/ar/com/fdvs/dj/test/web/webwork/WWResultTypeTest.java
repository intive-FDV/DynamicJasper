package ar.com.fdvs.dj.test.web.webwork;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.log4j.lf5.util.StreamUtils;

import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class WWResultTypeTest extends TestCase {
	
	public void testSetup() throws Exception {
        ServletRunner sr = new ServletRunner( getClass().getResourceAsStream("/webwork/web.xml") );       // (1) use the web.xml file to define mappings
        ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application

        WebResponse r = client.getResponse( "http://localhost/generateReport.action" ); // (3) invoke the servlet w/o authorization

        FileOutputStream fos = new FileOutputStream( new File(System.getProperty("user.dir")+ "/target/" + this.getClass().getName() + ".pdf"));
        InputStream is = r.getInputStream();
        StreamUtils.copy(is, fos);
        
        is.close();
        fos.close();
        

		
	}

}
