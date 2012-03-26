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

package ar.com.fdvs.dj.domain.builders;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.charts.design.JRDesignCategoryDataset;
import net.sf.jasperreports.charts.design.JRDesignCategorySeries;
import net.sf.jasperreports.charts.design.JRDesignPieDataset;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public class DataSetFactory {

	public static JRDesignChartDataset getDataset(DJChart djchart, JRDesignGroup group, JRDesignGroup parentGroup, List vars){

		JRDesignChartDataset dataSet = null;
		
		byte chartType = djchart.getType();
		
		if (chartType == DJChart.PIE_CHART){
			dataSet = createPieDataset(group,parentGroup, vars, djchart);
		}
		else if (chartType == DJChart.BAR_CHART) {
			dataSet = createBarDataset(group,parentGroup, vars, djchart);
		}
//		else if (chartType ==  DJChart.LINE_CHART) {
//			dataSet = createLineDataset(group,parentGroup, vars, djchart);
//		}

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
			AbstractColumn col = (AbstractColumn) djchart.getColumns().get(index);
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

	protected static JRDesignChartDataset createBarDataset(JRDesignGroup group, JRDesignGroup parentGroup, List vars, DJChart djchart) {
		JRDesignCategoryDataset data = new JRDesignCategoryDataset(null);

		for (Iterator iterator = vars.iterator(); iterator.hasNext();) {
			JRDesignCategorySeries serie = new JRDesignCategorySeries();
			JRDesignVariable var = (JRDesignVariable) iterator.next();
			
			//And use it as value for each bar
			JRDesignExpression varExp = getExpressionFromVariable(var);
			serie.setValueExpression(varExp);
	
			//The key for each bar
			JRExpression exp2 = group.getExpression();
	
			JRDesignExpression exp3 = new JRDesignExpression();
			int index = vars.indexOf(var);
			AbstractColumn col = (AbstractColumn) djchart.getColumns().get(index);
			exp3.setText("\"" + col.getTitle() + "\"");
			exp3.setValueClass(String.class);
	
			//Here you can set subgroups of bars
			if (!djchart.getOptions().isUseColumnsAsCategorie()){
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

	protected static JRDesignChartDataset createPieDataset(JRDesignGroup group, JRDesignGroup parentGroup, List vars, DJChart djchart) {		
		JRDesignPieDataset data = new JRDesignPieDataset(null);

		for (Iterator iterator = vars.iterator(); iterator.hasNext();) {
			JRDesignVariable var = (JRDesignVariable) iterator.next();
			
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
			dataset.setResetType(JRDesignVariable.RESET_TYPE_REPORT);
		else
			dataset.setResetType(JRDesignVariable.RESET_TYPE_GROUP);
	}
}
