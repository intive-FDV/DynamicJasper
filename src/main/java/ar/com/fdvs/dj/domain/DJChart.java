package ar.com.fdvs.dj.domain;

import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * Charts to be displayed by dynamicjasper
 * @author msimone
 */
public class DJChart {
	
	//The possible chart types
	public static final byte PIE_CHART = JRDesignChart.CHART_TYPE_PIE;
	public static final byte BAR_CHART = JRDesignChart.CHART_TYPE_BAR;
	
	//The possible calculation types
	public static final byte CALCULATION_COUNT = JRDesignVariable.CALCULATION_COUNT;
	public static final byte CALCULATION_SUM = JRDesignVariable.CALCULATION_SUM;
	
	//How to build the chart?
	private byte type;
	private ColumnsGroup columnsGroup;
	private AbstractColumn column;
	private byte operation;
	
	//How to show the chart?
	private DJChartOptions chartOptions;

	
	public DJChart(){}
	
	public DJChart(byte type, ColumnsGroup columnsGroup, AbstractColumn column, byte operation, DJChartOptions chartOptions){
		this.type = type;
		this.columnsGroup = columnsGroup;
		this.column = column;
		this.operation = operation;		
		this.chartOptions = chartOptions;
	}
	
	public AbstractColumn getColumn() {
		return column;
	}
	
	public void setColumn(AbstractColumn column) {
		this.column = column;
	}
	
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

	public ColumnsGroup getColumnsGroup() {
		return columnsGroup;
	}

	public void setColumnsGroup(ColumnsGroup columnsGroup) {
		this.columnsGroup = columnsGroup;
	}

	public DJChartOptions getOptions() {
		return chartOptions;
	}

	public void setOptions(DJChartOptions options) {
		this.chartOptions = options;
	}
}
