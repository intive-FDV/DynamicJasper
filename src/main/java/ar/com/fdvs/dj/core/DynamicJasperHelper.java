/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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

package ar.com.fdvs.dj.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.layout.AbstractLayoutManager;
import ar.com.fdvs.dj.core.registration.ColumnRegistrationManager;
import ar.com.fdvs.dj.core.registration.ColumnsGroupRegistrationManager;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;


/**
 * Helper class for running a report and some other DJ related stuff
 */
public final class DynamicJasperHelper {

	private static final Log log = LogFactory.getLog(DynamicJasperHelper.class);
	private static final String DJ_RESOURCE_BUNDLE ="dj-messages";

	private final static void registerEntities(DynamicJasperDesign jd, DynamicReport dr) {
		new ColumnRegistrationManager(jd,dr).registerEntities(dr.getColumns());
		new ColumnsGroupRegistrationManager(jd,dr).registerEntities(dr.getColumnsGroups());
		registerOtherFields(jd,dr.getFields());
		Locale locale = dr.getReportLocale() == null ? Locale.getDefault() : dr.getReportLocale();
		ResourceBundle messages = null;
		if (dr.getResourceBundle() != null ){
			try {
				messages =  ResourceBundle.getBundle(dr.getResourceBundle(), locale);
			} catch (MissingResourceException e){ log.warn(e.getMessage() + ", usign defaut (dj-messages)");}
		}
		
		if (messages == null) {
			messages =  ResourceBundle.getBundle(DJ_RESOURCE_BUNDLE, locale);
		}
		jd.getParametersWithValues().put(JRDesignParameter.REPORT_RESOURCE_BUNDLE, messages);
		jd.getParametersWithValues().put(JRDesignParameter.REPORT_LOCALE, locale);
//		JRDesignParameter.REPORT_RESOURCE_BUNDLE
//		report.		
	}

	private static void registerOtherFields(DynamicJasperDesign jd, List fields) {
		for (Iterator iter = fields.iterator(); iter.hasNext();) {
			ColumnProperty element = (ColumnProperty) iter.next();
			JRDesignField field = new JRDesignField();
			field.setValueClassName(element.getValueClassName());
			field.setName(element.getProperty());
			try {
				jd.addField(field);
			} catch (JRException e) {
//				e.printStackTrace(); 
				//if the field is already registered, it's not a problem
				log.warn(e.getMessage(),e);
			}
		}
		
	}

	private final static DynamicJasperDesign getNewDesign(DynamicReport dr) {
		log.info("obtaining DynamicJasperDesign instance");
		DynamicJasperDesign des = new DynamicJasperDesign();
		DynamicReportOptions options = dr.getOptions();
		Page page = options.getPage();

		des.setColumnCount(options.getColumnsPerPage().intValue());
		des.setPrintOrder(JasperDesign.PRINT_ORDER_VERTICAL);

		des.setPageWidth(page.getWidth());
		des.setPageHeight(page.getHeight());

		des.setColumnWidth(options.getColumnWidth());
		des.setColumnSpacing(options.getColumnSpace().intValue());
		des.setLeftMargin(options.getLeftMargin().intValue());
		des.setRightMargin(options.getRightMargin().intValue());
		des.setTopMargin(options.getTopMargin().intValue());
		des.setBottomMargin(options.getBottomMargin().intValue());

		des.setWhenNoDataType(JasperDesign.WHEN_NO_DATA_TYPE_NO_PAGES);
		des.setTitleNewPage(false);
		des.setSummaryNewPage(false);

		des.setDetail(new JRDesignBand());
		des.setPageHeader(new JRDesignBand());
		des.setPageFooter(new JRDesignBand());
		des.setSummary(new JRDesignBand());

		des.setTitleNewPage(options.isTitleNewPage());

		des.setName("DynamicReport...");
		return des;
	}

	private final static DynamicJasperDesign generateJasperDesign(DynamicReport dr) throws CoreException {
		DynamicJasperDesign jd = null;
		try {
			if (dr.getTemplateFileName() != null) {
				log.info("loading template file: "+dr.getTemplateFileName());
				log.info("Attemping to find the file directly in the file system...");
				File file = new File(dr.getTemplateFileName());
				if (file.exists()){
					jd = downCast(JRXmlLoader.load(file));
				} else {
					log.info("Not found: Attemping to find the file in the classpath...");
					URL url = DynamicJasperHelper.class.getClassLoader().getResource(
							dr.getTemplateFileName());
					jd = downCast(JRXmlLoader.load(url.openStream()));
				}
				populateReportOptionsFromDesign(jd,dr);
			} else {
				//Create new JasperDesign from the scratch
				jd = getNewDesign(dr);
			}
		} catch (JRException e) {
			throw new CoreException(e.getMessage());
		} catch (IOException e) {
			throw new CoreException(e.getMessage());
		}
		return jd;
	}

	/**
	 * Becasuse all the layout calculations are made from the Domain Model of DynamicJasper, when loading
	 * a template file, we have to populate the "ReportOptions" with the settings from the template file (ie: margins, etc)
	 * @param jd
	 * @param dr
	 */
	private static void populateReportOptionsFromDesign(DynamicJasperDesign jd, DynamicReport dr) {
		DynamicReportOptions options = dr.getOptions();
		
		options.setBottomMargin(new Integer(jd.getBottomMargin()));
		options.setTopMargin(new Integer(jd.getTopMargin()));
		options.setLeftMargin(new Integer(jd.getLeftMargin()));
		options.setRightMargin(new Integer(jd.getRightMargin()));
		
		options.setColumnSpace(new Integer(jd.getColumnSpacing()));
		options.setColumnsPerPage(new Integer(jd.getColumnCount()));
		
		options.setPage(new Page(jd.getPageHeight(),jd.getPageWidth()));
		
	}

	private final static DynamicJasperDesign downCast(JasperDesign jd) throws CoreException {
		DynamicJasperDesign djd = new DynamicJasperDesign();
		log.info("downcasting JasperDesign");
		try {
			BeanUtils.copyProperties(djd, jd);
			
			//BeanUtils.copyProperties does not perform deep copy, 
			//adding original parameter definitions manually
			for (Iterator iter = jd.getParametersList().iterator(); iter.hasNext();) {
				JRParameter element = (JRParameter) iter.next();
				try {
					djd.addParameter(element);
				} catch (JRException e) {	}
				
			}
			
			
		} catch (IllegalAccessException e) {
			throw new CoreException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new CoreException(e.getMessage());
		}

		return djd;
	}

	public final static JasperPrint generateJasperPrint(DynamicReport dr, AbstractLayoutManager layoutManager, JRDataSource ds) {
        return generateJasperPrint(dr, layoutManager, ds, new HashMap());
    }
	
	public final static JasperPrint generateJasperPrint(DynamicReport dr, AbstractLayoutManager layoutManager, Collection collection) {
		JRDataSource ds = new JRBeanCollectionDataSource(collection);
		return generateJasperPrint(dr, layoutManager, ds, new HashMap());
	}
	
	public final static JasperPrint generateJasperPrint(DynamicReport dr, AbstractLayoutManager layoutManager, ResultSet resultSet) {
		JRDataSource ds = new JRResultSetDataSource(resultSet);
		return generateJasperPrint(dr, layoutManager, ds, new HashMap());
	}

    public final static JasperPrint generateJasperPrint(DynamicReport dr, AbstractLayoutManager layoutManager, JRDataSource ds, Map _parameters) {
		log.info("generating JasperPrint");
		JasperPrint jp = null;
		try {
			DynamicJasperDesign jd = generateJasperDesign(dr);
			registerEntities(jd, dr);
			layoutManager.applyLayout(jd, dr);
			JasperReport jr = JasperCompileManager.compileReport(jd);
            Map params = new HashMap(_parameters);
            params.putAll(jd.getParametersWithValues());
            jp = JasperFillManager.fillReport(jr, params, ds);
		} catch (CoreException e) {
			log.error(e.getMessage(),e);
		} catch (JRException e) {
			log.error(e.getMessage(),e);
		}
		return jp;
	}

	public final static JasperReport generateJasperReport(DynamicReport dr, AbstractLayoutManager layoutManager) {
		log.info("generating JasperPrint");
//		JasperPrint jp = null;
		JasperReport jr = null;
		try {
			DynamicJasperDesign jd = generateJasperDesign(dr);
			registerEntities(jd, dr);
			layoutManager.applyLayout(jd, dr);
			jr = JasperCompileManager.compileReport(jd);
//			jp = JasperFillManager.fillReport(jr,jd.getParametersWithValues(), ds);
		} catch (CoreException e) {
			log.error(e.getMessage());
		} catch (JRException e) {
			log.error(e.getMessage());
		}
		return jr;
	}

	public final static ColumnsGroup getColumnGroup(AbstractColumn col, List groups) {
		Iterator it = groups.iterator();
		while (it.hasNext()) {
			ColumnsGroup group = (ColumnsGroup) it.next();
			if (group.getColumnToGroupBy().equals(col))
				return group;
		}
		return null;
	}

	public final static boolean existsGroupWithColumnNames(List groups) {
		Iterator it = groups.iterator();
		while (it.hasNext()) {
			ColumnsGroup group = (ColumnsGroup) it.next();
			if (group.getLayout().isShowColumnNames())
				return true;
		}
		return false;
	}

}
