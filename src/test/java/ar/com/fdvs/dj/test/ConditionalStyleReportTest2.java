/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.com.fdvs.dj.test;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionStyleExpression;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rve
 */
public class ConditionalStyleReportTest2 extends TestCase {
    private DynamicReportBuilder drb;

    public DynamicReport buildDynamicReport() {
        return drb.build();
    }

    public ConditionalStyleReportTest2() {
        try {
            drb = new DynamicReportBuilder();
            drb.setGrandTotalLegend("Total");
            drb.setPageSizeAndOrientation(new Page(585, 842));
            drb.setUseFullPageWidth(true);
            drb.setAllowDetailSplit(false);
            drb.setWhenNoData("No data", null, true, true);
            //drb.setReportName("Test inner crosstab");
            ArrayList listCondStyle = getConditonalStyles();
            AbstractColumn columnState1 = ColumnBuilder.getNew()
                    .setColumnProperty("1", Integer.class.getName()).setTitle("Sales")
                    .setHeaderStyle(getHeaderStyle()).setStyle(getDataStyle())
                    .addConditionalStyles(listCondStyle)
                    .build();
            drb.addColumn(columnState1);

            AbstractColumn columnState2 = ColumnBuilder.getNew()
                    .setColumnProperty("2", String.class.getName()).setTitle("Year")
                    .setHeaderStyle(getHeaderStyle()).setStyle(getDataStyle())
                    .addConditionalStyles(listCondStyle)
                    .build();
            drb.addColumn(columnState2);

            drb.addField("3", Boolean.class.getName());

        } catch (ColumnBuilderException ex) {
            ex.printStackTrace();
            drb = null;
        }
    }

    private static Style getRedStyle() {
        Style alertStyle = new Style();
        alertStyle.setTransparency(Transparency.OPAQUE);
        alertStyle.setBackgroundColor(Color.RED);
        alertStyle.setTextColor(Color.BLACK);
        alertStyle.setVerticalAlign(VerticalAlign.TOP);
        return alertStyle;
    }

    private static Style getBlueStyle() {
        Style alertStyle = new Style();
        alertStyle.setTransparency(Transparency.OPAQUE);
        alertStyle.setBackgroundColor(Color.BLUE);
        alertStyle.setTextColor(Color.BLACK);
        alertStyle.setVerticalAlign(VerticalAlign.TOP);
        return alertStyle;
    }

    private static Style getGrayStyle() {
        Style alertStyle = new Style();
        alertStyle.setTransparency(Transparency.OPAQUE);
        alertStyle.setBackgroundColor(Color.LIGHT_GRAY);
        alertStyle.setTextColor(Color.BLACK);
        alertStyle.setVerticalAlign(VerticalAlign.TOP);
        return alertStyle;
    }

    private static Style getGreenStyle() {
        Style alertStyle = new Style();
        alertStyle.setTransparency(Transparency.OPAQUE);
        alertStyle.setBackgroundColor(Color.GREEN);
        alertStyle.setTextColor(Color.BLACK);
        alertStyle.setVerticalAlign(VerticalAlign.TOP);
        return alertStyle;
    }

    private static Style getYellowStyle() {
        Style alertStyle = new Style();
        alertStyle.setTransparency(Transparency.OPAQUE);
        alertStyle.setBackgroundColor(Color.YELLOW);
        alertStyle.setTextColor(Color.BLACK);
        alertStyle.setVerticalAlign(VerticalAlign.TOP);
        return alertStyle;
    }

    private static Style getOrangeStyle() {
        Style alertStyle = new Style();
        alertStyle.setTransparency(Transparency.OPAQUE);
        alertStyle.setBackgroundColor(Color.ORANGE);
        alertStyle.setTextColor(Color.BLACK);
        alertStyle.setVerticalAlign(VerticalAlign.TOP);
        return alertStyle;
    }

    private static Style getHeaderStyle() {
        Style headerStyle = new Style();
        headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
        headerStyle.setTransparency(Transparency.OPAQUE);
        headerStyle.setBackgroundColor(Color.BLUE);
        headerStyle.setTextColor(Color.WHITE);
        headerStyle.setVerticalAlign(VerticalAlign.TOP);

        headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        return headerStyle;
    }

    private static Style getDataStyle() {
        Style dataStyle = new Style();
        dataStyle.setTransparency(Transparency.TRANSPARENT);
        dataStyle.setTextColor(Color.BLACK);
        dataStyle.setVerticalAlign(VerticalAlign.TOP);
        return dataStyle;
    }

    public static List getList() {
        List list = new ArrayList();

        // Add the first line
        Map result1 = new HashMap();
        result1.put("1", 150);
        result1.put("2", "2007");
        result1.put("3", "Rouge");

        list.add(result1);

        // Add the second line
        Map result2 = new HashMap();
        result2.put("1", 250);
        result2.put("2", "2008");
        result2.put("3", "Vert");

        list.add(result2);

        // Add the third line
        Map result3 = new HashMap();
        result3.put("1", 203);
        result3.put("2", String.valueOf("2009"));
        result3.put("3", "Gris");

        list.add(result3);

        return list;
    }

    private ArrayList getConditonalStyles() {
        ArrayList conditionalStyles = new ArrayList();
        FicheCondition fc = new FicheCondition("3", "Rouge");

        ConditionalStyle cs = new ConditionalStyle(fc, getRedStyle());
        conditionalStyles.add(cs);

        fc = new FicheCondition("3", "Vert");

        cs = new ConditionalStyle(fc, getGreenStyle());
        conditionalStyles.add(cs);

        fc = new FicheCondition("3", "Gris");

        cs = new ConditionalStyle(fc, getGrayStyle());
        conditionalStyles.add(cs);

        return conditionalStyles;
    }

    private class FicheCondition extends ConditionStyleExpression implements CustomExpression {
        private String fieldName;
        private String colorValue;

        public FicheCondition(String fieldName, String colorValue) {
            this.fieldName = fieldName;
            this.colorValue = colorValue;
        }

        public Object evaluate(Map fields, Map variables, Map parameters) {
            boolean condition = false;
            Object currentValue = fields.get(fieldName);
            if (currentValue instanceof String) {
                String s = (String) currentValue;
                condition = colorValue.equals(currentValue);
            }
            return condition;
        }

        public String getClassName() {
            return Boolean.class.getName();
        }
    }

    public static LayoutManager getLayoutManager() {
        return new ClassicLayoutManager();
    }

    public void test() throws JRException, FileNotFoundException {
        ConditionalStyleReportTest2 db = new ConditionalStyleReportTest2();
        List list = getList();
        DynamicReport dynamicReport = db.buildDynamicReport();
        JRDataSource ds = new JRBeanCollectionDataSource(list);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynamicReport, getLayoutManager(), ds);
/*        JRXlsExporter exporter = new JRXlsExporter();

        File outputFile = new File(System.getProperty("user.dir") + "/target/" + this.getClass().getName() + ".xls");
        FileOutputStream fos = new FileOutputStream(outputFile);

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos); //and output stream

        //Excel specific parameter
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        exporter.exportReport();*/

        ReportExporter.exportReportXls(jp, System.getProperty("user.dir") + "/target/reports/" + this.getClass().getSimpleName() + ".xls");


    }

    public static void main(String[] args) throws Exception {
        ConditionalStyleReportTest2 test = new ConditionalStyleReportTest2();
        test.test();
    }
}