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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJChartColors;
import ar.com.fdvs.dj.domain.DJChartOptions;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public class DJChartBuilder {
	private Byte type;
	private DJGroup columnsGroup;
	private List columns = new ArrayList();
	private Byte operation;
	private DJChartOptions chartOptions;

	public DJChart build() throws ChartBuilderException {
		if (type == null) throw new ChartBuilderException("Chart type must be specified");
		if (columnsGroup == null) throw new ChartBuilderException("The group to wich the chart is related must be specified");
		if (columns.isEmpty()) throw new ChartBuilderException("At least one column to wich the chart is related must be specified");
		if (operation == null) throw new ChartBuilderException("The operation for the chart must be specified");
		if (chartOptions == null) chartOptions = createDefaultOptions();

		DJChart chart = new DJChart(type.byteValue(),columnsGroup,columns,operation.byteValue(),chartOptions);
		return chart;
	}

	private DJChartOptions createDefaultOptions() {
		DJChartOptions options = new DJChartOptions(true, Color.white, 300, 300, true, DJChartOptions.POSITION_HEADER, 0, 0, true, (byte) 1, DJChartColors.googleAnalytics());
		return options;
	}

	public DJChartBuilder addColumn(AbstractColumn column) {
		this.columns.add(column);
		return this;
	}

	public byte getOperation() {
		return operation.byteValue();
	}

	/**
	 * @deprecated
	 * @param operation
	 * @return
	 */
	public DJChartBuilder addOperation(byte operation) {
		this.operation = new Byte(operation);
		return this;
	}
	
	public DJChartBuilder setOperation(byte operation) {
		this.operation = new Byte(operation);
		return this;
	}

	public byte getType() {
		return type.byteValue();
	}

	/***
	 * @deprecated
	 * @param type
	 * @return
	 */
	public DJChartBuilder addType(byte type) {
		this.type = new Byte(type);
		return this;
	}
	
	public DJChartBuilder setType(byte type) {
		this.type = new Byte(type);
		return this;
	}

	public DJGroup getColumnsGroup() {
		return columnsGroup;
	}

	/**
	 * @deprecated
	 * @param columnsGroup
	 * @return
	 */
	public DJChartBuilder addColumnsGroup(DJGroup columnsGroup) {
		this.columnsGroup = columnsGroup;
		return this;
	}

	public DJChartBuilder setColumnsGroup(DJGroup columnsGroup) {
		this.columnsGroup = columnsGroup;
		return this;
	}

	public DJChartBuilder addParams(byte type, DJGroup columnsGroup, AbstractColumn column, byte operation, DJChartOptions chartOptions){
		return this.setType(type).setColumnsGroup(columnsGroup)
			.addColumn(column)
			.setOperation(operation)
			.setChartOptions(chartOptions);
	}

	public DJChartOptions getChartOptions() {
		return chartOptions;
	}

	/**
	 * @deprecated
	 * @param chartOptions
	 * @return
	 */
	public DJChartBuilder addChartOptions(DJChartOptions chartOptions) {
		this.chartOptions = chartOptions;
		return this;
	}

	public DJChartBuilder setChartOptions(DJChartOptions chartOptions) {
		this.chartOptions = chartOptions;
		return this;
	}

	public DJChartBuilder setShowLegend(boolean showLegend) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setShowLegend(showLegend);
		return this;
	}

	public DJChartBuilder setBackColor(Color backColor) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setBackColor(backColor);
		return this;
	}

	public DJChartBuilder setHeight(int height) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setHeight(height);
		return this;
	}

	public DJChartBuilder setWidth(int width) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setWidth(width);
		return this;
	}

	public DJChartBuilder setCentered(boolean centered) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setCentered(centered);
		return this;
	}

	public DJChartBuilder setPosition(byte position) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setPosition(position);
		return this;
	}

	public DJChartBuilder setY(int y) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setY(y);
		return this;
	}

	public DJChartBuilder setX(int x) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setX(x);
		return this;
	}

	public DJChartBuilder setShowLabels(boolean showLabels) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setShowLabels(showLabels);
		return this;
	}

	public DJChartBuilder setBorder(byte border) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setBorder(border);
		return this;
	}

	public DJChartBuilder setColors(List colors) {
		if (chartOptions == null) chartOptions = createDefaultOptions();
		this.chartOptions.setColors(colors);
		return this;
	}

	public DJChartBuilder setUseColumnsAsCategories(boolean b) {
		this.chartOptions.setUseColumnsAsCategorie(b);
		return this;
	}
}
