package ar.com.fdvs.dj.domain.builders;

import net.sf.jasperreports.charts.design.JRDesignCategoryDataset;
import net.sf.jasperreports.charts.design.JRDesignCategorySeries;
import net.sf.jasperreports.charts.design.JRDesignPieDataset;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import ar.com.fdvs.dj.domain.DJChart;

public class DataSetFactory {
	
	public static JRDesignChartDataset getDataset(byte chartType, JRDesignGroup group, JRDesignGroup parentGroup, JRDesignVariable var){

		JRDesignChartDataset dataSet = null;
		
		if (chartType == DJChart.PIE_CHART){
			dataSet = createPieDataset(group,parentGroup, var);
		}
		else if (chartType == DJChart.BAR_CHART) {
			dataSet = createBarDataset(group,parentGroup, var);
		}
		
		//if (dataSet == null); //TODO: exception "Unknown chart type"
		
		return dataSet;
	}

	private static JRDesignChartDataset createBarDataset(JRDesignGroup group, JRDesignGroup parentGroup, JRDesignVariable var) {
		JRDesignCategoryDataset data = new JRDesignCategoryDataset(null);		
		JRDesignCategorySeries serie = new JRDesignCategorySeries();

		//And use it as value for each bar
		serie.setValueExpression(getExpressionFromVariable(var));
	
		//The key for each bar
		JRExpression exp2 = group.getExpression();

		/*JRDesignExpression exp3 = new JRDesignExpression();
		exp3.setText("$F{productLine}");
		exp3.setValueClass(String.class);*/
		
		//Here you can set subgroups of bars
		//serie.setCategoryExpression(exp3);
		serie.setCategoryExpression(exp2);

		serie.setLabelExpression(exp2);
		serie.setSeriesExpression(exp2);
		
		data.addCategorySeries(serie);
		
		setResetStyle(data, group, parentGroup);
		
		return data;
	}

	private static JRDesignChartDataset createPieDataset(JRDesignGroup group, JRDesignGroup parentGroup, JRDesignVariable var) {
		JRDesignPieDataset data = new JRDesignPieDataset(null);
		
		//And transform it in the value for each pie slice		
		data.setValueExpression(getExpressionFromVariable(var));
		
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
	private static JRExpression getExpressionFromVariable(JRDesignVariable var){
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
