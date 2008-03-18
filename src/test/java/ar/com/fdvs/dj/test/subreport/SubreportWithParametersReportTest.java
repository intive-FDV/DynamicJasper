/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.test.subreport;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.xml.JRXmlWriter;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.ReportExporter;

public class SubreportWithParametersReportTest extends BaseDjReportTest {

	private Connection con;

	public DynamicReport buildReport() throws Exception {


		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("Id", "id", Integer.class.getName(),30)
			.addColumn("Nombre", "nombre", String.class.getName(),30)
			.addColumn("Apellido", "apellido", String.class.getName(),50)
			.addColumn("Password", "password", String.class.getName(),50)
			.addGroups(1)
			.setTitle("Usuarios del sistema")			
//			.setQuery("select * from usuario where nombre = $P{nom}", DJConstants.QUERY_LANGUAGE_SQL)
			.setQuery("select * from usuario", DJConstants.QUERY_LANGUAGE_SQL)
			.setUseFullPageWidth(true);
		
		DynamicReport drLevel2 = createLevel2Subreport();

		SubreportParameter[] subreportParameters = new SubreportParameter[]{new SubreportParameter("idUsuario","id",Integer.class.getName(),DJConstants.SUBREPORT_PARAM_ORIGIN_FIELD)};
		drb.addSubreportInGroupFooter(1, drLevel2, new ClassicLayoutManager(),
				null, DJConstants.DATA_SOURCE_ORIGIN_USE_REPORT_CONNECTION, DJConstants.DATA_SOURCE_TYPE_SQL_CONNECTION, subreportParameters);

		DynamicReport dr = drb.build();
		
		params.put("nom", "Juan"); //Note that the query has a parameter, by putting in the map
//		params.put("con2", con); //Note that the query has a parameter, by putting in the map
		//an item with the proper key, it will be automatically registered as a parameter
		return dr;
	}

	private DynamicReport createLevel2Subreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
			.addColumn("Calle", "calle", String.class.getName(), 200)
			.addColumn("Numero", "numero", Integer.class.getName(), 50)
			.setQuery("select * from direccion where id_usuario = $P{idUsuario}", DJConstants.QUERY_LANGUAGE_SQL)
			.setUseFullPageWidth(false)		
			.addParameter("idUsuario", Integer.class.getName())
			.setTitle("Adresses for user id = \"+$P{idUsuario} +\"")
			.build();
		return dr;
	}	
	
	public static void main(String[] args) throws Exception {
		SubreportWithParametersReportTest test = new SubreportWithParametersReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
//			JasperDesignViewer.viewReportDesign(jr);
		
		String jrxml = JRXmlWriter.writeReport(test.jr, "UTF-8");
		System.out.println(jrxml);
	}
	
	public void testReport() throws Exception {
		try {
			con = creatMySQLConnection();
			
			dr = buildReport();

			
			jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), con,params );
			
			con.close();
			
			ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/"+this.getClass().getName()+".pdf");
			jr = DynamicJasperHelper.generateJasperReport(dr,  new ClassicLayoutManager(),params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}	
	
  public static Connection creatMySQLConnection() {
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return con;
}
		  

}
