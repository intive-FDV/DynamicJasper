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

package ar.com.fdvs.dj.domain.builders;

import java.util.Iterator;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.CrossTabColorShema;
import ar.com.fdvs.dj.core.layout.LayoutException;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.DJCrosstabMeasure;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.DJLabel;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;

public class CrosstabBuilder {

	private DJCrosstab crosstab = new DJCrosstab();
	
	int cellHeight = -1;
	int cellWidth = -1;
	int columnHeaderHeight = -1;
	int rowHeaderWidth = -1;
	
	
	private final int DEFAULT_ROW_HEADER_WIDTH = 90;
	private final int DEFAULT_COLUMN_HEADER_HEIGHT = 25;
	private final int DEFAULT_CELL_HEIGHT = 20;
	private final int DEFAULT_CELL_WIDTH = 90;
	
	/**
	 * Build the crosstab. Throws LayoutException if anything is wrong
	 * @return
	 */
	public DJCrosstab build(){
		if (crosstab.getMeasures().isEmpty()){
			throw new LayoutException("Crosstabs must have at least one measure");
		}
		if (crosstab.getColumns().isEmpty()){
			throw new LayoutException("Crosstabs must have at least one column");
		}
		if (crosstab.getRows().isEmpty()){
			throw new LayoutException("Crosstabs must have at least one row");
		}
		
		
		//Ensure default dimension values
		for (Iterator iterator = crosstab.getColumns().iterator(); iterator.hasNext();) {
			DJCrosstabColumn col = (DJCrosstabColumn) iterator.next();
			
			if (col.getWidth() == -1 && cellWidth != -1)
				col.setWidth(cellWidth);

			if (col.getWidth() == -1 )
				col.setWidth(DEFAULT_CELL_WIDTH);
			
			if (col.getHeaderHeight() == -1 && columnHeaderHeight != -1)
				col.setHeaderHeight(columnHeaderHeight);
			
			if (col.getHeaderHeight() == -1)
				col.setHeaderHeight(DEFAULT_COLUMN_HEADER_HEIGHT);
		}			
		
		for (Iterator iterator = crosstab.getRows().iterator(); iterator.hasNext();) {
			DJCrosstabRow row = (DJCrosstabRow) iterator.next();
			
			if (row.getHeight() == -1 && cellHeight != -1)
				row.setHeight(cellHeight);

			if (row.getHeight() == -1 )
				row.setHeight(DEFAULT_CELL_HEIGHT);

			if (row.getHeaderWidth() == -1 && rowHeaderWidth != -1)
				row.setHeaderWidth(rowHeaderWidth);
			
			if (row.getHeaderWidth() == -1)
				row.setHeaderWidth(DEFAULT_ROW_HEADER_WIDTH);			
		}		
		
		return crosstab;
	}

	/**
	 * The height if the whole corsstab. This just ensures a minimun height in case the crosstab results
	 * shorter that the height specified.
	 * 
	 * Height is not taken into account if the crosstab must grow because of its data
	 * 
	 * @param height
	 * @return
	 */
	public CrosstabBuilder setHeight(int height) {
		crosstab.setHeight(height);
		return this;
	}

	public CrosstabBuilder setWidth(int width) {
		crosstab.setWidth(width);
		return this;
	}
	public CrosstabBuilder setHeaderStyle(Style headerStyle) {
		crosstab.setHeaderStyle(headerStyle);
		return this;
	}
	public CrosstabBuilder setDatasource(String expression, int origin, int type) {
		DJDataSource datasource = new DJDataSource(expression,origin,type);
		crosstab.setDatasource(datasource);
		return this;
	}
	public CrosstabBuilder setDatasource(String expression, int origin, int type, boolean preSorted) {
		DJDataSource datasource = new DJDataSource(expression,origin,type);
		datasource.setPreSorted(preSorted);
		crosstab.setDatasource(datasource);
		return this;
	}
	
	/**
	 * To use main report datasource. There should be nothing else in the detail band
	 * @param preSorted
	 * @return
	 */
	public CrosstabBuilder useMainReportDatasource(boolean preSorted) {
		DJDataSource datasource = new DJDataSource("ds",DJConstants.DATA_SOURCE_ORIGIN_REPORT_DATASOURCE,DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE);
		datasource.setPreSorted(preSorted);
		crosstab.setDatasource(datasource);
		return this;
	}
	public CrosstabBuilder setUseFullWidth(boolean useFullWidth) {
		crosstab.setUseFullWidth(useFullWidth);
		return this;
	}
	public CrosstabBuilder setCellBorder(Border cellBorder) {
		crosstab.setCellBorder(cellBorder);
		return this;
	}
	
	
	/**
	 * Adds a measure to the crosstab. A crosstab can have many measures. DJ will lay out one measure above
	 * the other.
	 * 
	 * A measure is what is shown on each intersection of a column and a row. A calculation is performed to 
	 * all occurrences in the datasource where the column and row values matches (between elements) 
	 * 
	 * @param property
	 * @param className
	 * @param operation
	 * @param title
	 * @param style
	 * @return
	 */
	public CrosstabBuilder addMeasure(String property, String className, DJCalculation operation, String title, Style style) {
		DJCrosstabMeasure measure = new DJCrosstabMeasure(property,className, operation , title);
		measure.setStyle(style);
		crosstab.getMeasures().add(measure);
		return this;
	}

	public CrosstabBuilder addMeasure(DJCrosstabMeasure measure) {
		crosstab.getMeasures().add(measure);
		return this;
	}
	
	/**
	 * Add a row to the crosstab. In a double entry "X\Y" like table  table, rows are "X" (columns are Y) 
	 * The first row added will be the inner most one.  
	 * @param row
	 * @return
	 */
	public CrosstabBuilder addRow(DJCrosstabRow row) {
		crosstab.getRows().add(row);
		return this;
	}

	/**
	 * Add a column to the crosstab. In a double entry "X\Y" like table  table, columns are "Y" (rows are X) 
	 * The first column added will be the inner most one.  
	 * @param col
	 * @return
	 */
	public CrosstabBuilder addColumn(DJCrosstabColumn col) {
		crosstab.getColumns().add(col);
		return this;
	}
	public CrosstabBuilder setColorScheme(int colorScheme) {
		crosstab.setColorScheme(colorScheme);
		return this;
	}
	public CrosstabBuilder setColorScheme(CrossTabColorShema colorScheme) {
		crosstab.setCtColorScheme(colorScheme);
		return this;
	}
	public CrosstabBuilder setMainHeaderTitle(String title) {
		crosstab.setMainHeaderTitle(title);
		crosstab.setAutomaticTitle(false);
		return this;
	}
	public CrosstabBuilder setAutomaticTitle(boolean bool) {
		crosstab.setAutomaticTitle(bool);
		return this;
	}
	public CrosstabBuilder setBottomSpace(int bottomSpace) {
		crosstab.setBottomSpace(bottomSpace);
		return this;
	}
	public CrosstabBuilder setTopSpace(int topSpace) {
		crosstab.setTopSpace(topSpace);
		return this;
	}

	/**
	 * {@linkplain CrosstabBuilder#addColumn(DJCrosstabColumn)}
	 * @param title
	 * @param property
	 * @param className
	 * @param showTotal
	 * @return
	 */
	public CrosstabBuilder addColumn(String title, String property, String className, boolean showTotal) {
		DJCrosstabColumn col = new CrosstabColumnBuilder()
			.setProperty(property,className)
			.setShowTotals(showTotal)
			.setTitle(title)
			.build();
		addColumn(col);
		return this;
	}
	
	/**
	 * {@linkplain CrosstabBuilder#addColumn(DJCrosstabColumn)}
	 * @param title
	 * @param property
	 * @param className
	 * @param showTotal
	 * @param headerStyle
	 * @param totalStyle
	 * @param totalHeaderStyle
	 * @return
	 */
	public CrosstabBuilder addColumn(String title, String property, String className, boolean showTotal,
			Style headerStyle, Style totalStyle, Style totalHeaderStyle) {
		DJCrosstabColumn col = new CrosstabColumnBuilder()
		.setProperty(property,className)
		.setShowTotals(showTotal)
		.setTitle(title)
		.setHeaderStyle(headerStyle)
		.setTotalHeaderStyle(totalHeaderStyle)
		.setTotalStyle(totalStyle)
		.build();
		addColumn(col);
		return this;
	}
	
	/**
	 * {@linkplain CrosstabBuilder#addRow(DJCrosstabRow)}
	 * @param title
	 * @param property
	 * @param className
	 * @param showTotal
	 * @return
	 */
	public CrosstabBuilder addRow(String title, String property, String className, boolean showTotal) {
		DJCrosstabRow row = new CrosstabRowBuilder()
			.setProperty(property,className)
			.setShowTotals(showTotal)
			.setTitle(title)
			.build();
		addRow(row);
		return this;
	}
	
/**
 * {@linkplain CrosstabBuilder#addRow(DJCrosstabRow)}
 * @param title
 * @param property
 * @param className
 * @param showTotal
 * @param headerStyle
 * @param totalStyle
 * @param totalHeaderStyle
 * @return
 */
	public CrosstabBuilder addRow(String title, String property, String className, boolean showTotal,
			Style headerStyle, Style totalStyle, Style totalHeaderStyle) {
		DJCrosstabRow row = new CrosstabRowBuilder()
		.setProperty(property,className)
		.setShowTotals(showTotal)
		.setTitle(title)
		.setHeaderStyle(headerStyle)
		.setTotalHeaderStyle(totalHeaderStyle)
		.setTotalStyle(totalStyle)
		.build();
		addRow(row);
		return this;
	}

	/**
	 * Should be called after all rows have been created 
	 * @param headerStyle
	 * @param totalStyle
	 * @param totalHeaderStyle
	 * @return
	 */
	public CrosstabBuilder setRowStyles(Style headerStyle, Style totalStyle, Style totalHeaderStyle) {
		crosstab.setRowHeaderStyle(headerStyle);
		crosstab.setRowTotalheaderStyle(totalHeaderStyle);
		crosstab.setRowTotalStyle(totalStyle);
		return this;
	}

	/**
	 * Should be called after all columns have been created
	 * @param headerStyle
	 * @param totalStyle
	 * @param totalHeaderStyle
	 * @return
	 */
	public CrosstabBuilder setColumnStyles(Style headerStyle, Style totalStyle, Style totalHeaderStyle) {
		crosstab.setColumnHeaderStyle(headerStyle);
		crosstab.setColumnTotalheaderStyle(totalHeaderStyle);
		crosstab.setColumnTotalStyle(totalStyle);
		return this;
	}

	public CrosstabBuilder setCellWidth(int width) {
		this.cellWidth = width;
		return this;
	}
	public CrosstabBuilder setColumnHeaderHeight(int height) {
		this.columnHeaderHeight = height;
		return this;
	}
	public CrosstabBuilder setCellDimension(int height,int width) {
		setCellHeight(height);
		setCellWidth(width);
		return this;
	}
	public CrosstabBuilder setCellHeight(int height) {
		this.cellHeight = height;
		return this;
	}
	public CrosstabBuilder setRowHeaderWidth(int width) {
		this.rowHeaderWidth = width;
		return this;
	}

	public CrosstabBuilder setCaption(DJLabel caption) {
		crosstab.setCaption(caption);
		return this;
	}

}
