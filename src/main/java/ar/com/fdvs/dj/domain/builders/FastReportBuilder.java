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

package ar.com.fdvs.dj.domain.builders;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

/**
 * Builder created to give users a friendly way of creating a DynamicReport.</br>
 * </br>
 * Usage example: </br>
 * DynamicReportBuilder drb = new DynamicReportBuilder();
 * Integer margin = new Integer(20);
 * drb.addTitle("Clients List").addTitleStyle(titleStyle)
 * .addSubtitle("Clients without debt")
 * .addDetailHeight(new Integer(15))
 * .addLeftMargin(margin).addRightMargin(margin).addTopMargin(margin)
 * .addBottomMargin(margin)
 * .addPrintBackgroundOnOddRows(true).addOddRowBackgroundStyle(oddRowStyle)
 * .addColumnsPerPage(new Integer(1)).addColumnSpace(new Integer(5))
 * .addColumn(column1).addColumn(column2).build();
 * </br>
 * Like with all DJ's builders, it's usage must end with a call to build() mehtod.
 * </br>
 */
public class FastReportBuilder extends DynamicReportBuilder {

	Style currencyStyle;
	Style numberStyle;
	Style subtitleStyle;

	protected int groupCount = 0;

	public FastReportBuilder(){
		currencyStyle = new Style();
		currencyStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		numberStyle = new Style();
		numberStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		Style defaultHeaderStyle = options.getDefaultHeaderStyle();
		defaultHeaderStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		defaultHeaderStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		defaultHeaderStyle.setBorderBottom(Border.DOTTED);
		defaultHeaderStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		defaultHeaderStyle.setBackgroundColor(Color.LIGHT_GRAY);
		defaultHeaderStyle.setTransparency(Transparency.OPAQUE);
		
		Style titleStyle2 = report.getTitleStyle();
		titleStyle2.setFont(Font.ARIAL_BIG_BOLD);
		titleStyle2.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle2.setVerticalAlign(VerticalAlign.TOP);
	}

	public DynamicReport build(){

		//build the groups
		for (int i = 0; i < groupCount; i++) {
			GroupBuilder gb = new GroupBuilder();
			PropertyColumn col = (PropertyColumn) report.getColumns().get(i);
			gb.addCriteriaColumn(col);
			report.getColumnsGroups().add(gb.build());
		}

		return super.build();
	}


	public FastReportBuilder addColumn(String title, String property, String className, int width) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getInstance()
			.addColumnProperty(new ColumnProperty(property, className))
			.addWidth(new Integer(width))
			.addTitle(title)
			.build();

		guessStyle(className, column);

		addColumn(column);

		return this;
	}

	public FastReportBuilder addColumn(String title, String property, String className, int width, boolean fixedWidth) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getInstance()
		.addColumnProperty(new ColumnProperty(property, className))
		.addWidth(new Integer(width))
		.addTitle(title)
		.addFixedWidth(Boolean.valueOf(fixedWidth))
		.build();
		
		guessStyle(className, column);
		
		addColumn(column);
		
		return this;
	}

	private void guessStyle(String className, AbstractColumn column) throws ClassNotFoundException {
		Class clazz = Class.forName(className);
		if (BigDecimal.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)) {
			column.setPattern("$ #.00");
			column.setStyle(currencyStyle);
		}

		if (Integer.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)) {
			column.setStyle(numberStyle);
		}

		if (Date.class.isAssignableFrom(clazz)) {
			column.setPattern("dd/MM/yy");
		}

		if (Timestamp.class.isAssignableFrom(clazz)) {
			column.setPattern("dd/MM/yy hh:mm:ss");
		}
	}

	public DynamicReportBuilder addGroups(int numgroups) {
		groupCount = numgroups;
		return this;
	}

	public FastReportBuilder addResourceBundle(String resourceBundle) {
		report.setResourceBundle(resourceBundle);
		return this;
	}



}