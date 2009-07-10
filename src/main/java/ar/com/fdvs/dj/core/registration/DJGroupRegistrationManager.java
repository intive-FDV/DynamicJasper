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
		DJGroup columnsGroup = (DJGroup) entity;
		try {
			JRDesignGroup group = (JRDesignGroup)transformEntity(columnsGroup);
			getDjd().addGroup(group);
			//Variables are registered right after the group where they belong.
			String property = columnsGroup.getColumnToGroupBy().getColumnProperty().getProperty();
			ColumnsGroupVariablesRegistrationManager headerVariablesRM = new ColumnsGroupVariablesRegistrationManager(ColumnsGroupVariablesRegistrationManager.HEADER, property, getDjd(),getDynamicReport(),getLayoutManager());
			headerVariablesRM.registerEntities(columnsGroup.getHeaderVariables());
			ColumnsGroupVariablesRegistrationManager footerVariablesRM = new ColumnsGroupVariablesRegistrationManager(ColumnsGroupVariablesRegistrationManager.FOOTER, property, getDjd(),getDynamicReport(),getLayoutManager());
			footerVariablesRM.registerEntities(columnsGroup.getFooterVariables());
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage());
		}
	}

	//PropertyColumn only can be used for grouping (not OperationColumn)
	protected Object transformEntity(Entity entity) {
		log.debug("transforming group...");
		DJGroup djgroup = (DJGroup) entity;
		PropertyColumn column = djgroup.getColumnToGroupBy();
		JRDesignGroup group = new JRDesignGroup();

		int groupIndex = getDynamicReport().getColumnsGroups().indexOf(djgroup);
		int columnIndex = getDynamicReport().getColumns().indexOf(djgroup.getColumnToGroupBy());
		if (column instanceof GlobalGroupColumn){
			group.setName("global_column_" + groupIndex);
		} else {
			group.setName( "group["+groupIndex+"]_for_column_" + columnIndex + "-" +  column.getTitle());
		}
		
		getLayoutManager().getReferencesMap().put(group.getName(), djgroup);

		group.setCountVariable(new JRDesignVariable());
		group.setGroupFooter(new JRDesignBand());
		group.setGroupHeader(new JRDesignBand());

		JRDesignExpression jrExpression = new JRDesignExpression();
		
		CustomExpression expressionToGroupBy = column.getExpressionToGroupBy();
		if (expressionToGroupBy != null) { //new in 3.0.7-b5
			String expToGroupByName = group.getName() + "_expression_to_group_by";
			registerExpressionColumnParameter(expToGroupByName, expressionToGroupBy);
			String expText = ExpressionUtils.createCustomExpressionInvocationText(expToGroupByName);
			jrExpression.setText(expText);
			log.debug("Expression for CustomExpression = " + expText);
			jrExpression.setValueClassName(expressionToGroupBy.getClassName());
		} else {
			jrExpression.setText(column.getTextForExpression());
			jrExpression.setValueClassName(column.getValueClassNameForExpression());
		}
		

		group.setExpression(jrExpression);
		group.setCountVariable(new JRDesignVariable());

		return group;
	}



}
