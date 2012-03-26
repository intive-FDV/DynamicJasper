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
