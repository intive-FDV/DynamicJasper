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

package ar.com.fdvs.dj.test.encoding;


import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.ReportExporter;
import ar.com.fdvs.dj.test.domain.Product;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DefaultEncodingReportTest extends BaseDjReportTest {

    public static final String PDF_FONT_ENCODING = "ISO-8859-2";

    /**
     * For a list of supported encodings: http://docs.oracle.com/javase/6/docs/technotes/guides/intl/encoding.doc.html
     */


    public DynamicReport buildReport() throws Exception {


        Style titleStyle = Style.createBlankStyle("title");
        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
        Style headerStyle = Style.createBlankStyle("headerStyle");
        Style detailStyle = Style.createBlankStyle("detailStyle");

        String encoding = PDF_FONT_ENCODING;

        log.debug("ENCODING IS: " + encoding);

        titleStyle.setFont(new Font(18, "Arial", true));
        subtitleStyle.setFont(new Font(14, "Arial", false));
        detailStyle.setFont(new Font(10, "Arial", false));

        /*
          Creates the DynamicReportBuilder and sets the basic options for
          the report
         */
        FastReportBuilder drb = new FastReportBuilder();
        drb.addColumn("State", "state", String.class.getName(), 30)
                .addColumn("Branch", "branch", String.class.getName(), 30)
                .addColumn("Product Line", "productLine", String.class.getName(), 50)
                .addColumn("Item", "item", String.class.getName(), 50)
                .addColumn("Item Code", "id", Long.class.getName(), 30, true)
                .addColumn("Quantity", "quantity", Long.class.getName(), 60, true)
                .addColumn("Amount", "amount", Float.class.getName(), 70, true)
                .addGroups(2)
                .setTitle("Report with Polish letters")
                .setSubtitle("ĄĆ\tĘ\tŁ\tŃ\tÓ\tŚ\tŹ\tŻ\tą\tć\tę\tł\tń\tó\tś\tź\tż")
                .setPrintBackgroundOnOddRows(true)
                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                .setDefaultEncoding(encoding)
                .setUseFullPageWidth(true);

        drb.addGlobalFooterVariable(drb.getColumn(4), DJCalculation.COUNT, null, new DJValueFormatter() {

            public String getClassName() {
                return String.class.getName();
            }


            public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
                return (value == null ? "0" : value.toString()) + " Clients";
            }
        });


        DynamicReport dr = drb.build();

        return dr;
    }

    public static Collection getDummyCollection() {

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("dd/MM/yyyy");

        List col = new ArrayList();

        //The collection is ordered by State, Branch and Product Line
        col.add(new Product(1L, "book", "ąćęłńóśźż", "abcdefghaijkslmnopqĄĆĘŁŃÓŚŹŻĄĆĘŁŃÓŚŹŻĄĆĘŁŃÓŚŹŻĄĆĘŁŃÓŚŹŻĄĆĘŁŃÓŚŹŻĄĆĘŁ", "Main Street", new Long("2500"), new Float("5")));
        col.add(new Product(1L, "book", "áñö", "ĄĆĘŁŃÓŚŹŻ", "Main Street", new Long("2500"), new Float("5")));
        return col;
    }

    @Override
    protected void exportReport() throws Exception {
        ReportExporter.exportReport(jp, System.getProperty("user.dir") + "/target/reports/" + this.getClass().getName() + ".pdf");
        ReportExporter.exportReportXls(jp, System.getProperty("user.dir") + "/target/reports/" + this.getClass().getName() + ".xls");
        exportToJRXML();
    }

    @Override
    protected JRDataSource getDataSource() {
        return new JRBeanCollectionDataSource(getDummyCollection());
    }

    public static void main(String[] args) throws Exception {
        DefaultEncodingReportTest test = new DefaultEncodingReportTest();
        test.testReport();
        test.exportToJRXML();
        JasperViewer.viewReport(test.jp);    //finally display the report report
        JasperDesignViewer.viewReportDesign(test.jr);
    }

}
