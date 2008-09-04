package ar.com.fdvs.dj.test.web.webwork;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Parameter;
import ar.com.fdvs.dj.test.FastReportTest;

import com.opensymphony.xwork.ActionSupport;

public class GenerateReportAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DynamicReport dynamicReport;
	
	private Collection dataSource;
	
	private String reportTitle = "This is the report Title";
	
//	private Map params;
//
//	public Map getParams() {
//		return params;
//	}

	public DynamicReport getDynamicReport() {
		return dynamicReport;
	}

	public Collection getDataSource() {
		return dataSource;
	}

	public String execute() throws Exception {
		
		FastReportTest frt = new FastReportTest();
		
		this.dynamicReport = frt.buildReport();
		//this.params = frt.getParams();
		this.dataSource = frt.getDummyCollectionSorted(dynamicReport.getColumns());
		return SUCCESS;
	}

	
	

}
