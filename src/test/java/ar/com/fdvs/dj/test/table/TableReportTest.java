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

package ar.com.fdvs.dj.test.table;


import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.entities.container.ElementContainer;
import ar.com.fdvs.dj.domain.entities.container.StaticTextElement;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.BaseDjReportTest2;
import ar.com.fdvs.dj.util.LayoutUtils;
import ar.com.fdvs.dj.util.Utils;
import net.sf.jasperreports.components.table.*;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.convert.ReportConverter;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import org.xml.sax.Attributes;

import java.awt.*;
import java.util.Date;

public class TableReportTest extends BaseDjReportTest2 {

	public DynamicReport buildReport() throws Exception {


		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addColumn("Item", "item", String.class.getName(),50)
			.addColumn("Amount", "amount", Float.class.getName(),60,true)
			.addGroups(2)
			.setTitle("November" + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setColumnsPerPage(2,10)
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

        ElementContainer elementContainer = new ElementContainer();
        elementContainer.add(new StaticTextElement("Texto 1"));
        elementContainer.add(new StaticTextElement("Texto 2"));
        elementContainer.add(new StaticTextElement("Texto 3"));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new StaticTextElement("Texto 4"));
        elementContainer.add(new StaticTextElement("Texto 5"));
        elementContainer.add(new StaticTextElement("Texto 6"));
        elementContainer.add(new StaticTextElement("Texto 7"));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new StaticTextElement("Texto 4"));
        elementContainer.add(new StaticTextElement("Texto 5"));
        elementContainer.add(new StaticTextElement("Texto 6"));
        elementContainer.add(new StaticTextElement("Texto 7"));
        elementContainer.add(new StaticTextElement("Texto 8"));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new StaticTextElement("Texto 4"));
        elementContainer.add(new StaticTextElement("Texto 5"));
        elementContainer.add(new StaticTextElement("Texto 6"));
        elementContainer.add(new StaticTextElement("Texto 7"));
        elementContainer.add(new StaticTextElement("Texto 8"));
        elementContainer.add(new StaticTextElement("Texto 9"));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new StaticTextElement("Texto 4"));
        elementContainer.add(new StaticTextElement("Texto 5"));
        elementContainer.add(new StaticTextElement("Texto 6"));
        StaticTextElement element = new StaticTextElement("Texto 7");
        element.setFixedWidth(false);
        element.setWidth(200);
        elementContainer.add(element);
        elementContainer.add(new StaticTextElement("Texto 8"));
        elementContainer.add(new StaticTextElement("Texto 9"));
        dr.getTitleElements().add(elementContainer);

		return dr;
	}

    @Override
    protected void handleDesign(DynamicJasperDesign djd) {
        super.handleDesign(djd);

        if (true)
            return;

        JRDesignBand header = (JRDesignBand) djd.getPageHeader();
        int voffset = LayoutUtils.findVerticalOffset(header);

        JRDesignFrame frame = new JRDesignFrame();

        frame.setY(voffset+1);
        frame.setWidth(dr.getOptions().getPrintableWidth());
        frame.setHeight(200);

        JRDesignStaticText staticText = createStaticText("texto!");
        staticText.setWidth(80);
        staticText.setHeight(30);
        frame.addElement(staticText);
        frame.setBackcolor(Color.CYAN);
        frame.setMode(ModeEnum.OPAQUE);

        header.addElement(frame);
        header.setHeight(voffset+200+1);

    }

    private JRDesignStaticText createStaticText(String s) {
        JRDesignStaticText dst = new JRDesignStaticText();
        dst.setText(s);
        return dst;
    }

    public static void main(String[] args) throws Exception {
		TableReportTest test = new TableReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
