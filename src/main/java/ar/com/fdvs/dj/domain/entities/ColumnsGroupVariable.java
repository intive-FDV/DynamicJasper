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

package ar.com.fdvs.dj.domain.entities;

import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * Entity used to handle global and group variables that represent the value of </br>
 * an operation applied to the corresponding rows.</br>
 * </br>
 * @see DJCalculation
 */
public class ColumnsGroupVariable implements Entity {

	private AbstractColumn columnToApplyOperation;
	private DJCalculation operation;
	private Style style;

	public ColumnsGroupVariable(AbstractColumn columnToApplyOperation, DJCalculation operation) {
		this.columnToApplyOperation = columnToApplyOperation;
		this.operation = operation;
	}

	public ColumnsGroupVariable(AbstractColumn columnToApplyOperation, DJCalculation operation, Style style) {
		this.columnToApplyOperation = columnToApplyOperation;
		this.operation = operation;
		this.style = style;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}


	public AbstractColumn getColumnToApplyOperation() {
		return columnToApplyOperation;
	}

	public void setColumnToApplyOperation(AbstractColumn columnToApplyOperation) {
		this.columnToApplyOperation = columnToApplyOperation;
	}

	public DJCalculation getOperation() {
		return operation;
	}

	public void setOperation(DJCalculation operation) {
		this.operation = operation;
	}

}
