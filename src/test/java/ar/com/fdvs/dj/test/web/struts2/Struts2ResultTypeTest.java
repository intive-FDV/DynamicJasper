/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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