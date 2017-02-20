package ar.com.fdvs.dj.test.web.struts2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.test.FastReportTest;
import ar.com.fdvs.dj.test.TemplateStyleReportTest;

import com.opensymphony.xwork.ActionSupport;

public class GenerateReportAction extends ActionSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private DynamicReport dynamicReport;

	private Collection dataSource;

    public String getLmanager() {
        String lmanager = "list";
        return lmanager;
	}

	private Map params = new HashMap();

	public Map getParams() {
		return params;
	}

	public String getLeftHeader() {
        String leftHeader = "DJ MAMANA 2008";
        return leftHeader;
	}

	public DynamicReport getDynamicReport() {
		return dynamicReport;
	}

	public Collection getDataSource() {
		return dataSource;
	}

	public String execute() throws Exception {

		FastReportTest frt = new FastReportTest();

		this.dynamicReport = frt.buildReport();
		this.params = frt.getParams();
		this.dataSource = frt.getDummyCollectionSorted(dynamicReport.getColumns());
		return SUCCESS;
	}

	public String doReportWithTemplate() throws Exception {

		TemplateStyleReportTest frt = new TemplateStyleReportTest();

		this.dynamicReport = frt.buildReport();
		//this.params = frt.getParams();
		this.dataSource = frt.getDummyCollectionSorted(dynamicReport.getColumns());

		params.put("leftHeader2", "This value comes from GenerateReportAction.params.leftHeader");

		return SUCCESS;
	}




}