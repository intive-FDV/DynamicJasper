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

package ar.com.fdvs.dj.core.registration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;

/**
 * Manager invoked to register conditional styles. </br>
 * A ConditionalStyle is read and it's expression is registered. </br>
 * </br>
 * @see DJGroup
 */
public class ConditionalStylesRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(ConditionalStylesRegistrationManager.class);
	private String columnName;
	int counter = 0;

	public ConditionalStylesRegistrationManager(DynamicJasperDesign jd,  DynamicReport dr, String columnName, LayoutManager layoutManager) {
		super(jd,dr,layoutManager);
		this.columnName = columnName;
	}

	protected void registerEntity(Entity entity) {
		//The column names are already registered when we get here
		ConditionalStyle condition = (ConditionalStyle) entity;
		String expressionName = columnName + "_style_" + counter;
        log.debug("registering conditional style " + expressionName);
		condition.setName(expressionName);
		registerCustomExpressionParameter(expressionName, condition.getCondition());
		counter++;
	}

	protected Object transformEntity(Entity entity) {
		return null;
	}

}
