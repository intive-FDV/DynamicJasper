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

import net.sf.jasperreports.engine.JRVariable;
import ar.com.fdvs.dj.domain.entities.columns.OperationColumn;

/**
 * Operations that can be shown as a group variable.</br>
 * </br>
 * @see OperationColumn
 */
public class ColumnsGroupVariableOperation {

	public static ColumnsGroupVariableOperation AVERAGE = new ColumnsGroupVariableOperation( JRVariable.CALCULATION_AVERAGE);
	public static ColumnsGroupVariableOperation COUNT = new ColumnsGroupVariableOperation( JRVariable.CALCULATION_COUNT);
	public static ColumnsGroupVariableOperation FIRST = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_FIRST);
	public static ColumnsGroupVariableOperation HIGHEST = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_HIGHEST);
	public static ColumnsGroupVariableOperation LOWEST = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_LOWEST);
	public static ColumnsGroupVariableOperation NOTHING = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_NOTHING);
	public static ColumnsGroupVariableOperation STANDARD_DEVIATION = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_STANDARD_DEVIATION);
	public static ColumnsGroupVariableOperation SUM = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_SUM);
	public static ColumnsGroupVariableOperation SYSTEM = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_SYSTEM);
	public static ColumnsGroupVariableOperation VARIANCE = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_VARIANCE);
	public static ColumnsGroupVariableOperation DISTINCT_COUNT = new ColumnsGroupVariableOperation(JRVariable.CALCULATION_DISTINCT_COUNT);

	private byte value;

	private ColumnsGroupVariableOperation(byte value){
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

}
