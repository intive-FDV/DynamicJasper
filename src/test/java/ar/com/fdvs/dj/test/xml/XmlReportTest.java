package ar.com.fdvs.dj.test.xml;

import java.util.Date;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import net.sf.jasperreports.view.JasperViewer;

import org.w3c.dom.Document;

import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.test.BaseDjReportTest;

public class XmlReportTest extends BaseDjReportTest {
	
	public DynamicReport buildReport() throws Exception  {

	       FastReportBuilder drb = new FastReportBuilder();
	       drb.addColumn("Cuenta", "CODCTA",String.class.getName(),30,false,"",null,"CODCTA");
	       drb.addColumn("S/Cta.", "AUXCNT",String.class.getName(),30,false,"",null,"AUXCNT");
	       drb.addColumn("Titulo", "DESIND",String.class.getName(),50,false,"",null,"DESIND");
	       drb.addColumn("Tipo", "ACTPAS",String.class.getName(),50,false,"",null,"ACTPAS");
	       drb.setTitle("Plan Contable");
	       drb.setSubtitle("This report was generated at " + new Date());
	       drb.addGlobalFooterVariable(1, DJCalculation.COUNT, null);
	       drb.setUseFullPageWidth(true);
//	       drb.setQuery("/data/linea", DJConstants.QUERY_LANGUAGE_XPATH);
	       DynamicReport dr = drb.build();
	       return dr;
	}
	
	protected JRDataSource getDataSource() {
		try {
			Document document;
			document = JRXmlUtils.parse(JRLoader.getLocationInputStream("sample-data.xml"));
			JRXmlDataSource ds = new JRXmlDataSource(document, "/data/linea");
//			params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT,document);
			return ds;
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}	

	public static void main(String[] args) throws Exception {
		XmlReportTest test = new XmlReportTest();
	    test.testReport();
		JasperViewer.viewReport(test.jp);
	}	

}
