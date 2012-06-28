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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.GlobalGroupColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.ExpressionUtils;

/**
 * Manager invoked to register groups of columns. A ColumnsGroup is read and </br>
 * transformed into a JRDesignGroup.</br>
 * </br>
 * @see DJGroup
 */
public class DJGroupRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(DJGroupRegistrationManager.class);

	public DJGroupRegistrationManager(DynamicJasperDesign jd, DynamicReport dr, LayoutManager layoutManager) {
		super(jd,dr,layoutManager);
	}

	protected void registerEntity(Entity entity) {
		DJGroup djgroup = (DJGroup) entity;
		try {
            //Set Group Name
            if (djgroup.getName() == null) {
                PropertyColumn column = djgroup.getColumnToGroupBy();
                String prefix = this.getDjd().getName() + "_";
                int groupIndex = getDynamicReport().getColumnsGroups().indexOf(djgroup);
                int columnIndex = getDynamicReport().getColumns().indexOf(djgroup.getColumnToGroupBy());
                if (column instanceof GlobalGroupColumn){
                    djgroup.setName(prefix + "global_column_" + groupIndex);
                } else {
                    djgroup.setName(prefix + "group["+groupIndex+"]_for_column_" + columnIndex + "-" +  column.getName());
                }
            }


            log.debug("registering group " + djgroup.getName());

            JRDesignGroup group = (JRDesignGroup)transformEntity(djgroup);
			getDjd().addGroup(group);
			//Variables are registered right after the group where they belong.
			String property = djgroup.getColumnToGroupBy().getColumnProperty().getProperty();
			
			if (djgroup.getFooterLabel() != null && djgroup.getFooterLabel().getLabelExpression() != null) {
				registerCustomExpressionParameter(group.getName() + "_labelExpression", djgroup.getFooterLabel().getLabelExpression());
			}

//			ColumnsGroupFieldVariablesRegistrationManager fieldVariablesRM = new ColumnsGroupFieldVariablesRegistrationManager(getDjd(),getDynamicReport(),getLayoutManager(), group);
//			fieldVariablesRM.registerEntities(djgroup.getFieldVariables());
			
			DJGroupVariableDefRegistrationManager variablesRM = new DJGroupVariableDefRegistrationManager(getDjd(),getDynamicReport(),getLayoutManager(), group);
			variablesRM.registerEntities(djgroup.getVariables());
			
			ColumnsGroupVariablesRegistrationManager headerVariablesRM = new ColumnsGroupVariablesRegistrationManager(ColumnsGroupVariablesRegistrationManager.HEADER, property, getDjd(),getDynamicReport(),getLayoutManager());
			headerVariablesRM.registerEntities(djgroup.getHeaderVariables());
			
			ColumnsGroupVariablesRegistrationManager footerVariablesRM = new ColumnsGroupVariablesRegistrationManager(ColumnsGroupVariablesRegistrationManager.FOOTER, property, getDjd(),getDynamicReport(),getLayoutManager());
			footerVariablesRM.registerEntities(djgroup.getFooterVariables());
			
			DJCrosstabRegistrationManager headerCrosstabsRm = new DJCrosstabRegistrationManager(ColumnsGroupVariablesRegistrationManager.HEADER, getDjd(),getDynamicReport(),getLayoutManager());
			headerCrosstabsRm.registerEntities(djgroup.getHeaderCrosstabs());
			
			DJCrosstabRegistrationManager footerCrosstabsRm = new DJCrosstabRegistrationManager(ColumnsGroupVariablesRegistrationManager.FOOTER, getDjd(),getDynamicReport(),getLayoutManager());
			footerCrosstabsRm.registerEntities(djgroup.getFooterCrosstabs());
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
	}

	//PropertyColumn only can be used for grouping (not OperationColumn)
	protected Object transformEntity(Entity entity) throws JRException {
		//log.debug("transforming group...");
		DJGroup djgroup = (DJGroup) entity;
		PropertyColumn column = djgroup.getColumnToGroupBy();
		JRDesignGroup group = new JRDesignGroup();


		group.setName(djgroup.getName());
		
		getLayoutManager().getReferencesMap().put(group.getName(), djgroup);

		group.setCountVariable(new JRDesignVariable());
		group.setGroupFooter(new JRDesignBand());
		group.setGroupHeader(new JRDesignBand());

		JRDesignExpression jrExpression = new JRDesignExpression();
		
		CustomExpression expressionToGroupBy = column.getExpressionToGroupBy();
		
		if (expressionToGroupBy != null) { //new in 3.0.7-b5
			useVariableForCustomExpression(group, jrExpression, expressionToGroupBy);
			
		} else {
			if (column instanceof ExpressionColumn){
				ExpressionColumn col = (ExpressionColumn)column;
				CustomExpression customExpression = col.getExpression();
				useVariableForCustomExpression(group, jrExpression, customExpression);
			} else {
				jrExpression.setText(column.getTextForExpression());
				jrExpression.setValueClassName(column.getValueClassNameForExpression());
			}
		}
		

		group.setExpression(jrExpression);

		return group;
	}

	/**
	 * When a group expression gets its value from a CustomExpression, a variable must be used otherwise it will fail
	 * to work as expected.<br><br>
	 * 
	 * Instead of using: GROUP -> CUSTOM_EXPRESSION<br>
	 * <br>
	 * we use: GROUP -> VARIABLE -> CUSTOM_EXPRESSION<br>
	 * <br><br>
	 * See http://jasperforge.org/plugins/mantis/view.php?id=4226 for more detail
	 * 
	 * @param group
	 * @param jrExpression
	 * @param customExpression
	 * @throws JRException
	 */
	protected void useVariableForCustomExpression(JRDesignGroup group,
			JRDesignExpression jrExpression, CustomExpression customExpression)
			throws JRException {
		//1) Register CustomExpression object as a parameter
		String expToGroupByName = group.getName() + "_custom_expression";
		registerCustomExpressionParameter(expToGroupByName, customExpression);
		
		//2) Create a variable which is calculated through the custom expression
		JRDesignVariable gvar = new JRDesignVariable();
		String varName = group.getName() + "_variable_for_group_expression";
		gvar.setName(varName);
		gvar.setCalculation(JRDesignVariable.CALCULATION_NOTHING);
		gvar.setValueClassName(customExpression.getClassName());
		
		String expText = ExpressionUtils.createCustomExpressionInvocationText(customExpression, expToGroupByName);
		JRDesignExpression gvarExp = new JRDesignExpression();
		gvarExp.setValueClassName(customExpression.getClassName());
		gvarExp.setText(expText);
		gvar.setExpression(gvarExp);
		getDjd().addVariable(gvar);
		
		//3) Make the group expression point to the variable
		jrExpression.setText("$V{"+varName+"}");
		jrExpression.setValueClassName(customExpression.getClassName());
		log.debug("Expression for CustomExpression usgin variable = \"" + varName + "\" which point to: " + expText);
	}



}
