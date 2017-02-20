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

package ar.com.fdvs.dj.domain.chart.dataset;

import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.hyperlink.LiteralExpression;
import ar.com.fdvs.dj.util.ExpressionUtils;
import net.sf.jasperreports.charts.design.JRDesignCategoryDataset;
import net.sf.jasperreports.charts.design.JRDesignCategorySeries;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDataset extends AbstractDataset {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private PropertyColumn category = null;
	private final List<AbstractColumn> series = new ArrayList<AbstractColumn>();
	private final Map<AbstractColumn, StringExpression> seriesLabels = new HashMap<AbstractColumn, StringExpression>();
	private boolean useSeriesAsCategory = false;
		
	/**
	 * Sets the category column.
	 *
	 * @param category the category column
	 **/
	public void setCategory(PropertyColumn category) {
		this.category = category;
	}
	
	/**
	 * Returns the category column.
	 *
	 * @return	the category column
	 **/
	public PropertyColumn getCategory() {
		return category;
	}
	
	/**
	 * Adds the specified serie column to the dataset.
	 * 
	 * @param column the serie column
	 **/
	public void addSerie(AbstractColumn column) {
		series.add(column);
	}

	/**
	 * Adds the specified serie column to the dataset with custom label.
	 * 
	 * @param column the serie column
	 * @param label column the custom label
	 **/
	public void addSerie(AbstractColumn column, String label) {
		addSerie(column, new LiteralExpression(label));
	}

	/**
	 * Adds the specified serie column to the dataset with custom label expression.
	 * 
	 * @param column the serie column
	 * @param labelExpression column the custom label expression
	 **/
	public void addSerie(AbstractColumn column, StringExpression labelExpression) {
		series.add(column);
		seriesLabels.put(column, labelExpression);
	}
	
	/**
	 * Removes the specified serie column from the dataset.
	 * 
	 * @param column the serie column	 
	 **/
	public void removeSerie(AbstractColumn column) {
		series.remove(column);
		seriesLabels.remove(column);
	}

	/**
	 * Removes all defined series.
	 */
	public void clearSeries() {
		series.clear();
		seriesLabels.clear();
	}
	
	/**
	 * Returns a list of all the defined series.  Every entry in the list is of type AbstractColumn.
	 * If there are no defined series this method will return an empty list, not null. 
	 *
	 * @return	the list of series
	 **/
	public List getSeries()	{
		return series;
	}
	
	public void setUseSeriesAsCategory(boolean useSeriesAsCategory) {
		this.useSeriesAsCategory = useSeriesAsCategory;
	}

	public boolean isUseSeriesAsCategory() {
		return useSeriesAsCategory;
	}
	
	public JRDesignChartDataset transform(DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars) {
		JRDesignCategoryDataset data = new JRDesignCategoryDataset(null);

		for (AbstractColumn sery : series) {
			JRDesignCategorySeries serie = new JRDesignCategorySeries();

			//And use it as value for each bar
			JRDesignExpression varExp = getExpressionFromVariable((JRDesignVariable) vars.get(sery));
			serie.setValueExpression(varExp);

			//The key for each bar
			JRExpression exp2 = group.getExpression();

			JRDesignExpression exp3;
			if (seriesLabels.containsKey(sery)) {
				exp3 = ExpressionUtils.createAndRegisterExpression(design, "dataset_" + sery.getName() + "_" + name, seriesLabels.get(sery));
			} else {
				exp3 = new JRDesignExpression();
				exp3.setText("\"" + sery.getTitle() + "\"");
			}
			exp3.setValueClass(String.class);

			//Here you can set subgroups of bars
			if (useSeriesAsCategory) {
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

	public List getColumns() {
		return series;
	}

	public PropertyColumn getColumnsGroup() {
		return category;
	}
}
