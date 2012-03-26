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

package ar.com.fdvs.dj.core.registration;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.design.*;

import net.sf.jasperreports.engine.type.CalculationEnum;
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
		log.debug("registering group...");
		DJGroup djgroup = (DJGroup) entity;
		try {
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
		log.debug("transforming group...");
		DJGroup djgroup = (DJGroup) entity;
		PropertyColumn column = djgroup.getColumnToGroupBy();
		JRDesignGroup group = new JRDesignGroup();

		if (djgroup.getName() == null) {
			int groupIndex = getDynamicReport().getColumnsGroups().indexOf(djgroup);
			int columnIndex = getDynamicReport().getColumns().indexOf(djgroup.getColumnToGroupBy());
			if (column instanceof GlobalGroupColumn){
				djgroup.setName("global_column_" + groupIndex);
			} else {
				djgroup.setName( "group["+groupIndex+"]_for_column_" + columnIndex + "-" +  column.getName());
			}			
		}
		group.setName(djgroup.getName());
		
		getLayoutManager().getReferencesMap().put(group.getName(), djgroup);

		group.setCountVariable(new JRDesignVariable());

        JRDesignSection gfs = (JRDesignSection) group.getGroupFooterSection();
        gfs.getBandsList().add(new JRDesignBand());


        JRDesignSection ghs = (JRDesignSection) group.getGroupHeaderSection();
        ghs.getBandsList().add(new JRDesignBand());

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
		gvar.setCalculation( CalculationEnum.NOTHING );
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
