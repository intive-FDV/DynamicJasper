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

package ar.com.fdvs.dj.test.colspan;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Date;

public class ColumnsSpanTest extends BaseDjReportTest {

    /**
     * Creates the DynamicReportBuilder and sets the basic options for
     * the report
     */
    public DynamicReport buildReport() throws Exception {

        FastReportBuilder frb = new FastReportBuilder();

        frb.addColumn("State", "state", String.class.getName(), 30)
                .addColumn("Branch", "branch", String.class.getName(), 30)
                .addColumn("Item", "item", String.class.getName(), 50)
                .addColumn("Amount", "amount", Float.class.getName(), 60, true)
                .addGroups(2)
                .setTitle("November " + getYear() +" sales report")
                .setSubtitle("This report was generated at " + new Date())
                .setColumnsPerPage(1, 10)
                .setUseFullPageWidth(true)
                .setColspan(1, 2, "Estimated");

        DynamicReport dynamicReport = frb.build();
        dynamicReport.getOptions().getDefaultHeaderStyle().setBorder(Border.PEN_1_POINT());

        return dynamicReport;
    }

    public static void main(String[] args) throws Exception {
        ColumnsSpanTest test = new ColumnsSpanTest();
        test.testReport();
        JasperViewer.viewReport(test.jp);
    }

}
