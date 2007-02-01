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

package ar.com.fdvs.dj.domain.entities;

import java.util.ArrayList;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

/**
 * Entity created to handle groups of columns.</br>
 * Multiple groups can be created for a single report. In this case the result </br>
 * would be a nesting with the latest groups added to the report being the inner ones.
 */
public class ColumnsGroup extends Entity {

	//The column used to group by
	private PropertyColumn columnToGroupBy;

	public PropertyColumn getColumnToGroupBy() {
		return columnToGroupBy;
	}

	public void setColumnToGroupBy(PropertyColumn columnToGroupBy) {
		this.columnToGroupBy = columnToGroupBy;
	}

	//<ColumnsGroupVariable>
	private ArrayList headerVariables = new ArrayList();
	//<ColumnsGroupVariable>
	private ArrayList footerVariables = new ArrayList();
	private Integer headerHeight = new Integer(20);
	private Integer footerHeight = new Integer(20);
	private GroupLayout layout = GroupLayout.VALUE_IN_HEADER;

	public ArrayList getFooterVariables() {
		return footerVariables;
	}

	public void setFooterVariables(ArrayList footerVariables) {
		this.footerVariables = footerVariables;
	}

	public ArrayList getHeaderVariables() {
		return headerVariables;
	}

	public void setHeaderVariables(ArrayList headerVariables) {
		this.headerVariables = headerVariables;
	}

	public Integer getFooterHeight() {
		return footerHeight;
	}

	public void setFooterHeight(Integer footerHeight) {
		this.footerHeight = footerHeight;
	}

	public Integer getHeaderHeight() {
		return headerHeight;
	}

	public void setHeaderHeight(Integer headerHeight) {
		this.headerHeight = headerHeight;
	}

	public GroupLayout getLayout() {
		return layout;
	}

	public void setLayout(GroupLayout layout) {
		this.layout = layout;
	}

}
