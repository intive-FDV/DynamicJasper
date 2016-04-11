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
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.entities.container.ElementContainer;
import ar.com.fdvs.dj.domain.entities.container.ImageElement;
import ar.com.fdvs.dj.domain.entities.container.TextElement;
import ar.com.fdvs.dj.domain.entities.container.CustomExpressionElement;
import ar.com.fdvs.dj.test.BaseDjReportTest2;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			//.setColumnsPerPage(2,10)
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

        Style elementStyle = Style.createBlankStyle("textElement");
        elementStyle.setBorder(Border.PEN_1_POINT());
        elementStyle.getBorder().setColor(Color.BLUE);
        elementStyle.setStretchWithOverflow(true);
        elementStyle.setPadding(3);

        ElementContainer elementContainer1 = new ElementContainer();
        elementContainer1.add(new TextElement("Text 1",30,false,elementStyle));
        dr.getTitleElements().add(elementContainer1);

        ElementContainer elementContainer2 = new ElementContainer();
        elementContainer2.add(new TextElement("Text 1",30,false,elementStyle));
        elementContainer2.add(new TextElement("Text 2",30,false,elementStyle));
        dr.getTitleElements().add(elementContainer2);


        ElementContainer elementContainer = new ElementContainer();
        elementContainer.add(new TextElement("Text 1",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 2",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 3",30,false,elementStyle));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new TextElement("Text 4",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 5",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 6",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 7",30,false,elementStyle));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new TextElement("Text 4",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 5",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 6",30,false,elementStyle));
        elementContainer.add(new TextElement("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 7",30,false,elementStyle));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new TextElement("Text 4",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 5",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 6",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 7",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 8",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 9",30,false,elementStyle));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new TextElement("Text 4",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 5",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 6",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 7 (fixed)",200, true,elementStyle));
        elementContainer.add(new TextElement("Text 8",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 9",30,false,elementStyle));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new TextElement("<b>field 1:</b>",40, true, elementStyle));
        TextElement element1 = new TextElement("Value 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 00 11 22", 50, true, elementStyle);
        elementContainer.add(element1);
        elementContainer.add(new TextElement("Text 6",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 7",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 8",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 9",30,false,elementStyle));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        String path = System.getProperty("user.dir") + "/target/test-classes/images/logo_fdv_solutions_60.jpg";
        elementContainer.add(new ImageElement(path, 197, 60, ImageScaleMode.FILL_PROPORTIONALLY, HorizontalAlign.LEFT));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new ImageElement(path, 197, 60, ImageScaleMode.FILL_PROPORTIONALLY, HorizontalAlign.CENTER));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new ImageElement(path, 197, 60, ImageScaleMode.FILL_PROPORTIONALLY, HorizontalAlign.RIGHT));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new ImageElement(path, 197, 60, ImageScaleMode.FILL, HorizontalAlign.RIGHT));
        dr.getTitleElements().add(elementContainer);

        elementContainer = new ElementContainer();
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(path)));
        elementContainer.add(new ImageElement(new FileInputStream(new File(path)), 197, 60, ImageScaleMode.FILL, HorizontalAlign.CENTER));
        dr.getTitleElements().add(elementContainer);

		return dr;
	}

    @Override
    protected void handleDesign(DynamicJasperDesign djd) {
        super.handleDesign(djd);

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
