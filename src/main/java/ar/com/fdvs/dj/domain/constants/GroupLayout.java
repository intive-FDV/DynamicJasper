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

package ar.com.fdvs.dj.domain.constants;

public class GroupLayout {

	private boolean showValueInHeader;
	private boolean showValueForEach;
	private boolean showColumnNames;

	public GroupLayout(boolean showValueInHeader, boolean showValueForEach, boolean showColumnNames) {
		super();
		this.showValueInHeader = showValueInHeader;
		this.showValueForEach = showValueForEach;
		this.showColumnNames = showColumnNames;
	}

	public boolean isShowColumnNames() {
		return showColumnNames;
	}

	public boolean isShowValueForEach() {
		return showValueForEach;
	}

	public boolean isShowValueInHeader() {
		return showValueInHeader;
	}

	public static GroupLayout VALUE_IN_HEADER_AND_FOR_EACH_WITH_COLNAMES = new GroupLayout(true,true,true);
	public static GroupLayout VALUE_IN_HEADER_AND_FOR_EACH = new GroupLayout(true,true,false);
	public static GroupLayout VALUE_IN_HEADER_WITH_COLNAMES = new GroupLayout(true,false,true);
	public static GroupLayout VALUE_IN_HEADER = new GroupLayout(true,false,false);
	public static GroupLayout VALUE_FOR_EACH = new GroupLayout(false,true,false);
	public static GroupLayout VALUE_FOR_EACH_WITH_COLNAMES = new GroupLayout(false,true,true);
	public static GroupLayout EMPTY = new GroupLayout(false,false,false);

}
