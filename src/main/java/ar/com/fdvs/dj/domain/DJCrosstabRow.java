package ar.com.fdvs.dj.domain;

public class DJCrosstabRow {
	
	private ColumnProperty property;
	private int height;
	private int headerWidth;
	
	private String title;
	
	private boolean showTotals = false;
	
	private Style totalStyle;
	private Style totalHeaderStyle;
	private Style headerStyle;
	
	public Style getTotalStyle() {
		return totalStyle;
	}
	public void setTotalStyle(Style totalStyle) {
		this.totalStyle = totalStyle;
	}
	public boolean isShowTotals() {
		return showTotals;
	}
	public void setShowTotals(boolean showTotals) {
		this.showTotals = showTotals;
	}
	public ColumnProperty getProperty() {
		return property;
	}
	public void setProperty(ColumnProperty property) {
		this.property = property;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getHeaderWidth() {
		return headerWidth;
	}
	public void setHeaderWidth(int headerWidth) {
		this.headerWidth = headerWidth;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Style getTotalHeaderStyle() {
		return totalHeaderStyle;
	}
	public void setTotalHeaderStyle(Style titleStyle) {
		this.totalHeaderStyle = titleStyle;
	}
	public Style getHeaderStyle() {
		return headerStyle;
	}
	public void setHeaderStyle(Style headerStyle) {
		this.headerStyle = headerStyle;
	}
	
}
