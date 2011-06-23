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

package ar.com.fdvs.dj.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;


public class DJCrosstabMeasure extends DJBaseElement {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DJCrosstabMeasure.class);

	/**
	 * Logger for this class
	 */

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private ColumnProperty property;
	private DJCalculation operation;
	private String title;
	private List conditionalStyles = new ArrayList();
	private DJValueFormatter valueFormatter;
	private DJCRosstabMeasurePrecalculatedTotalProvider precalculatedTotalProvider;
	private Boolean isPercentage = Boolean.FALSE;

	private Style style;
	
	private DJHyperLink link;

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public List getConditionalStyles() {
		return conditionalStyles;
	}

	public void setConditionalStyles(List conditionalStyles) {
		this.conditionalStyles = conditionalStyles;
	}
	
	public DJCrosstabMeasure(String propertyName, String className, DJCalculation operation, String title) {
		super();
		this.property = new ColumnProperty(propertyName, className);
		this.operation = operation;
		this.title = title;
	}

	public DJCrosstabMeasure(ColumnProperty measure, DJCalculation operation, String title) {
		super();
		this.property = measure;
		this.operation = operation;
		this.title = title;
	}

	public DJCrosstabMeasure() {
		super();
	}

	public ColumnProperty getProperty() {
		return property;
	}
	public void setProperty(ColumnProperty measure) {
		this.property = measure;
	}
	public DJCalculation getOperation() {
		return operation;
	}
	public void setOperation(DJCalculation operation) {
		this.operation = operation;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public DJHyperLink getLink() {
		return link;
	}

	public void setLink(DJHyperLink link) {
		this.link = link;
	}

	public DJValueFormatter getValueFormatter() {
		return valueFormatter;
	}

	public void setValueFormatter(DJValueFormatter valueFormatter) {
		this.valueFormatter = valueFormatter;
	}

	public String getTextForValueFormatterExpression(String variableName) {
		
		String fieldsMap = ExpressionUtils.getTextForFieldsFromScriptlet();
		String parametersMap = ExpressionUtils.getTextForParametersFromScriptlet();
		String variablesMap = ExpressionUtils.getTextForVariablesFromScriptlet();		
		
		String stringExpression = "((("+DJValueFormatter.class.getName()+")$P{crosstab-measure__"+variableName+"_vf}).evaluate( "
			+ "$V{"+variableName+"}, " + fieldsMap +", " + variablesMap + ", " + parametersMap +" ))";
		

		logger.debug("Expression for crosstab DJValueFormatter = " + stringExpression);

		return stringExpression;
	}

	public DJCRosstabMeasurePrecalculatedTotalProvider getPrecalculatedTotalProvider() {
		return precalculatedTotalProvider;
	}

	public void setPrecalculatedTotalProvider(
			DJCRosstabMeasurePrecalculatedTotalProvider precalculatedTotalProvider) {
		this.precalculatedTotalProvider = precalculatedTotalProvider;
	}

	public Boolean getIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	
}
