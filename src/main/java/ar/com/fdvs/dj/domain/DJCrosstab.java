package ar.com.fdvs.dj.domain;

import java.util.ArrayList;
import java.util.List;

import ar.com.fdvs.dj.test.CalculatedColumnReportTest;

public class DJCrosstab {
	
	private List rows = new ArrayList();
	private List columns = new ArrayList();
	private List measures = new ArrayList();
	
	private boolean showColumnGroupTotals = false;
	private boolean showRowGroupTotals = false;
	
	private int height;
	private int width;
	
	private Style headerStyle;
	
	public Style getHeaderStyle() {
		return headerStyle;
	}
	public void setHeaderStyle(Style headerStyle) {
		this.headerStyle = headerStyle;
	}
	public boolean isShowColumnGroupTotals() {
		return showColumnGroupTotals;
	}
	public void setShowColumnGroupTotals(boolean showColumnGroupTotals) {
		this.showColumnGroupTotals = showColumnGroupTotals;
	}
	public boolean isShowRowGroupTotals() {
		return showRowGroupTotals;
	}
	public void setShowRowGroupTotals(boolean showRowGroupTotals) {
		this.showRowGroupTotals = showRowGroupTotals;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public List getColumns() {
		return columns;
	}
	public void setColumns(List columns) {
		this.columns = columns;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public List getMeasures() {
		return measures;
	}
	public void setMeasures(List measures) {
		this.measures = measures;
	}
	
	public DJCrosstabMeasure getMeasure(int index) {
		return (DJCrosstabMeasure) measures.get(index);
	}	

}
