package ar.com.fdvs.dj.domain.builders;

import java.awt.Color;

import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJChartColors;
import ar.com.fdvs.dj.domain.DJChartOptions;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public class DJChartBuilder {
	private Byte type;
	private ColumnsGroup columnsGroup;
	private AbstractColumn column;
	private Byte operation;
	private DJChartOptions chartOptions;
	
	public DJChart build() throws ChartBuilderException {
		if (type == null) throw new ChartBuilderException("Chart type must be specified");
		if (columnsGroup == null) throw new ChartBuilderException("The group to wich the chart is related must be specified");
		if (column == null) throw new ChartBuilderException("The column to wich the chart is related must be specified");
		if (operation == null) throw new ChartBuilderException("The operation for the chart must be specified");
		if (chartOptions == null) chartOptions = createDefaultOperations();
		
		DJChart chart = new DJChart(type.byteValue(),columnsGroup,column,operation.byteValue(),chartOptions);
		return chart;
	}
	
	private DJChartOptions createDefaultOperations() {
		DJChartOptions options = new DJChartOptions(true, Color.white, 300, 300, true, DJChartOptions.POSITION_HEADER, 0, 0, true, (byte) 1, DJChartColors.justAnotherColorScheme());		
		return options;
	}

	public AbstractColumn getColumn() {
		return column;
	}
	
	public DJChartBuilder addColumn(AbstractColumn column) {
		this.column = column;
		return this;
	}
	
	public byte getOperation() {
		return operation.byteValue();
	}
	
	public DJChartBuilder addOperation(byte operation) {
		this.operation = new Byte(operation);
		return this;
	}
	
	public byte getType() {
		return type.byteValue();
	}
	
	public DJChartBuilder addType(byte type) {
		this.type = new Byte(type);
		return this;
	}

	public ColumnsGroup getColumnsGroup() {
		return columnsGroup;
	}

	public DJChartBuilder addColumnsGroup(ColumnsGroup columnsGroup) {
		this.columnsGroup = columnsGroup;
		return this;
	}
	
	public DJChartBuilder addParams(byte type, ColumnsGroup columnsGroup, AbstractColumn column, byte operation, DJChartOptions chartOptions){
		return this.addType(type).addColumnsGroup(columnsGroup).addColumn(column).addOperation(operation).addChartOptions(chartOptions);
	}

	public DJChartOptions getChartOptions() {
		return chartOptions;
	}

	public DJChartBuilder addChartOptions(DJChartOptions chartOptions) {
		this.chartOptions = chartOptions;
		return this;
	}

}
