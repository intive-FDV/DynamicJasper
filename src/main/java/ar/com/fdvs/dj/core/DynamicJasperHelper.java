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

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.registration.ColumnRegistrationManager;
import ar.com.fdvs.dj.core.registration.DJGroupRegistrationManager;
import ar.com.fdvs.dj.core.registration.DJGroupVariableDefRegistrationManager;
import ar.com.fdvs.dj.core.registration.VariableRegistrationManager;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariableDef;
import ar.com.fdvs.dj.domain.entities.Parameter;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PercentageColumn;
import ar.com.fdvs.dj.util.DJCompilerFactory;
import ar.com.fdvs.dj.util.LayoutUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/**
 * Helper class for running a report and some other DJ related stuff
 */
public class DynamicJasperHelper {

    private static final Log log = LogFactory.getLog(DynamicJasperHelper.class);
    public static final String DEFAULT_XML_ENCODING = "UTF-8";
    private static final String DJ_RESOURCE_BUNDLE = "dj-messages";

    private static final Random random = new Random(System.currentTimeMillis());

    private static void registerEntities(DynamicJasperDesign jd, DynamicReport dr, LayoutManager layoutManager) {
        ColumnRegistrationManager columnRegistrationManager = new ColumnRegistrationManager(jd, dr, layoutManager);
        columnRegistrationManager.registerEntities(dr.getColumns());

        DJGroupRegistrationManager djGroupRegistrationManager = new DJGroupRegistrationManager(jd, dr, layoutManager);
        djGroupRegistrationManager.registerEntities(dr.getColumnsGroups());

        VariableRegistrationManager variableRegistrationManager = new VariableRegistrationManager(jd, dr, layoutManager);
        variableRegistrationManager.registerEntities(dr.getVariables());

        registerPercentageColumnsVariables(jd, dr, layoutManager);
        registerOtherFields(jd, dr.getFields());
        Locale locale = dr.getReportLocale() == null ? Locale.getDefault() : dr.getReportLocale();
        if (log.isDebugEnabled()) {
            log.debug("Requested Locale = " + dr.getReportLocale() + ", Locale to use: " + locale);
        }
        ResourceBundle messages = null;
        if (dr.getResourceBundle() != null) {
            try {
                messages = ResourceBundle.getBundle(dr.getResourceBundle(), locale);
            } catch (MissingResourceException e) {
                log.warn(e.getMessage() + ", using default (dj-messages)");
            }
        }

        if (messages == null) {
            try {
                messages = ResourceBundle.getBundle(DJ_RESOURCE_BUNDLE, locale);
            } catch (MissingResourceException e) {
                log.warn(e.getMessage() + ", using default (dj-messages)");
                try {
                    messages = ResourceBundle.getBundle(DJ_RESOURCE_BUNDLE, Locale.ENGLISH); //this cannot fail because is included in the DJ jar
                } catch (MissingResourceException e2) {
                    log.error("Default messages not found: " + DJ_RESOURCE_BUNDLE + ", " + e2.getMessage(), e2);
                    throw new DJException("Default messajes file not found: " + DJ_RESOURCE_BUNDLE + "en.properties", e2);
                }
            }
        }
        jd.getParametersWithValues().put(JRDesignParameter.REPORT_RESOURCE_BUNDLE, messages);
        jd.getParametersWithValues().put(JRDesignParameter.REPORT_LOCALE, locale);
    }

    private static void registerPercentageColumnsVariables(DynamicJasperDesign jd, DynamicReport dr, LayoutManager layoutManager) {
        for (AbstractColumn column : dr.getColumns()) {
            /*
              Group should not be needed in the percentage column. There should be a variable for each group, using
              parent group as "rest group"
             */
            if (column instanceof PercentageColumn) {
                PercentageColumn percentageColumn = ((PercentageColumn) column);
                for (DJGroup djGroup : dr.getColumnsGroups()) {
                    JRDesignGroup jrGroup = LayoutUtils.getJRDesignGroup(jd, layoutManager, djGroup);
                    DJGroupVariableDefRegistrationManager variablesRM = new DJGroupVariableDefRegistrationManager(jd, dr, layoutManager, jrGroup);
                    DJGroupVariableDef variable = new DJGroupVariableDef(percentageColumn.getGroupVariableName(djGroup), percentageColumn.getPercentageColumn(), DJCalculation.SUM);
                    Collection<DJGroupVariableDef> entities = new ArrayList<DJGroupVariableDef>();
                    entities.add(variable);
                    variablesRM.registerEntities(entities);
                }
            }
        }
    }


    private static void registerOtherFields(DynamicJasperDesign jd, List<ColumnProperty> fields) {
        for (ColumnProperty element : fields) {
            JRDesignField field = new JRDesignField();
            field.setValueClassName(element.getValueClassName());
            field.setName(element.getProperty());
            try {
                jd.addField(field);
            } catch (JRException e) {
//				e.printStackTrace();
                //if the field is already registered, it's not a problem
                log.warn(e.getMessage());
            }
        }

    }


    protected static DynamicJasperDesign generateJasperDesign(DynamicReport dr) throws CoreException {
        DynamicJasperDesign jd;
        try {
            if (dr.getTemplateFileName() != null) {
                log.info("about to load template file: " + dr.getTemplateFileName() + ", Attemping to find the file directly in the file system.");
                File file = new File(dr.getTemplateFileName());
                if (file.exists()) {
                    JasperDesign jdesign = JRXmlLoader.load(file);
                    jd = DJJRDesignHelper.downCast(jdesign, dr);
                } else {
                    log.info("Not found: Attemping to find the file in the classpath...");
                    URL url = DynamicJasperHelper.class.getClassLoader().getResource(dr.getTemplateFileName());
                    if (url == null)
                        throw new CoreException("could not open template file: " + dr.getTemplateFileName());

                    JasperDesign jdesign = JRXmlLoader.load(url.openStream());
                    jd = DJJRDesignHelper.downCast(jdesign, dr);
                }
                DJJRDesignHelper.populateReportOptionsFromDesign(jd, dr);

            } else {
                //Create new JasperDesign from the scratch
                jd = DJJRDesignHelper.getNewDesign(dr);
            }

            //Force a unique name to the report
            jd.setName("" + jd.getName() + "_" + random.nextInt(10000));

            log.debug("The name for this report will be: " + jd.getName());

            jd.setScriptletClass(DJDefaultScriptlet.class.getName()); //Set up scripttlet so that custom expressions can do their magic

            registerParameters(jd, dr);
        } catch (JRException e) {
            throw new CoreException(e.getMessage(), e);
        } catch (IOException e) {
            throw new CoreException(e.getMessage(), e);
        }
        return jd;
    }

    protected static void registerParameters(DynamicJasperDesign jd, DynamicReport dr) {
        for (Parameter param  : dr.getParameters()) {
            JRDesignParameter jrparam = new JRDesignParameter();
            jrparam.setName(param.getName());
            jrparam.setValueClassName(param.getClassName());

            try {
                jd.addParameter(jrparam);
            } catch (JRException e) {
                throw new CoreException(e.getMessage(), e);
            }
        }

    }

    public static JasperPrint generateJasperPrint(DynamicReport dr, LayoutManager layoutManager, JRDataSource ds) throws JRException {
        return generateJasperPrint(dr, layoutManager, ds, new HashMap<String, Object>());
    }

    public static JasperPrint generateJasperPrint(DynamicReport dr, LayoutManager layoutManager, Collection collection) throws JRException {
        JRDataSource ds = new JRBeanCollectionDataSource(collection);
        return generateJasperPrint(dr, layoutManager, ds, new HashMap<String, Object>());
    }

    public static JasperPrint generateJasperPrint(DynamicReport dr, LayoutManager layoutManager, ResultSet resultSet) throws JRException {
        JRDataSource ds = new JRResultSetDataSource(resultSet);
        return generateJasperPrint(dr, layoutManager, ds, new HashMap<String, Object>());
    }

    /**
     * Compiles and fills the reports design.
     *
     * @param dr            the DynamicReport
     * @param layoutManager the object in charge of doing the layout
     * @param ds            The datasource
     * @param _parameters   Map with parameters that the report may need
     * @return
     * @throws JRException
     */
    public static JasperPrint generateJasperPrint(DynamicReport dr, LayoutManager layoutManager, JRDataSource ds, Map<String, Object> _parameters) throws JRException {
        log.info("generating JasperPrint");
        JasperPrint jp;


        JasperReport jr = DynamicJasperHelper.generateJasperReport(dr, layoutManager, _parameters);
        jp = JasperFillManager.fillReport(jr, _parameters, ds);

        return jp;
    }

    /**
     * For running queries embebed in the report design
     *
     * @param dr
     * @param layoutManager
     * @param con
     * @param _parameters
     * @return
     * @throws JRException
     */
    public static JasperPrint generateJasperPrint(DynamicReport dr, LayoutManager layoutManager, Connection con, Map<String, Object> _parameters) throws JRException {
        log.info("generating JasperPrint");
        JasperPrint jp;

        if (_parameters == null)
            _parameters = new HashMap<String, Object>();

        visitSubreports(dr, _parameters);
        compileOrLoadSubreports(dr, _parameters, "r");

        DynamicJasperDesign jd = generateJasperDesign(dr);
        Map<String, Object> params = new HashMap<String, Object>();
        if (!_parameters.isEmpty()) {
            registerParams(jd, _parameters);
            params.putAll(_parameters);
        }
        registerEntities(jd, dr, layoutManager);
        layoutManager.applyLayout(jd, dr);
        JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance()).setProperty(JRCompiler.COMPILER_PREFIX, DJCompilerFactory.getCompilerClassName());
        //JRProperties.setProperty(JRCompiler.COMPILER_PREFIX, DJCompilerFactory.getCompilerClassName());
        JasperReport jr = JasperCompileManager.compileReport(jd);
        params.putAll(jd.getParametersWithValues());
        jp = JasperFillManager.fillReport(jr, params, con);

        return jp;
    }


    /**
     * For compiling and filling reports whose datasource is passed as parameter (e.g. Hibernate, Mondrean, etc.)
     *
     * @param dr
     * @param layoutManager
     * @param _parameters
     * @return
     * @throws JRException
     */
    public static JasperPrint generateJasperPrint(DynamicReport dr, LayoutManager layoutManager, Map<String, Object> _parameters) throws JRException {
        log.info("generating JasperPrint");
        JasperPrint jp;

        if (_parameters == null)
            _parameters = new HashMap<String, Object>();

        visitSubreports(dr, _parameters);
        compileOrLoadSubreports(dr, _parameters, "r");

        DynamicJasperDesign jd = generateJasperDesign(dr);
        Map<String, Object> params = new HashMap<String, Object>();
        if (!_parameters.isEmpty()) {
            registerParams(jd, _parameters);
            params.putAll(_parameters);
        }
        registerEntities(jd, dr, layoutManager);
        layoutManager.applyLayout(jd, dr);
        JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance()).setProperty(JRCompiler.COMPILER_PREFIX, DJCompilerFactory.getCompilerClassName());
        JasperReport jr = JasperCompileManager.compileReport(jd);
        params.putAll(jd.getParametersWithValues());
        jp = JasperFillManager.fillReport(jr, params);

        return jp;
    }

    /**
     * Creates a jrxml file
     *
     * @param dr
     * @param layoutManager
     * @param _parameters
     * @param xmlEncoding   (default is UTF-8 )
     * @return
     * @throws JRException
     */
    public static String generateJRXML(DynamicReport dr, LayoutManager layoutManager, Map _parameters, String xmlEncoding) throws JRException {
        JasperReport jr = generateJasperReport(dr, layoutManager, _parameters);
        if (xmlEncoding == null)
            xmlEncoding = DEFAULT_XML_ENCODING;
        log.debug("generating JRXML");
        return JRXmlWriter.writeReport(jr, xmlEncoding);
    }

    /**
     * Creates a jrxml file
     *
     * @param dr
     * @param layoutManager
     * @param _parameters
     * @param xmlEncoding   (default is UTF-8 )
     * @param outputStream
     * @throws JRException
     */
    public static void generateJRXML(DynamicReport dr, LayoutManager layoutManager, Map _parameters, String xmlEncoding, OutputStream outputStream) throws JRException {
        JasperReport jr = generateJasperReport(dr, layoutManager, _parameters);
        if (xmlEncoding == null)
            xmlEncoding = DEFAULT_XML_ENCODING;
        JRXmlWriter.writeReport(jr, outputStream, xmlEncoding);
    }

    /**
     * Creates a jrxml file
     *
     * @param dr
     * @param layoutManager
     * @param _parameters
     * @param xmlEncoding   (default is UTF-8 )
     * @param filename      the path to the destination file
     * @throws JRException
     */
    public static void generateJRXML(DynamicReport dr, LayoutManager layoutManager, Map _parameters, String xmlEncoding, String filename) throws JRException {
        JasperReport jr = generateJasperReport(dr, layoutManager, _parameters);
        if (xmlEncoding == null)
            xmlEncoding = DEFAULT_XML_ENCODING;

        ensurePath(filename);
        log.debug("generating JRXML to " + filename);
        JRXmlWriter.writeReport(jr, filename, xmlEncoding);
    }

    public static void generateJRXML(JasperReport jr, String xmlEncoding, String filename) throws JRException {
        if (xmlEncoding == null)
            xmlEncoding = DEFAULT_XML_ENCODING;

        ensurePath(filename);
        log.debug("generating JRXML to " + filename);
        JRXmlWriter.writeReport(jr, filename, xmlEncoding);
    }

    private static void ensurePath(String filename) {
        File outputFile = new File(filename);
        File parentFile = outputFile.getParentFile();
        if (parentFile != null)
            //noinspection ResultOfMethodCallIgnored
            parentFile.mkdirs();
    }

    protected static void compileOrLoadSubreports(DynamicReport dr, Map<String, Object> _parameters, String namePrefix) throws JRException {
        log.debug("Visiting subreports for " + namePrefix);
        int groupnum = 0;

        for (DJGroup group : dr.getColumnsGroups()) {
            groupnum++;
            int subreportNum = 1;
            //Header Subreports
            for (Iterator<Subreport> iterator2 = group.getHeaderSubreports().iterator(); iterator2.hasNext(); subreportNum++) {
                Subreport subreport = iterator2.next();
                String name = namePrefix + "_g" + groupnum + "sr" + subreportNum + "h";
                subreport.setName(name);

                if (subreport.getDynamicReport() != null) {
                    Map<String, Object> originalParameters = _parameters;
                    if (subreport.getParametersExpression() != null) {
                        //noinspection unchecked
                        _parameters = (Map<String, Object>) originalParameters.get(subreport.getParametersExpression());
                    }

                    JasperReport jp = generateJasperReport(subreport.getDynamicReport(), subreport.getLayoutManager(), _parameters, name);
                    _parameters.put(name, jp);
                    subreport.setReport(jp);
                    log.debug("Adding Header Subreport " + name + " to parameters map");
                    _parameters = originalParameters;
                }

            }

            //Footer Subreports
            subreportNum = 1;
            for (Iterator<Subreport> iterator2 = group.getFooterSubreports().iterator(); iterator2.hasNext(); subreportNum++) {
                Subreport subreport = iterator2.next();
                String name = namePrefix + "[g" + groupnum + "sr" + subreportNum + "footer]";
                subreport.setName(name);

                if (subreport.getDynamicReport() != null) {
                    Map<String, Object> originalParameters = _parameters;
                    if (subreport.getParametersExpression() != null) {
                        //noinspection unchecked
                        _parameters = (Map<String, Object>) originalParameters.get(subreport.getParametersExpression());
                        if (_parameters == null) {
                            _parameters = new HashMap<String, Object>();
                            originalParameters.put(subreport.getParametersExpression(), _parameters);
                        }
                    }

                    JasperReport jp = generateJasperReport(subreport.getDynamicReport(), subreport.getLayoutManager(), _parameters, name);
                    _parameters.put(name, jp);
                    subreport.setReport(jp);
                    log.debug("Adding Footer Subreport " + name + " to parameters map");
                    _parameters = originalParameters;
                }

            }

        }

        log.debug("Finished compiling and loading subreports for " + namePrefix);
    }

    /**
     * For every String key, it registers the object as a parameter to make it available
     * in the report.
     *
     * @param jd
     * @param _parameters
     */
    public static void registerParams(DynamicJasperDesign jd, Map _parameters) {
        for (Object key : _parameters.keySet()) {
            if (key instanceof String) {
                try {
                    Object value = _parameters.get(key);
                    if (jd.getParametersMap().get(key) != null) {
                        log.warn("Parameter \"" + key + "\" already registered, skipping this one: " + value);
                        continue;
                    }

                    JRDesignParameter parameter = new JRDesignParameter();

                    if (value == null) //There are some Map implementations that allows nulls values, just go on
                        continue;

//					parameter.setValueClassName(value.getClass().getCanonicalName());
                    Class clazz = value.getClass().getComponentType();
                    if (clazz == null)
                        clazz = value.getClass();
                    parameter.setValueClass(clazz); //NOTE this is very strange
                    //when using an array as subreport-data-source, I must pass the parameter class name like this: value.getClass().getComponentType()
                    parameter.setName((String) key);
                    jd.addParameter(parameter);
                } catch (JRException e) {
                    //nothing to do
                }
            }

        }

    }

    /**
     * Compiles the report and applies the layout. <b>generatedParams</b> MUST NOT BE NULL
     * All the key objects from the generatedParams map that are String, will be registered as parameters of the report.
     *
     * @param dr
     * @param layoutManager
     * @param generatedParams
     * @return
     * @throws JRException
     */
    public static JasperReport generateJasperReport(DynamicReport dr, LayoutManager layoutManager, Map generatedParams) throws JRException {
        log.info("generating JasperReport (DynamicReport dr, LayoutManager layoutManager, Map generatedParams)");
        return generateJasperReport(dr, layoutManager, generatedParams, "r");
    }

    @SuppressWarnings("unchecked")
    public static JasperReport generateJasperReport(DynamicReport dr, LayoutManager layoutManager, Map generatedParams, String nameprefix) throws JRException {
        log.info("generating JasperReport with prefix: " + nameprefix);
        JasperReport jr;
        if (generatedParams == null) {
            log.warn("null parameters map passed to DynamicJasperHelper, you wont be able to retrieve some generated values during the layout process.");
            generatedParams = new HashMap();
        }

        visitSubreports(dr, generatedParams);
        compileOrLoadSubreports(dr, generatedParams, nameprefix);
        log.debug("Continuing with Jasper Design for " + nameprefix);

        DynamicJasperDesign jd = generateJasperDesign(dr);
        registerEntities(jd, dr, layoutManager);

        registerParams(jd, generatedParams); //if we have parameters from the outside, we register them

        if (dr.getJasperDesignDecorator()!=null){
            dr.getJasperDesignDecorator().beforeLayout(jd,generatedParams);
        }

        layoutManager.applyLayout(jd, dr);

        if (dr.getJasperDesignDecorator()!=null){
            dr.getJasperDesignDecorator().afterLayout(jd,generatedParams);
        }

        JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance()).setProperty(JRCompiler.COMPILER_PREFIX, DJCompilerFactory.getCompilerClassName());

        jr = JasperCompileManager.compileReport(jd);
        generatedParams.putAll(jd.getParametersWithValues());
        log.info("Done generating JasperReport for design with name: " + jd.getName());
        return jr;
    }

     /**
     * Performs any needed operation on subreports after they are built like ensuring proper subreport with
     * if "fitToParentPrintableArea" flag is set to true
     *
     * @param dr
     * @param _parameters
     * @throws JRException
     */
    @SuppressWarnings("unchecked")
    protected static void visitSubreports(DynamicReport dr, Map _parameters) {
        for (DJGroup group : dr.getColumnsGroups()) {
            //Header Subreports
            for (Subreport subreport : group.getHeaderSubreports()) {
                if (subreport.getDynamicReport() != null) {
                    visitSubreport(dr, subreport);
                    visitSubreports(subreport.getDynamicReport(), _parameters);
                }
            }

            //Footer Subreports
            for (Subreport subreport : group.getFooterSubreports()) {
                if (subreport.getDynamicReport() != null) {
                    visitSubreport(dr, subreport);
                    visitSubreports(subreport.getDynamicReport(), _parameters);
                }
            }
        }

    }

    protected static void visitSubreport(DynamicReport parentDr, Subreport subreport) {
        DynamicReport childDr = subreport.getDynamicReport();
        if (subreport.isFitToParentPrintableArea()) {
            childDr.getOptions().setPage(parentDr.getOptions().getPage());
            childDr.getOptions().setLeftMargin(parentDr.getOptions().getLeftMargin());
            childDr.getOptions().setRightMargin(parentDr.getOptions().getRightMargin());
        }
    }

}
