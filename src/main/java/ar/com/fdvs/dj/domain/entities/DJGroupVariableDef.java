/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
