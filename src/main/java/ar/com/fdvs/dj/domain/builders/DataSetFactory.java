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

package ar.com.fdvs.dj.domain.builders;

import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.charts.design.JRDesignCategoryDataset;
import net.sf.jasperreports.charts.design.JRDesignCategorySeries;
import net.sf.jasperreports.charts.design.JRDesignPieDataset;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.DatasetResetTypeEnum;

import java.util.List;

public class DataSetFactory {

	public static JRDesignChartDataset getDataset(DJChart djchart, JRDesignGroup group, JRDesignGroup parentGroup, List<JRDesignVariable> vars){

		JRDesignChartDataset dataSet = null;
		
		byte chartType = djchart.getType();
		
		if (chartType == DJChart.PIE_CHART){
			dataSet = createPieDataset(group,parentGroup, vars, djchart);
		}
		else if (chartType == DJChart.BAR_CHART) {
			dataSet = createBarDataset(group,parentGroup, vars, djchart);
		}

		if (dataSet == null){
			throw new DJException("Error creating dataset for chart, no valid dataset type.");
		}

		return dataSet;
	}

	/**
	 * Use vars[0] as value, user vars[1] as series
	 * @param group
	 * @param parentGroup
	 * @param vars
	 * @param djchart
	 * @return
	 */
	protected static JRDesignChartDataset createLineDataset(JRDesignGroup group, JRDesignGroup parentGroup, List vars, DJChart djchart) {
		JRDesignCategoryDataset data = new JRDesignCategoryDataset(null);

//		for (Iterator iterator = vars.iterator(); iterator.hasNext();) {
			JRDesignCategorySeries serie = new JRDesignCategorySeries();
//			JRDesignVariable var = (JRDesignVariable) iterator.next();
			JRDesignVariable var = (JRDesignVariable) vars.get(0);
			JRDesignVariable var1 = (JRDesignVariable) vars.get(0);
			if (vars.size() > 1)
				var1 = (JRDesignVariable) vars.get(1);
			
			//And use it as value for each bar
			JRDesignExpression varExp = getExpressionFromVariable(var);
			JRExpression varExp1 = var1.getExpression();
			serie.setValueExpression(varExp);
	
			//The key for each bar
			JRExpression exp2 = group.getExpression();
	
			JRDesignExpression exp3 = new JRDesignExpression();
			int index = vars.indexOf(var);
			AbstractColumn col = djchart.getColumns().get(index);
			exp3.setText("\"" + col.getTitle() + "\"");
			exp3.setValueClass(String.class);
	
			//Here you can set subgroups of bars
			serie.setCategoryExpression(exp2);
//			serie.setCategoryExpression(varExp1);
	
			serie.setLabelExpression(exp2);
			serie.setSeriesExpression(varExp1);
				
			data.addCategorySeries(serie);
//		}

		setResetStyle(data, group, parentGroup);
		return data;
	}

	protected static JRDesignChartDataset createBarDataset(JRDesignGroup group, JRDesignGroup parentGroup, List<JRDesignVariable> vars, DJChart djchart) {
		JRDesignCategoryDataset data = new JRDesignCategoryDataset(null);

		for (JRDesignVariable var1 : vars) {
			JRDesignCategorySeries serie = new JRDesignCategorySeries();

			//And use it as value for each bar
			JRDesignExpression varExp = getExpressionFromVariable(var1);
			serie.setValueExpression(varExp);

			//The key for each bar
			JRExpression exp2 = group.getExpression();

			JRDesignExpression exp3 = new JRDesignExpression();
			int index = vars.indexOf(var1);
			AbstractColumn col = djchart.getColumns().get(index);
			exp3.setText("\"" + col.getTitle() + "\"");
			exp3.setValueClass(String.class);

			//Here you can set subgroups of bars
			if (!djchart.getOptions().isUseColumnsAsCategorie()) {
				serie.setCategoryExpression(exp3);

				serie.setLabelExpression(exp2);
				serie.setSeriesExpression(exp2);
			} else {
				//FIXED: due to https://sourceforge.net/forum/message.php?msg_id=7396861
				serie.setCategoryExpression(exp2);

				serie.setLabelExpression(exp3);
				serie.setSeriesExpression(exp3);
			}


			data.addCategorySeries(serie);
		}

		setResetStyle(data, group, parentGroup);

		return data;
	}

	protected static JRDesignChartDataset createPieDataset(JRDesignGroup group, JRDesignGroup parentGroup, List<JRDesignVariable> vars, DJChart djchart) {
		JRDesignPieDataset data = new JRDesignPieDataset(null);

		//noinspection LoopStatementThatDoesntLoop
		for (JRDesignVariable var : vars) {
			//And transform it in the value for each pie slice
			JRDesignExpression expression = getExpressionFromVariable(var);
			data.setValueExpression(expression);

			break; //PIE data set uses only one series
		}
		//The key for each pie slice
		data.setKeyExpression(group.getExpression());

		setResetStyle(data, group, parentGroup);

		return data;
	}

	/**
	 * Generates an expression from a variable
	 * @param var The variable from which to generate the expression
	 * @return A expression that represents the given variable
	 */
	private static JRDesignExpression getExpressionFromVariable(JRDesignVariable var){
		JRDesignExpression exp = new JRDesignExpression();
		exp.setText("$V{" + var.getName() + "}");
		exp.setValueClass(var.getValueClass());
		return exp;
	}

	private static void setResetStyle(JRDesignChartDataset dataset, JRDesignGroup group, JRDesignGroup parentGroup){
		//When to start a new chart? When the group's parent changes
		dataset.setResetGroup(parentGroup);
		if (dataset.getResetGroup().equals(group))
			dataset.setResetType( DatasetResetTypeEnum.REPORT );
		else
			dataset.setResetType( DatasetResetTypeEnum.GROUP );
	}
}
