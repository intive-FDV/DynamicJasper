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


import ar.com.fdvs.dj.core.FieldMapWrapper;
import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJVariable;
import ar.com.fdvs.dj.domain.entities.container.CustomExpressionElement;
import ar.com.fdvs.dj.domain.entities.container.ElementContainer;
import ar.com.fdvs.dj.domain.entities.container.ImageElement;
import ar.com.fdvs.dj.domain.entities.container.TextElement;
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
import java.util.Map;

public class ElementsInGroupReportTest extends BaseDjReportTest2 {

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
            .addFooterVariable(1,4,DJCalculation.SUM,null)
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
            .setPrintColumnNames(false)
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

        DJVariable myVar = new DJVariable("myVar", Float.class.getName(), DJCalculation.SUM, new CustomExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return fields.get("amount");
            }

            @Override
            public String getClassName() {
                return Float.class.getName();
            }
        });

        myVar.setResetGroup(dr.getColumnsGroups().get(0));
        myVar.setInitialValueExpression(new CustomExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return 0f;
            }

            @Override
            public String getClassName() {
                return Float.class.getName();
            }
        });

        dr.getVariables().add(myVar);

        Style elementStyle = Style.createBlankStyle("textElement");
        elementStyle.setBorder(Border.PEN_1_POINT());
        elementStyle.getBorder().setColor(Color.BLUE);
        elementStyle.setStretchWithOverflow(true);
        elementStyle.setPadding(3);

        DJGroup aGroup = dr.getColumnsGroups().get(0);
        aGroup.setLayout(GroupLayout.DEFAULT_WITH_HEADER);

        ElementContainer elementContainer1 = new ElementContainer();
//        CustomExpression ce = new StringExpression() {
//            @Override
//            public Object evaluate(Map fields, Map variables, Map parameters) {
//                return "This is a value returned from a CustomExpression for group \"state\", in the headr. Current value is: <b>"+fields.get("state")+"</b> ";
//            }
//        };
//        elementContainer1.add(new CustomExpressionElement(ce,30,false,elementStyle));
//        aGroup.addHeaderElement(elementContainer1);

        ElementContainer elementContainer2 = new ElementContainer();
        elementContainer2.add(new TextElement("Text 1",30,false,elementStyle));
        elementContainer2.add(new TextElement("Text 2",30,false,elementStyle));
        aGroup.addHeaderElement(elementContainer2);


        ElementContainer elementContainer = new ElementContainer();
        elementContainer.add(new TextElement("Text 1",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 2",30,false,elementStyle));
        elementContainer.add(new TextElement("Text 3",30,false,elementStyle));
        aGroup.addHeaderElement(elementContainer);

        elementContainer = new ElementContainer();
        CustomExpression cef = new StringExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                FieldMapWrapper fieldMapWrapper = (FieldMapWrapper) fields;
                return "This is a value returned from a CustomExpression for group \"state\" in the footer," +
                        " current value is: <b>"+fieldMapWrapper.getJRFillField("state").getOldValue()+"</b> ";
            }

        };
        elementContainer.add(new CustomExpressionElement(cef,90,false,elementStyle));


        CustomExpression cefSum = new StringExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                FieldMapWrapper fieldMapWrapper = (FieldMapWrapper) fields;
                return "Total amount is: "+ variables.get("myVar");
            }

        };
        elementContainer.add(new CustomExpressionElement(cefSum,30,false,elementStyle));

        aGroup.addFooterElement(elementContainer);

        elementContainer = new ElementContainer();
        elementContainer.add(new TextElement(""));
        aGroup.addFooterElement(elementContainer);




        elementContainer = new ElementContainer();
        String path = System.getProperty("user.dir") + "/target/test-classes/images/logo_fdv_solutions_60.jpg";
        elementContainer.add(new ImageElement(path, 197, 60, ImageScaleMode.FILL_PROPORTIONALLY, HorizontalAlign.LEFT));
        aGroup.addHeaderElement(elementContainer);

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
		ElementsInGroupReportTest test = new ElementsInGroupReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
