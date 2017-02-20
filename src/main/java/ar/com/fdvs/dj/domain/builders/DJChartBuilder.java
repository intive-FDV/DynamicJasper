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

import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJChartColors;
import ar.com.fdvs.dj.domain.DJChartOptions;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class DJChartBuilder {
	private Byte type;
	private DJGroup columnsGroup;
	private List<AbstractColumn> columns = new ArrayList<AbstractColumn>();
	private Byte operation;
	private DJChartOptions chartOptions;

	public DJChart build() throws ChartBuilderException {
		if (type == null) throw new ChartBuilderException("Chart type must be specified");
		if (columnsGroup == null) throw new ChartBuilderException("The group to wich the chart is related must be specified");
		if (columns.isEmpty()) throw new ChartBuilderException("At least one column to wich the chart is related must be specified");
		if (operation == null) throw new ChartBuilderException("The operation for the chart must be specified");
		if (chartOptions == null) chartOptions = createDefaultOptions();

		return new DJChart(type,columnsGroup,columns, operation,chartOptions);
	}

	private DJChartOptions createDefaultOptions() {
		return new DJChartOptions(true, Color.white, 300, 300, true, DJChartOptions.POSITION_HEADER, 0, 0, true, (byte) 1, DJChartColors.googleAnalytics());
	}

	public DJChartBuilder addColumn(AbstractColumn column) {
		this.columns.add(column);
		return this;
	}

	public byte getOperation() {
		return operation;
	}

	/**
	 * @deprecated
	 * @param operation
	 * @return
	 */
	public DJChartBuilder addOperation(byte operation) {
		this.operation = operation;
		return this;
	}
	
	public DJChartBuilder setOperation(byte operation) {
		this.operation = operation;
		return this;
	}

	public byte getType() {
		return type;
	}

	/***
	 * @deprecated
	 * @param type
	 * @return
	 */
	public DJChartBuilder addType(byte type) {
		this.type = type;
		return this;
	}
	
	public DJChartBuilder setType(byte type) {
		this.type = type;
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
