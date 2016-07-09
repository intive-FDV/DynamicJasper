/*
 * DynamicJasper: A library for creating reports dynamically by specifying
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

package ar.com.fdvs.dj.domain.builders;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
