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

package ar.com.fdvs.dj.domain.entities;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JasperReport;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;

public class Subreport extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private JasperReport report;

	/**
	 * For internal usage 
	 */
	private String name;
	
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
	 * @since 3.0.1
	 */
	private boolean splitAllowed = true;

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
		if (parametersExpression != null)
			useParentReportParameters = false;
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
	public boolean isSplitAllowed() {
		return splitAllowed;
	}
	public void setSplitAllowed(boolean splitAllowed) {
		this.splitAllowed = splitAllowed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
