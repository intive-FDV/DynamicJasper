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

package ar.com.fdvs.dj.core;

import ar.com.fdvs.dj.domain.DJQuery;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.util.LayoutUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.type.PrintOrderEnum;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DJJRDesignHelper {

    private static final Log log = LogFactory.getLog(DJJRDesignHelper.class);

    public static DynamicJasperDesign getNewDesign(DynamicReport dr) {
        log.info("Creating DynamicJasperDesign");
        DynamicJasperDesign des = new DynamicJasperDesign();
        DynamicReportOptions options = dr.getOptions();
        Page page = options.getPage();

        des.setPrintOrder(PrintOrderEnum.VERTICAL);

        OrientationEnum orientation = OrientationEnum.PORTRAIT;
        if (!page.isOrientationPortrait())
            orientation = OrientationEnum.LANDSCAPE;
        des.setOrientation(orientation);

        des.setPageWidth(page.getWidth());
        des.setPageHeight(page.getHeight());

        des.setColumnWidth(options.getColumnWidth());
        des.setColumnSpacing(options.getColumnSpace());
        des.setLeftMargin(options.getLeftMargin());
        des.setRightMargin(options.getRightMargin());
        des.setTopMargin(options.getTopMargin());
        des.setBottomMargin(options.getBottomMargin());

        if (dr.getLanguage() != null)
            des.setLanguage(dr.getLanguage().toLowerCase());

        JRDesignSection detailSection = (JRDesignSection) des.getDetailSection();
        detailSection.getBandsList().add(new JRDesignBand());

        des.setPageHeader(new JRDesignBand());
        des.setPageFooter(new JRDesignBand());
        des.setSummary(new JRDesignBand());

        //Behavior options
        populateBehavioralOptions(dr, des);

        if (dr.getQuery() != null) {
            JRDesignQuery query = getJRDesignQuery(dr);
            des.setQuery(query);
        }

        for (String name : dr.getProperties().keySet()) {
            des.setProperty(name, dr.getProperties().get(name));
        }

        des.setName(dr.getReportName() != null ? dr.getReportName() : "DJR");
        return des;
    }

    protected static void populateBehavioralOptions(DynamicReport dr, DynamicJasperDesign des) {
        DynamicReportOptions options = dr.getOptions();
        des.setColumnCount(options.getColumnsPerPage());
        des.setWhenNoDataType(WhenNoDataTypeEnum.getByValue(dr.getWhenNoDataType()));
        des.setWhenResourceMissingType(WhenResourceMissingTypeEnum.getByValue(dr.getWhenResourceMissing()));
        des.setTitleNewPage(options.isTitleNewPage());
        des.setIgnorePagination(options.isIgnorePagination());

        JRDesignSection detailSection = (JRDesignSection) des.getDetailSection();
        List<JRBand> bands = detailSection.getBandsList();

        //FIXME we can do better in split! there may be more bands
        if (!bands.isEmpty()) {
            JRDesignBand detailBand = (JRDesignBand) bands.iterator().next();
            detailBand.setSplitType(LayoutUtils.getSplitTypeFromBoolean(dr.isAllowDetailSplit()));
        }


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
            if (dr.isTemplateImportParameters()) {
                for (JRParameter element : jd.getParametersList()) {
                    try {
                        djd.addParameter(element);
                    } catch (JRException e) {
                        if (log.isDebugEnabled()) {
                            String message = e.getMessage();
                            if (!message.startsWith("Duplicate declaration of parameter")) {
                                log.warn(message);
                            }
                        }
                    }
                }
            }

            //BeanUtils.copyProperties does not perform deep copy,
            //adding original fields definitions manually
            if (dr.isTemplateImportFields()) {
                for (JRField element : jd.getFieldsList()) {
                    try {
                        djd.addField(element);
                    } catch (JRException e) {
                        if (log.isDebugEnabled()) {
                            log.warn(e.getMessage());
                        }
                    }
                }
            }

            //BeanUtils.copyProperties does not perform deep copy,
            //adding original variables definitions manually
            if (dr.isTemplateImportVariables()) {
                for (JRVariable element : jd.getVariablesList()) {
                    try {
                        if (element instanceof JRDesignVariable) {
                            djd.addVariable((JRDesignVariable) element);
                        }
                    } catch (JRException e) {
                        if (log.isDebugEnabled()) {
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

                for (JRDataset jrDataset : jd.getDatasetsList()) {
                    JRDesignDataset dataset = (JRDesignDataset) jrDataset;
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
            for (String propName : properties) {
                String propValue = jd.getProperty(propName);
                djd.setProperty(propName, propValue);
            }


            //Add all existing styles in the design to the new one
            for (JRStyle style : jd.getStylesList()) {
                try {
                    djd.addStyle(style);
                } catch (JRException e) {
                    if (log.isDebugEnabled()) {
                        log.warn("Duplicated style (style name \"" + style.getName() + "\") when loading design: " + e.getMessage(), e);
                    }
                }
            }

            //Adding style templates templates
            JRReportTemplate[] templates = jd.getTemplates();
            if (templates != null) {
                for (JRReportTemplate template : templates) {
                    djd.addTemplate(template); //TODO Make a test for this!
                }
            }

            //even though some of this options may be present in the template, current values
            //in the DynamicReport should prevail
            populateBehavioralOptions(dr, djd);

        } catch (IllegalAccessException e) {
            throw new CoreException(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new CoreException(e.getMessage(), e);
        }

        return djd;
    }

    /**
     * Because all the layout calculations are made from the Domain Model of DynamicJasper, when loading
     * a template file, we have to populate the "ReportOptions" with the settings from the template file (ie: margins, etc)
     *
     * @param jd
     * @param dr
     */
    protected static void populateReportOptionsFromDesign(DynamicJasperDesign jd, DynamicReport dr) {
        DynamicReportOptions options = dr.getOptions();

        options.setBottomMargin(jd.getBottomMargin());
        options.setTopMargin(jd.getTopMargin());
        options.setLeftMargin(jd.getLeftMargin());
        options.setRightMargin(jd.getRightMargin());

        options.setColumnSpace(jd.getColumnSpacing());
        options.setColumnsPerPage(jd.getColumnCount());

        boolean isPortrait = true;
        if (jd.getOrientationValue() == OrientationEnum.LANDSCAPE) {
            isPortrait = false;
        }
        options.setPage(new Page(jd.getPageHeight(), jd.getPageWidth(), isPortrait));

        if (dr.getQuery() != null) {
            JRDesignQuery query = DJJRDesignHelper.getJRDesignQuery(dr);
            jd.setQuery(query);
        }

        if (dr.getReportName() != null) {
            jd.setName(dr.getReportName());
        }

    }

}
