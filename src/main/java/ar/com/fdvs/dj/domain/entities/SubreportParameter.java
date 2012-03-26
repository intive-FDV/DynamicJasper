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

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DJBaseElement;

public class SubreportParameter extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private String name;
	private String expression;
	private String className;

	/**
	 * Subreport parameters are objects from the main report. In the main report
	 * they can come from field values, parameters or variables.<br/>
	 * Use {@link DJConstants}.SUBREPORT_PARAM_ORIGIN_FIELD,<br/>
	 * {@link DJConstants}.SUBREPORT_PARAM_ORIGIN_PARAMETER or <br/>
	 * {@link DJConstants}.SUBREPORT_PARAM_ORIGIN_VARIABLE  to specify it.
	 */
	private int parameterOrigin = DJConstants.SUBREPORT_PARAM_ORIGIN_FIELD;
	
	public int getParameterOrigin() {
		return parameterOrigin;
	}

	public void setParameterOrigin(int parameterOrigin) {
		this.parameterOrigin = parameterOrigin;
	}

	public SubreportParameter() {
		super();
	}
	
	
	
	public SubreportParameter(String name, String expression, String className, int parameterOrigin) {
		super();
		this.name = name;
		this.expression = expression;
		this.className = className;
		this.parameterOrigin = parameterOrigin;
	}

	public SubreportParameter(String name, String expression, String className) {
		super();
		this.name = name;
		this.expression = expression;
		this.className = className;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
