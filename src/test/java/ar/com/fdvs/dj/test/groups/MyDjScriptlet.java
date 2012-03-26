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

package ar.com.fdvs.dj.test.groups;

import java.util.Map;

import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.fill.JRFillVariable;

import org.apache.log4j.Logger;

import ar.com.fdvs.dj.core.DJDefaultScriptlet;

/**
 * It is very important to extend {@link DJDefaultScriptlet} because it is needed for the normal operation of the Report.
 * @author mamana
 *
 */
public class MyDjScriptlet extends DJDefaultScriptlet {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MyDjScriptlet.class);
	private Map precalculatedValues;

	public void afterGroupInit(String groupName) throws JRScriptletException {		
		super.afterGroupInit(groupName);
		
		String currentGroupValue = (String) getFieldValue("state");
		
		logger.debug("afterGroupInit [" + groupName + "] = " + currentGroupValue);
		
		/**
		 * Recover JasperReport variable (the holder for the calculated value)
		 * The naming convention used in DJ is: variable-<[header|footer]>_<grouped column property>_<column with the variable>
		 * As an example: variable-header_state_quantity stands for: a header variable placed in the group for the column "state" 
		 * which calculates in the column "quantity"
		 */
		JRFillVariable var = (JRFillVariable) variablesMap.get("variable-header_state_quantity");
		
		/**
		 * Here We get the precalculated value for the current group
		 */
		Object precalculatedValue = getPrecalculatedValue("state", currentGroupValue);
		
		var.setValue(precalculatedValue);
		
	}

	private Object getPrecalculatedValue(String fieldName, String currentValue) {
		return precalculatedValues.get(currentValue);
	}

	public void setPrecalculatedValues(Map precalculateValues) {
		this.precalculatedValues = precalculateValues;
		
	}

}
