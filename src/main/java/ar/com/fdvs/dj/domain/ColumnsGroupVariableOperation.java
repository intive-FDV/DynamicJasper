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

package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.entities.columns.OperationColumn;

/**
 * Operations that can be shown as a group variable.</br>
 * </br>
 * @see OperationColumn
 */
public class ColumnsGroupVariableOperation {

	public static ColumnsGroupVariableOperation AVERAGE = new ColumnsGroupVariableOperation( (byte) 3);
	public static ColumnsGroupVariableOperation COUNT = new ColumnsGroupVariableOperation( (byte) 1);
	public static ColumnsGroupVariableOperation FIRST = new ColumnsGroupVariableOperation((byte) 9);
	public static ColumnsGroupVariableOperation HIGHEST = new ColumnsGroupVariableOperation((byte) 5);
	public static ColumnsGroupVariableOperation LOWEST = new ColumnsGroupVariableOperation((byte) 4);
	public static ColumnsGroupVariableOperation NOTHING = new ColumnsGroupVariableOperation((byte) 0);
	public static ColumnsGroupVariableOperation STANDARD_DEVIATION = new ColumnsGroupVariableOperation((byte) 6);
	public static ColumnsGroupVariableOperation SUM = new ColumnsGroupVariableOperation((byte)2);
	public static ColumnsGroupVariableOperation SYSTEM = new ColumnsGroupVariableOperation((byte) 8);
	public static ColumnsGroupVariableOperation VARIANCE = new ColumnsGroupVariableOperation((byte) 7);

	private byte value;

	private ColumnsGroupVariableOperation(byte value){
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

}
