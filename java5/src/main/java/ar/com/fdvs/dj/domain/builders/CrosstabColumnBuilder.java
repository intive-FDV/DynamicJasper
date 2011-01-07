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
	public CrosstabColumnBuilder setTotalLegend(String legend){
		column.setTotalLegend(legend);
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
