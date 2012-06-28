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

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * Charts to be displayed by dynamicjasper
 * @author msimone
 * @deprecated
 */
public class DJChart extends DJBaseElement{

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	//The possible chart types
	public static final byte PIE_CHART = JRDesignChart.CHART_TYPE_PIE;
	public static final byte BAR_CHART = JRDesignChart.CHART_TYPE_BAR;
//	public static final byte LINE_CHART = JRDesignChart.CHART_TYPE_LINE; //not yet... to much to think left

	//The possible calculation types
	public static final byte CALCULATION_COUNT = JRDesignVariable.CALCULATION_COUNT;
	public static final byte CALCULATION_SUM = JRDesignVariable.CALCULATION_SUM;

	//How to build the chart?
	private byte type;
	private DJGroup columnsGroup;
//	private AbstractColumn column;
	
	/**
	 * List<AbstractColumn>
	 */
	private List columns = new ArrayList();
	

	private byte operation;

	//How to show the chart?
	private DJChartOptions chartOptions;

	public DJChart(){}

	public DJChart(byte type, DJGroup columnsGroup, AbstractColumn column, byte operation, DJChartOptions chartOptions){
		this.type = type;
		this.columnsGroup = columnsGroup;
		
		if (column != null)
			this.columns.add(column);
		
		this.operation = operation;
		this.chartOptions = chartOptions;
	}
	
	public DJChart(byte type, DJGroup columnsGroup, List columns, byte operation, DJChartOptions chartOptions){
		this.type = type;
		this.columnsGroup = columnsGroup;
		
		if (columns != null)
			this.columns.addAll(columns);
		
		this.operation = operation;
		this.chartOptions = chartOptions;
	}

//	public AbstractColumn getColumn() {
//		return column;
//	}
//
//	public void setColumn(AbstractColumn column) {
//		this.column = column;
//	}

	public byte getOperation() {
		return operation;
	}

	public void setOperation(byte operation) {
		this.operation = operation;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public DJGroup getColumnsGroup() {
		return columnsGroup;
	}

	public void setColumnsGroup(DJGroup columnsGroup) {
		this.columnsGroup = columnsGroup;
	}

	public DJChartOptions getOptions() {
		return chartOptions;
	}

	public void setOptions(DJChartOptions options) {
		this.chartOptions = options;
	}
	
	public List getColumns() {
		return columns;
	}

	public void setColumns(List columns) {
		this.columns = columns;
	}
	
}
