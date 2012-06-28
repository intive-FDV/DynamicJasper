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

package ar.com.fdvs.dj.core.layout;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * Simple Layout Manager recommended when we want to get a ready to operate </br>
 * Excel plain list from the report.</br>
 * </br>
 * Groups and many style options will be ignored.
 */
public class ListLayoutManager extends AbstractLayoutManager {

	private static final Log log = LogFactory.getLog(ListLayoutManager.class);
	
	protected Map referencesMap = new HashMap();

	public Map getReferencesMap() {
		return referencesMap;
	}	

	protected void startLayout() {
		getReport().getOptions().setColumnsPerPage(new Integer(1));
		getReport().getOptions().setColumnSpace(new Integer(0));
		getDesign().setColumnCount(1);
		getDesign().setColumnWidth(getReport().getOptions().getColumnWidth());
		super.startLayout();
		if (getReport().getOptions().isPrintColumnNames()){
			generateHeaderBand();
		}
		getDesign().setIgnorePagination(getReport().getOptions().isIgnorePagination());
	}

	protected void transformDetailBandTextField(AbstractColumn column, JRDesignTextField textField) {
		log.debug("transforming detail band text field...");
		textField.setPrintRepeatedValues(true);
		try {
			//if we have a java.lang.Number then the pattern must be ignored in order to let Excel recognize the number correctly.
			if (Number.class.isAssignableFrom(Class.forName(textField.getExpression().getValueClassName())))
				textField.setPattern(null);
		} catch (ClassNotFoundException e) {
			throw new LayoutException(e.getMessage(),e);
		}
	}

	protected void generateHeaderBand() {
		log.debug("generating header band...");
		JRDesignBand header = (JRDesignBand) getDesign().getColumnHeader();
		if (header == null) {
			header = new JRDesignBand();
			getDesign().setColumnHeader(header);
		}
//		if (!DynamicJasperHelper.existsGroupWithColumnNames(getReport().getColumnsGroups()))
			generateHeaderBand(header);
	}
}
