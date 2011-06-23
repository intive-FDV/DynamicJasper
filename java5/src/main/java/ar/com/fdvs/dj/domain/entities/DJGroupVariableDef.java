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

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * This class is meant for variable registration purposes only.
 * No graphical elements come out from this  (compared to DJGroupVariable)
 * 
 * a DJGroupVariableDef is later a registered as a JRVariable which can be referenced upon
 * its name.
 * 
 * @author mamana
 *
 */
public class DJGroupVariableDef implements Entity {
	
	private static final Log log = LogFactory.getLog(DJGroupVariableDef.class);

	private String name;
	private AbstractColumn columnToApplyOperation;
	private ColumnProperty columnProperty;
	private DJCalculation operation;
	
	public DJGroupVariableDef(String name, AbstractColumn columnToApplyOperation, DJCalculation operation) {
		this.name = name;
		this.columnToApplyOperation = columnToApplyOperation;
		this.operation = operation;
	}
	
	public DJGroupVariableDef(String name, ColumnProperty columnProperty, DJCalculation operation) {
		this.name = name;
		this.columnProperty = columnProperty;
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

	public ColumnProperty getColumnProperty() {
		return columnProperty;
	}

	public void setColumnProperty(ColumnProperty columnProperty) {
		this.columnProperty = columnProperty;
	}
}
