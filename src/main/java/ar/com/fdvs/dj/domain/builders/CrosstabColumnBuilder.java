package ar.com.fdvs.dj.domain.builders;

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.Style;

public class CrosstabColumnBuilder {
	
	DJCrosstabColumn column = new DJCrosstabColumn();
	
	public DJCrosstabColumn build(){
		return this.column;
	}
	
	public CrosstabColumnBuilder setProperty(String property, String className){
		column.setProperty(new ColumnProperty(property,className));
		return this;
	}
	public CrosstabColumnBuilder setHeaderHeight(int height){
		column.setHeaderHeight(height);
		return this;
	}
	public CrosstabColumnBuilder setWidth(int width){
		column.setWidth(width);
		return this;
	}
	public CrosstabColumnBuilder setTitle(String title){
		column.setTitle(title);
		return this;
	}
	public CrosstabColumnBuilder setShowTotals(boolean showTotal){
		column.setShowTotals(showTotal);
		return this;
	}
	public CrosstabColumnBuilder setTotalStyle(Style style){
		column.setTotalStyle(style);
		return this;
	}
	public CrosstabColumnBuilder setTotalHeaderStyle(Style style){
		column.setTotalHeaderStyle(style);
		return this;
	}
	public CrosstabColumnBuilder setHeaderStyle(Style style){
		column.setHeaderStyle(style);
		return this;
	}

}
