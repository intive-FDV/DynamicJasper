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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.charts.design.JRDesignPieDataset;
import net.sf.jasperreports.charts.design.JRDesignPieSeries;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

public class PieDataset extends AbstractDataset {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private PropertyColumn key = null;
	private List series = new ArrayList();
		
	/**
	 * Sets the key column.
	 *
	 * @param key the key column
	 **/
	public void setKey(PropertyColumn key) {
		this.key = key;
	}
	
	/**
	 * Returns the key column.
	 *
	 * @return	the key column
	 **/
	public PropertyColumn getKey() {
		return key;
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
	 * Removes the specified serie column from the dataset.
	 * 
	 * @param column the serie column	 
	 **/
	public void removeSerie(AbstractColumn column) {
		series.remove(column);
	}

	/**
	 * Removes all defined series.
	 */
	public void clearSeries() {
		series.clear();
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
	
	public JRDesignChartDataset transform(DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars) {
		JRDesignPieDataset data = new JRDesignPieDataset(null);

		for (Iterator iterator = series.iterator(); iterator.hasNext();) {
			JRDesignPieSeries serie = new JRDesignPieSeries();
			AbstractColumn column = (AbstractColumn) iterator.next();
			
			//And use it as value for each bar
			JRDesignExpression varExp = getExpressionFromVariable((JRDesignVariable) vars.get(column));
			serie.setValueExpression(varExp);
	
			//The key for each bar
			JRExpression exp2 = group.getExpression();			
			serie.setKeyExpression(exp2);
				
			data.addPieSeries(serie);
		}
		
		setResetStyle(data, group, parentGroup);

		return data;
	}

	public List getColumns() {
		return series;
	}

	public PropertyColumn getColumnsGroup() {
		return key;
	}

}
