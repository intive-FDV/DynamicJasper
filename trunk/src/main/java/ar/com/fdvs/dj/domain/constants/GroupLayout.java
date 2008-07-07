/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
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

package ar.com.fdvs.dj.domain.constants;

public class GroupLayout {

	/**
	 * When true, the column headers will be printed
	 */
	private boolean printHeaders;

	/**
	 * If true, the value will be printed in an isolated line.
	 * If printHeaders is true, the headers will be shown below the current value
	 */
	private boolean showValueInHeader;

	/**
	 * If true, the value of column used as group criteria will be shown
	 * for each row.
	 */
	private boolean showValueForEachRow;

	/**
	 * used with showValueInHeader, if both are true, the column name will
	 * be printed before the value, i.e: columnX: Value y
	 */
	private boolean showColumnName;

	/**
	 * If true, the column of this group will not be printed
	 */
	private boolean hideColumn;

	public GroupLayout(boolean showValueInHeader,
			boolean showValueForEach,
			boolean showColumnName,
			boolean hideColumn,
			boolean printHeaders)
	{
		this.showValueInHeader = showValueInHeader;
		this.showValueForEachRow = showValueForEach;
		this.showColumnName = showColumnName;
		this.hideColumn = hideColumn;
		this.printHeaders = printHeaders;
	}

	public boolean isShowColumnName() {
		return showColumnName;
	}

	public boolean isShowValueForEachRow() {
		return showValueForEachRow;
	}

	public boolean isShowValueInHeader() {
		return showValueInHeader;
	}

	public static GroupLayout VALUE_IN_HEADER_AND_FOR_EACH 				= new GroupLayout(true,true,false,false,false);
	public static GroupLayout VALUE_IN_HEADER_AND_FOR_EACH_WITH_HEADERS = new GroupLayout(true,true,false,false,true);

	public static GroupLayout VALUE_IN_HEADER 								= new GroupLayout(true,false,false,true,false);
	public static GroupLayout VALUE_IN_HEADER_WITH_HEADERS 					= new GroupLayout(true,false,false,true,true);
	public static GroupLayout VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME 	= new GroupLayout(true,false,true,true,true);

	public static GroupLayout VALUE_FOR_EACH 				= new GroupLayout(false,true,false,false,false);
	public static GroupLayout VALUE_FOR_EACH_WITH_HEADERS   = new GroupLayout(false,true,false,false,true);

	public static GroupLayout EMPTY = new GroupLayout(false,false,false,true,false);

	public static GroupLayout DEFAULT = new GroupLayout(true,false,false,false,false);
	public static GroupLayout DEFAULT_WITH_HEADER = new GroupLayout(true,false,false,false,true);

	public boolean isHideColumn() {
		return hideColumn;
	}

	public void setHideColumn(boolean hideColumn) {
		this.hideColumn = hideColumn;
	}

	public boolean isPrintHeaders() {
		return printHeaders;
	}

}
