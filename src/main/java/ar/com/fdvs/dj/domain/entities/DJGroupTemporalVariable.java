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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public class DJGroupTemporalVariable implements Entity {
	
	private static final Log log = LogFactory.getLog(DJGroupTemporalVariable.class);

	private String name;
	private AbstractColumn columnToApplyOperation;
	private DJCalculation operation;
	
	public DJGroupTemporalVariable(String name, AbstractColumn columnToApplyOperation, DJCalculation operation) {
		this.name = name;
		this.columnToApplyOperation = columnToApplyOperation;
		this.operation = operation;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Log getLog() {
		return log;
	}
}
