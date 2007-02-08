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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import ar.com.fdvs.dj.core.layout.AbstractLayoutManager;
import ar.com.fdvs.dj.core.registration.ColumnRegistrationManager;
import ar.com.fdvs.dj.core.registration.ColumnsGroupRegistrationManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * Helper class for running a report and some other DJ related stuff
 */
public final class DynamicJasperHelper {

	private static final Log log = LogFactory.getLog(DynamicJasperHelper.class);

	private final static void registerEntities(DynamicJasperDesign jd, DynamicReport dr) {
		new ColumnRegistrationManager(jd).registerEntities(dr.getColumns());
		new ColumnsGroupRegistrationManager(jd).registerEntities(dr.getColumnsGroups());
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
		
		des.setName("DynamicReport...");
		return des;
	}

	private final static DynamicJasperDesign generateJasperDesign(DynamicReport dr) throws CoreException {
		DynamicJasperDesign jd = null;
		try {
			if (dr.getTemplateFileName() != null) {
				log.info("loading template file: "+dr.getTemplateFileName());
				URL url = DynamicJasperHelper.class.getClassLoader().getResource(
						dr.getTemplateFileName());
				jd = downCast(JRXmlLoader.load(url.openStream()));
			} else jd = getNewDesign(dr);
		} catch (JRException e) {
			throw new CoreException(e.getMessage());
		} catch (IOException e) {
			throw new CoreException(e.getMessage());
		}
		return jd;
	}

	private final static DynamicJasperDesign downCast(JasperDesign jd) throws CoreException {
		DynamicJasperDesign djd = new DynamicJasperDesign();
		log.info("downcasting JasperDesign");
		try {
			BeanUtils.copyProperties(djd, jd);
		} catch (IllegalAccessException e) {
			throw new CoreException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new CoreException(e.getMessage());
		}

		return djd;
	}

	public final static JasperPrint generateJasperPrint(DynamicReport dr, AbstractLayoutManager layoutManager, JRDataSource ds) {
		log.info("generating JasperPrint");
		JasperPrint jp = null;
		try {
			DynamicJasperDesign jd = generateJasperDesign(dr);
			registerEntities(jd, dr);
			layoutManager.applyLayout(jd, dr);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			jp = JasperFillManager.fillReport(jr,jd.getParametersWithValues(), ds);
		} catch (CoreException e) {
			log.error(e.getMessage());
		} catch (JRException e) {
			log.error(e.getMessage());
		}
		return jp;
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
