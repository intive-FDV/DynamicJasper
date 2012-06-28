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

package ar.com.fdvs.dj.domain.customexpression;

import java.util.Map;

import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.domain.CustomExpression;

public class DJSimpleExpression implements CustomExpression {

	private static final long serialVersionUID = 1L;
	
	public static final byte TYPE_FIELD 	= 0;
	public static final byte TYPE_VARIABLE 	= 1;
	public static final byte TYPE_PARAMATER	= 2;
	
	private String variableName;
	private String className;
	private byte type;
	
	public DJSimpleExpression(byte type, String variableName, String className){
		this.variableName=variableName;
		this.className = className;
		this.type = type;
	}

	public Object evaluate(Map fields, Map variables, Map parameters) {
		throw new DJException("This method should have not been called");
	}

	public String getClassName() {
		return className;
	}

	public String getVariableName() {
		return variableName;
	}

	public byte getType() {
		return type;
	}

}
