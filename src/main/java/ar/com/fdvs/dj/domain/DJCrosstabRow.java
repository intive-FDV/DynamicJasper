package ar.com.fdvs.dj.domain;

public class DJCrosstabRow {
	
	private ColumnProperty property;
	private int height;
	private int headerWidth;
	
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
	
}
