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

package ar.com.fdvs.dj.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRReportTemplate;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.DJQuery;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.constants.Page;

public class DJJRDesignHelper {

	private static final Log log = LogFactory.getLog(DJJRDesignHelper.class);

	public static DynamicJasperDesign getNewDesign(DynamicReport dr) {
		log.info("obtaining DynamicJasperDesign instance");
		DynamicJasperDesign des = new DynamicJasperDesign();
		DynamicReportOptions options = dr.getOptions();
		Page page = options.getPage();

		des.setPrintOrder(JasperDesign.PRINT_ORDER_VERTICAL);

		byte orientation = page.isOrientationPortrait() ? JasperReport.ORIENTATION_PORTRAIT : JasperReport.ORIENTATION_LANDSCAPE;
		des.setOrientation(orientation);

		des.setPageWidth(page.getWidth());
		des.setPageHeight(page.getHeight());

		des.setColumnWidth(options.getColumnWidth());
		des.setColumnSpacing(options.getColumnSpace().intValue());
		des.setLeftMargin(options.getLeftMargin().intValue());
		des.setRightMargin(options.getRightMargin().intValue());
		des.setTopMargin(options.getTopMargin().intValue());
		des.setBottomMargin(options.getBottomMargin().intValue());



		des.setDetail(new JRDesignBand());


		des.setPageHeader(new JRDesignBand());
		des.setPageFooter(new JRDesignBand());
		des.setSummary(new JRDesignBand());

		//Behavior options
		populateBehavioralOptions(dr, des);

		if (dr.getQuery() != null){
			JRDesignQuery query = getJRDesignQuery(dr);
			des.setQuery(query);
		}

		for (Iterator iterator = dr.getProperties().keySet().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			des.setProperty(name, (String) dr.getProperties().get(name));
		}

		des.setName(dr.getReportName() != null ? dr.getReportName() : "DynamicReport");
		return des;
	}

	protected static void populateBehavioralOptions(DynamicReport dr, DynamicJasperDesign des) {
		DynamicReportOptions options = dr.getOptions();
		des.setColumnCount(options.getColumnsPerPage().intValue());
		des.setWhenNoDataType(dr.getWhenNoDataType());
		des.setWhenResourceMissingType(dr.getWhenResourceMissing());
		des.setTitleNewPage(options.isTitleNewPage());
		des.setIgnorePagination(options.isIgnorePagination());
		des.getDetail().setSplitAllowed(dr.isAllowDetailSplit());
		des.setSummaryNewPage(options.isSummaryNewPage());
	}

	protected static JRDesignQuery getJRDesignQuery(DynamicReport dr) {
		JRDesignQuery query = new JRDesignQuery();
		query.setText(dr.getQuery().getText());
		query.setLanguage(dr.getQuery().getLanguage());
		return query;
	}


	public static DynamicJasperDesign downCast(JasperDesign jd, DynamicReport dr) throws CoreException {
		DynamicJasperDesign djd = new DynamicJasperDesign();
		log.info("downcasting JasperDesign");
		try {
			BeanUtils.copyProperties(djd, jd);

			//BeanUtils.copyProperties does not perform deep copy,
			//adding original parameter definitions manually
			if (dr.isTemplateImportParameters()){
				for (Iterator iter = jd.getParametersList().iterator(); iter.hasNext();) {
					JRParameter element = (JRParameter) iter.next();
					try {
						djd.addParameter(element);
					} catch (JRException e) {
						if (log.isDebugEnabled()){
							log.warn(e.getMessage());
						}
					}
				}
			}

			//BeanUtils.copyProperties does not perform deep copy,
			//adding original fields definitions manually
			if (dr.isTemplateImportFields()){
				for (Iterator iter = jd.getFieldsList().iterator(); iter.hasNext();) {
					JRField element = (JRField) iter.next();
					try {
						djd.addField(element);
					} catch (JRException e) {
						if (log.isDebugEnabled()){
							log.warn(e.getMessage());
						}
					}
				}
			}

			//BeanUtils.copyProperties does not perform deep copy,
			//adding original variables definitions manually
			if (dr.isTemplateImportVariables()){
				for (Iterator iter = jd.getVariablesList().iterator(); iter.hasNext();) {
					JRVariable element = (JRVariable) iter.next();
					try {
						if (element instanceof JRDesignVariable){
							djd.addVariable((JRDesignVariable) element);
						}
					} catch (JRException e) {
						if (log.isDebugEnabled()){
							log.warn(e.getMessage());
						}
					}
				}
			}

  			//BeanUtils.copyProperties does not perform deep copy,
			//adding original dataset definitions manually
			if (dr.isTemplateImportDatasets()) {
				// also copy query
				JRQuery query = jd.getQuery();
				if (query instanceof JRDesignQuery) {
					djd.setQuery((JRDesignQuery) query);
					dr.setQuery(new DJQuery(query.getText(), query
							.getLanguage()));
				}

				for (Iterator iter = jd.getDatasetsList().iterator(); iter.hasNext();) {
					JRDesignDataset dataset = (JRDesignDataset) iter.next();
					try {
						djd.addDataset(dataset);
					} catch (JRException e) {
						if (log.isDebugEnabled()) {
							log.warn(e.getMessage());
						}
					}
				}
			}

			//BeanUtils.copyProperties does not perform deep copy,
			//adding original properties definitions manually
			String[] properties = jd.getPropertyNames();
			for (int i = 0; i < properties.length; i++) {
				String propName = properties[i];
				String propValue = jd.getProperty(propName);
				djd.setProperty(propName, propValue);
			}


			//Add all existing styles in the design to the new one
			for (Iterator iterator = jd.getStylesList().iterator(); iterator.hasNext();) {
				JRStyle style = (JRStyle) iterator.next();
				try {
					djd.addStyle(style);
				} catch (JRException e) {
					if (log.isDebugEnabled()){
						log.warn("Duplicated style (style name \""+ style.getName()+"\") when loading design: " + e.getMessage(), e);
					}
				}
			}
			
			//Adding style templates templates
			JRReportTemplate[] templates = jd.getTemplates();
			if (templates != null){
				for (int i = 0; i < templates.length; i++) {
					djd.addTemplate(templates[i]); //TODO Make a test for this!
				}
			}
			
			//even though some of this options may be present in the template, current values 
			//in the DynamicReport should prevail
			populateBehavioralOptions(dr, djd);
			
		} catch (IllegalAccessException e) {
			throw new CoreException(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			throw new CoreException(e.getMessage(),e);
		}

		return djd;
	}

	/**
	 * Because all the layout calculations are made from the Domain Model of DynamicJasper, when loading
	 * a template file, we have to populate the "ReportOptions" with the settings from the template file (ie: margins, etc)
	 * @param jd
	 * @param dr
	 */
	protected static void populateReportOptionsFromDesign(DynamicJasperDesign jd, DynamicReport dr) {
		DynamicReportOptions options = dr.getOptions();

		options.setBottomMargin(new Integer(jd.getBottomMargin()));
		options.setTopMargin(new Integer(jd.getTopMargin()));
		options.setLeftMargin(new Integer(jd.getLeftMargin()));
		options.setRightMargin(new Integer(jd.getRightMargin()));

		options.setColumnSpace(new Integer(jd.getColumnSpacing()));
		options.setColumnsPerPage(new Integer(jd.getColumnCount()));

		options.setPage(new Page(jd.getPageHeight(),jd.getPageWidth()));

		if (dr.getQuery() != null){
			JRDesignQuery query = DJJRDesignHelper.getJRDesignQuery(dr);
			jd.setQuery(query);
		}
		
		if (dr.getReportName() != null){
			jd.setName(dr.getReportName());
		}

	}

}
