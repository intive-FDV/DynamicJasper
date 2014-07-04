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

import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class ColumnSpanOutOfBoudsTest extends TestCase {

    public void testOutOfBoundsColspan() throws Exception {

        FastReportBuilder frb = new FastReportBuilder();

        //4 Cols
        frb.addColumn("State", "state", String.class.getName(), 30)
                    .addColumn("Branch", "branch", String.class.getName(), 30)
                    .addColumn("Item", "item", String.class.getName(), 50)
                    .addColumn("Amount", "amount", Float.class.getName(), 60, true)
                    .setTitle("November " + Calendar.getInstance().get(Calendar.YEAR) + " sales report")
                    .setSubtitle("This report was generated at " + new Date())
                    .setColumnsPerPage(1, 10)
                    .setUseFullPageWidth(true);

        try {
            //Try to get 5 of 4
            frb.setColspan(1,5, "");

        } catch (ColumnBuilderException e) {
            return;
        }

        fail();
    }

    public void testColumnSpanStartsAfterTheLastColumn() {
        try {
            FastReportBuilder frb = new FastReportBuilder();
            frb.setColspan(1, 2, "Estimated");
            frb.build();
        } catch (ColumnBuilderException e) {
            //This should be OK
            return;
        }

        fail();
    }
}
