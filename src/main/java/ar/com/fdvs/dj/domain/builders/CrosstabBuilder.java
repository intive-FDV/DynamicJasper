package ar.com.fdvs.dj.domain.builders;

import java.util.Iterator;

import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.DJCrosstabMeasure;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;

public class CrosstabBuilder {

	private DJCrosstab crosstab = new DJCrosstab();
	
	public DJCrosstab build(){
		return crosstab;
	}
	
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
	public CrosstabBuilder setUseFullWidth(boolean useFullWidth) {
		crosstab.setUseFullWidth(useFullWidth);
		return this;
	}
	public CrosstabBuilder setCellBorder(Border cellBorder) {
		crosstab.setCellBorder(cellBorder);
		return this;
	}
	public CrosstabBuilder addMeasure(String property, String className, ColumnsGroupVariableOperation operation, String title, Style style) {
		DJCrosstabMeasure measure = new DJCrosstabMeasure(property,className, operation , title);
		measure.setStyle(style);
		crosstab.getMeasures().add(measure);
		return this;
	}
	
	public CrosstabBuilder addRow(DJCrosstabRow row) {
		crosstab.getRows().add(row);	
		return this;
	}
	
	public CrosstabBuilder addColumn(DJCrosstabColumn col) {
		crosstab.getColumns().add(col);
		return this;
	}
	public CrosstabBuilder setColorScheme(int colorScheme) {
		crosstab.setColorScheme(colorScheme);
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

	public CrosstabBuilder addColumn(String title, String property, String className, boolean showTotal) {
		DJCrosstabColumn col = new CrosstabColumnBuilder()
			.setProperty(property,className)
			.setShowTotals(showTotal)
			.setTitle(title)
			.build();
		addColumn(col);
		return this;
	}
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
	public CrosstabBuilder addRow(String title, String property, String className, boolean showTotal) {
		DJCrosstabRow row = new CrosstabRowBuilder()
			.setProperty(property,className)
			.setShowTotals(showTotal)
			.setTitle(title)
			.build();
		addRow(row);
		return this;
	}
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
	
	public CrosstabBuilder setRowStyles(Style headerStyle, Style totalStyle, Style totalHeaderStyle) {
		for (Iterator iterator = crosstab.getRows().iterator(); iterator.hasNext();) {
			DJCrosstabRow row = (DJCrosstabRow) iterator.next();
			row.setHeaderStyle(headerStyle);
			row.setTotalHeaderStyle(totalHeaderStyle);
			row.setTotalStyle(totalStyle);
		}
		return this;		
	}
	
	public CrosstabBuilder setColumnStyles(Style headerStyle, Style totalStyle, Style totalHeaderStyle) {
		for (Iterator iterator = crosstab.getColumns().iterator(); iterator.hasNext();) {
			DJCrosstabColumn col = (DJCrosstabColumn) iterator.next();
			col.setHeaderStyle(headerStyle);
			col.setTotalHeaderStyle(totalHeaderStyle);
			col.setTotalStyle(totalStyle);
		}		
		return this;		
	}
	
	public CrosstabBuilder setCellWidth(int width) {
		for (Iterator iterator = crosstab.getColumns().iterator(); iterator.hasNext();) {
			DJCrosstabColumn col = (DJCrosstabColumn) iterator.next();
			col.setWidth(width);
		}
		return this;
	}
	public CrosstabBuilder setColumnHeaderHeight(int height) {
		for (Iterator iterator = crosstab.getColumns().iterator(); iterator.hasNext();) {
			DJCrosstabColumn col = (DJCrosstabColumn) iterator.next();
			col.setHeaderHeight(height);
		}
		return this;
	}
	public CrosstabBuilder setCellDimension(int height,int width) {
		setCellHeight(height);
		setCellWidth(width);
		return this;
	}
	public CrosstabBuilder setCellHeight(int height) {
		for (Iterator iterator = crosstab.getRows().iterator(); iterator.hasNext();) {
			DJCrosstabRow row = (DJCrosstabRow) iterator.next();
			row.setHeight(height);
		}
		return this;
	}
	public CrosstabBuilder setRowHeaderWidth(int width) {
		for (Iterator iterator = crosstab.getRows().iterator(); iterator.hasNext();) {
			DJCrosstabRow row = (DJCrosstabRow) iterator.next();
			row.setHeaderWidth(width);
		}
		return this;
	}
	
}
