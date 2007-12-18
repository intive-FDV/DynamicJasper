package ar.com.fdvs.dj.domain.builders;

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.Style;

public class CrosstabRowBuilder {
	
	DJCrosstabRow row = new DJCrosstabRow();
	
	public DJCrosstabRow build(){
		return this.row;
	}
	
/**
 * 		DJCrosstabRow row = new DJCrosstabRow();
		row.setProperty(new ColumnProperty("productLine",String.class.getName()));
		row.setHeaderWidth(100);
		row.setHeight(30);
		row.setTitle("Product Line my mother teressa");
		row.setShowTotals(true);
		row.setTotalStyle(totalStyle);
		row.setTotalHeaderStyle(totalHeader);
		row.setHeaderStyle(colAndRowHeaderStyle);
 * @param property
 * @param className
 * @return
 */	
	
	public CrosstabRowBuilder setProperty(String property, String className){
		row.setProperty(new ColumnProperty(property,className));
		return this;
	}
	public CrosstabRowBuilder setHeaderWidth(int height){
		row.setHeaderWidth(height);
		return this;
	}
	public CrosstabRowBuilder setHeight(int height){
		row.setHeight(height);
		return this;
	}
	public CrosstabRowBuilder setTitle(String title){
		row.setTitle(title);
		return this;
	}
	public CrosstabRowBuilder setShowTotals(boolean showTotal){
		row.setShowTotals(showTotal);
		return this;
	}
	public CrosstabRowBuilder setTotalStyle(Style style){
		row.setTotalStyle(style);
		return this;
	}
	public CrosstabRowBuilder setTotalHeaderStyle(Style style){
		row.setTotalHeaderStyle(style);
		return this;
	}
	public CrosstabRowBuilder setHeaderStyle(Style style){
		row.setHeaderStyle(style);
		return this;
	}

}
