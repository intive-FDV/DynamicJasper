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

package ar.com.fdvs.dj.domain;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
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
	private Boolean visible = Boolean.TRUE;

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

	public String getTextForValueFormatterExpression(String variableName, List<DJCrosstabMeasure> measures) {
       String stringExpression = null;
        String fieldsMap = ExpressionUtils.getTextForFieldsFromScriptlet();
        String parametersMap = ExpressionUtils.getTextForParametersFromScriptlet();
        String variablesMap = ExpressionUtils.getTextForVariablesFromScriptlet();
        if (this.getValueFormatter() instanceof DjBaseMMValueFormatter){

            DjBaseMMValueFormatter formatter = DjBaseMMValueFormatter.class.cast(this.getValueFormatter());
            String[] propertyMeasures = new String[measures.size()];

            int i = 0;
            for (DJCrosstabMeasure measure : measures) {
                propertyMeasures[i] = measure.getProperty().getProperty();
                i++;
            }

            formatter.setPropertyMeasures(propertyMeasures);

            String rowValuesExp = "new Object[]{";
            int measureIdx=0;
            for (Iterator iterator = measures.iterator(); iterator.hasNext(); measureIdx++) {
                DJCrosstabMeasure djmeasure = (DJCrosstabMeasure) iterator.next();

                if (djmeasure.getProperty()== null){
                    continue;
                }

                rowValuesExp += "$V{" +djmeasure.getMeasureIdentifier(measureIdx) +"}";
                rowValuesExp += ", ";
            }

            //chop the last comma
            rowValuesExp = rowValuesExp.substring(0,rowValuesExp.length()-2);
            rowValuesExp += "}";
            stringExpression = "((("+DjBaseMMValueFormatter.class.getName()+")$P{crosstab-measure__"+variableName+"_vf}).evaluate( "
                        + rowValuesExp + ", " + fieldsMap +", " + variablesMap + ", " + parametersMap +" ))";
       }
        else {

             stringExpression = "((("+DJValueFormatter.class.getName()+")$P{crosstab-measure__"+variableName+"_vf}).evaluate( "
                    + "$V{"+variableName+"}, " + fieldsMap +", " + variablesMap + ", " + parametersMap +" ))";
        }

        logger.debug("Expression for crosstab DJValueFormatter = " + stringExpression);

		return stringExpression;
	}

    public String getMeasureIdentifier(int idx) {
        String measurePrefix = this.getMeasurePrefix(idx);
        String measureProperty = measurePrefix + this.getProperty().getProperty();
        return measureProperty;
    }

    public String getMeasurePrefix(int idx) {
        return "idx" + idx + "_";
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


    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
