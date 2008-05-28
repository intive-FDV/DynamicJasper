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

package ar.com.fdvs.dj.domain.entities;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JasperReport;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;

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

	/**
	 * If true, a page-break will be added before placing the sub-report
	 */
	private boolean startInNewPage = false;

	/**
	 * When the sub-report is dynamic (and no template), we can tell the report to
	 * fit its parent report width
	 */
	private boolean fitToParentPrintableArea = true;

	private DJDataSource datasource;

	/**
	 * List<SubreportParamter>
	 * The objects from the parent report that will be visible as parameters in the subreport
	 */
	private List parameters = new ArrayList();

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
	public List getParameters() {
		return parameters;
	}
	public void setParameters(List parameters) {
		this.parameters = parameters;
	}
	public boolean isStartInNewPage() {
		return startInNewPage;
	}
	public void setStartInNewPage(boolean startInNewPage) {
		this.startInNewPage = startInNewPage;
	}
	public boolean isFitToParentPrintableArea() {
		return fitToParentPrintableArea;
	}
	public void setFitToParentPrintableArea(boolean fitToParentPrintableArea) {
		this.fitToParentPrintableArea = fitToParentPrintableArea;
	}

}
