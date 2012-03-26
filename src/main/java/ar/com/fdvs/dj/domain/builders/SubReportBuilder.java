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

package ar.com.fdvs.dj.domain.builders;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;

public class SubReportBuilder {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(SubReportBuilder.class);


	private Subreport subreport = new Subreport();

	public Subreport build() throws DJBuilderException {
		if (subreport.getPath() == null && subreport.getDynamicReport() == null && subreport.getReport() == null)
			throw new DJBuilderException("No subreport origin defined (path, dynamicReport, jasperReport)");

		//If the subreport comes from a file, then we load it now
		if (subreport.getPath() != null) {
			JasperReport jr = null;
			File file = new File(subreport.getPath());
			if (file.exists()){
				logger.debug("Loading subreport from file path");
				try {
					jr = (JasperReport) JRLoader.loadObject(file);
				} catch (JRException e) {
					throw new DJBuilderException("Could not load subreport.",e);
				}
			} else {
				logger.debug("Loading subreport from classpath");
				URL url = DynamicJasperHelper.class.getClassLoader().getResource(subreport.getPath());
				try {
					jr = (JasperReport) JRLoader.loadObject(url.openStream());
				} catch (IOException e) {
					throw new DJBuilderException("Could not open subreport as an input stream",e);
				} catch (JRException e) {
					throw new DJBuilderException("Could not load subreport.",e);
				}
			}

			subreport.setReport(jr);
		}

		return subreport;
	}

	/**
	 * Indicates from where to get the data source.<br>
	 *
	 * @param origin Must be one of these constans located in DJConstants inteface<br>
	 * - SUBREPORT_DATAS_OURCE_ORIGIN_PARAMETER<br>
	 * - SUBREPORT_DATAS_OURCE_ORIGIN_FIELD<br>
	 * - SUBREPORT_DATAS_OURCE_ORIGIN_INTERNAL<br>
	 * @param type tell if the datasource is a Collection, an Array, a ResultSet or whatever.<br>
	 * Its value must be a constant from {@link DJConstants} of the like DATA_SOURCE_TYPE_...
	 * @param expression is -depending on the origin- te path to the datasource<br>
	 * ie: if origin is SUBREPORT_DATAS_OURCE_ORIGIN_PARAMETER, then expression can be "subreport_datasource".<br>
	 * You must in the parameters map an object using "subreport_datasource" as the key.<br>
	 * The object must be an instance of {@link JRDataSource} or any of the following<br>
	 * Collection, Array, ResultSet, or any of the data source types provided by Jasper Reports
	 * @return
	 */
	public SubReportBuilder setDataSource(int origin, int type, String expression) {
		DJDataSource ds = new DJDataSource(expression, origin, type);
		subreport.setDatasource(ds);
		return this;
	}

	/**
	 * like addDataSource(int origin, int type, String expression) but the type will be of the {@link JRDataSource}
	 * @param origin
	 * @param expression
	 * @return
	 */
	public SubReportBuilder setDataSource(int origin, String expression) {
		return setDataSource(origin, DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE, expression);
	}

	/**
	 * like addDataSource(int origin, String expression) but the origin will be from a Parameter
	 * @param origin
	 * @param expression
	 * @return
	 */
	public SubReportBuilder setDataSource(String expression) {
		return setDataSource(DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE, expression);
	}

	public SubReportBuilder setReport(JasperReport jasperReport) {
		subreport.setReport(jasperReport);
		return this;
	}

	public SubReportBuilder setDynamicReport(DynamicReport dynamicReport, LayoutManager layoutManager) {
		subreport.setDynamicReport(dynamicReport);
		subreport.setLayoutManager(layoutManager);
		return this;
	}

	public SubReportBuilder setPathToReport(String path) {
		subreport.setPath(path);
		return this;
	}

	public SubReportBuilder setStartInNewPage(boolean startInNewPage) {
		subreport.setStartInNewPage(startInNewPage);
		return this;
	}

	public SubReportBuilder addParameter(SubreportParameter sp) {
		subreport.getParameters().add(sp);
		return this;
	}

	public SubReportBuilder addParameterFieldType(String propertyName, String paramName) {
		SubreportParameter sp = new SubreportParameter(paramName,propertyName,null,DJConstants.SUBREPORT_PARAM_ORIGIN_FIELD);
		subreport.getParameters().add(sp);
		return this;
	}

	/**
	 * When true, and id the subreport is dynamic, it's page size and margins will match with the parent report
	 * @param fitparent
	 * @return
	 */
	public SubReportBuilder setFitToParentPrintableArea(boolean  fitparent) {
		subreport.setFitToParentPrintableArea(fitparent);
		return this;
	}


	/**
	 * If false, and the report doesn't fit in the space given until end of page, it will be pushed to the next
	 * page.
	 * default is TRUE
	 * @param splitAllowed
	 * @return
	 */
	public SubReportBuilder setSplitAllowed(boolean  splitAllowed) {
		subreport.setSplitAllowed(splitAllowed);
		return this;
	}


	/**
	 * defines the KEY in the parent report parameters map where to get the subreport parameters map.
	 * @param path where to get the parameter map for the subrerpot.
	 * @return
	 */
	public SubReportBuilder setParameterMapPath(String path) {
		subreport.setParametersExpression(path);
		subreport.setParametersMapOrigin(DJConstants.SUBREPORT_PARAMETER_MAP_ORIGIN_PARAMETER);
		return this;
	}

	/**
	 *
	 * @param path where to get the parameter map for the subrerpot
	 * @param origin where the paramters map comes from: DJConstants.SUBREPORT_PARAMETER_MAP_ORIGIN_PARAMETER or DJConstants.SUBREPORT_PARAMETER_MAP_ORIGIN_FIELD
	 * @return
	 */
	public SubReportBuilder setParameterMapPath(String path, int origin) {
		subreport.setParametersExpression(path);
		subreport.setParametersMapOrigin(origin);
		return this;
	}

	public SubReportBuilder setUserParentReportParameterMap(boolean useParent) {
		subreport.setUseParentReportParameters(useParent);
		return this;
	}



}
