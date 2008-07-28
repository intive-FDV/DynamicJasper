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
