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

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.constants.DJVariableIncrementType;
import ar.com.fdvs.dj.domain.constants.DJVariableResetType;

public class DJVariable extends DJBaseElement {
	
	public DJVariable(){

	}

	public DJVariable(String name, String className, DJCalculation calculation, CustomExpression expression) {
		super();
		this.name = name;
		this.className = className;
		this.calculation = calculation;
		this.expression = expression;
	}
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private String name;
	private String className;
	private DJCalculation calculation = DJCalculation.NOTHING;
	
	private DJGroup resetGroup;
	private DJVariableResetType resetType = DJVariableResetType.REPORT;
	
	private DJGroup incrementGroup;
	private DJVariableIncrementType incrementType = DJVariableIncrementType.NONE;
	
	private CustomExpression expression;
	private CustomExpression initialValueExpression;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public DJCalculation getCalculation() {
		return calculation;
	}
	public void setCalculation(DJCalculation calculation) {
		this.calculation = calculation;
	}
	public CustomExpression getExpression() {
		return expression;
	}
	public void setExpression(CustomExpression expression) {
		this.expression = expression;
	}
	public CustomExpression getInitialValueExpression() {
		return initialValueExpression;
	}
	public void setInitialValueExpression(CustomExpression initialValueExpression) {
		this.initialValueExpression = initialValueExpression;
	}
	public DJGroup getResetGroup() {
		return resetGroup;
	}
	public void setResetGroup(DJGroup resetGroup) {
		this.resetGroup = resetGroup;
	}
	public void setResetType(DJVariableResetType resetType) {
		this.resetType = resetType;
	}
	public DJVariableResetType getResetType() {
		return resetType;
	}
	public DJGroup getIncrementGroup() {
		return incrementGroup;
	}
	public void setIncrementGroup(DJGroup incrementGroup) {
		this.incrementGroup = incrementGroup;
	}
	public DJVariableIncrementType getIncrementType() {
		return incrementType;
	}
	public void setIncrementType(DJVariableIncrementType incrementType) {
		this.incrementType = incrementType;
	}
	
}
