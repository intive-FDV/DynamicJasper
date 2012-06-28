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

package ar.com.fdvs.dj.core;

import java.util.Map;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.fill.JRFillGroup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class handles parameter passing to custom expressions in runtime (during report fill)
 * @author mamana
 *
 */
public class DJDefaultScriptlet extends JRDefaultScriptlet  {
	
	int veces = 0;
	public DJDefaultScriptlet(){
		super();
//		logger.debug("Im a new Scrptlet  " + this);
	}
	
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DJDefaultScriptlet.class);
	
	protected static final String VARS_KEY = "vars";
	protected static final String PARAMS_KEY = "params";
	protected static final String FIELDS_KEY = "fields";
	
	protected FieldMapWrapper fieldMapWrapper = new FieldMapWrapper();
	protected ParameterMapWrapper parameterMapWrapper = new ParameterMapWrapper();
	protected VariableMapWrapper variableMapWrapper = new VariableMapWrapper();

	public void setData(Map parsm, Map fldsm, Map varsm, JRFillGroup[] grps) {
		super.setData(parsm, fldsm, varsm, grps);
		
		putValuesInMap();
	}
	
	protected void putValuesInMap() {
		fieldMapWrapper.setMap(this.fieldsMap);
		parameterMapWrapper.setMap(this.parametersMap);
		variableMapWrapper.setMap(this.variablesMap);
	}

	public Map getCurrentFiels() {
		return fieldMapWrapper; 
	}

	public Map getCurrentParams() {
		return parameterMapWrapper;
	}

	public Map getCurrentVariables() {
		return variableMapWrapper;
	}

}
