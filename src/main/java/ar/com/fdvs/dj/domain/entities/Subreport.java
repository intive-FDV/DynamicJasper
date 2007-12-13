package ar.com.fdvs.dj.domain.entities;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import net.sf.jasperreports.engine.JasperReport;

public class Subreport {
	
	private JasperReport report;
	
	/**
	 * The full path or URI in the class path of the compiled .jasper report file
	 */
	private String path;
	
	/**
	 * The DynamicReport to use
	 */
	private DynamicReport dynamicReport = null;
	
	/**
	 * Only needed when the subreport is made from a DynamicReport
	 */
	private LayoutManager layoutManager = null;
	
	
	private DJDataSource datasource;
//	/**
//	 * This expression must point to a JRDataSource object
//	 */
//	private String dataSourceExpression;
//	
//	/**
//	 * Tells form where to look up the data source expression
//	 */
//	private int dataSourceOrigin = DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_PARAMETER;
//
//	private int dataSourceType = DJConstants.DATA_SOURCE_TYPE_COLLECTION;
	
	
	private Style style;
	
	/**
	 * By default true,
	 */
	private boolean useParentReportParameters = true;
	
	/**
	 * This expression should point to a java.util.Map object
	 * which will be use as the parameters map for the subreport
	 */
	private String parametersExpression;
	
	/**
	 * Tells if the parameters maps origin is a parameter of the parent report, or a value of the current row (field)<br>
	 * It's value must be SUBREPORT_PARAMETER_MAP_ORIGIN_PARAMETER or SUBREPORT_PARAMETER_MAP_ORIGIN_FIELD
	 */
	private int parametersMapOrigin = DJConstants.SUBREPORT_PARAMETER_MAP_ORIGIN_PARAMETER;
	
	public int getParametersMapOrigin() {
		return parametersMapOrigin;
	}
	public void setParametersMapOrigin(int parametersMapOrigin) {
		this.parametersMapOrigin = parametersMapOrigin;
	}
	
	public String getParametersExpression() {
		return parametersExpression;
	}
	public void setParametersExpression(String parametersExpression) {
		this.parametersExpression = parametersExpression;
	}
	public boolean isUseParentReportParameters() {
		return useParentReportParameters;
	}
	public void setUseParentReportParameters(boolean useParentReportParameters) {
		this.useParentReportParameters = useParentReportParameters;
	}
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	public JasperReport getReport() {
		return report;
	}
	public void setReport(JasperReport design) {
		this.report = design;
	}
//	public String getDataSourceExpression() {
//		return dataSourceExpression;
//	}
//	public void setDataSourceExpression(String dataSourceExpression) {
//		this.dataSourceExpression = dataSourceExpression;
//	}
//	public int getDataSourceOrigin() {
//		return dataSourceOrigin;
//	}
//	public void setDataSourceOrigin(int dataSourceOrigin) {
//		this.dataSourceOrigin = dataSourceOrigin;
//	}
//	public int getDataSourceType() {
//		return dataSourceType;
//	}
//	public void setDataSourceType(int dataSourceType) {
//		this.dataSourceType = dataSourceType;
//	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public DynamicReport getDynamicReport() {
		return dynamicReport;
	}
	public void setDynamicReport(DynamicReport dynamicReport) {
		this.dynamicReport = dynamicReport;
	}
	public LayoutManager getLayoutManager() {
		return layoutManager;
	}
	public void setLayoutManager(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	public DJDataSource getDatasource() {
		return datasource;
	}
	public void setDatasource(DJDataSource datasource) {
		this.datasource = datasource;
	}

}
