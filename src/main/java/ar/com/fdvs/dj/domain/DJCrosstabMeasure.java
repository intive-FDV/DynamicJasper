package ar.com.fdvs.dj.domain;

public class DJCrosstabMeasure {

	private ColumnProperty property;
	private ColumnsGroupVariableOperation operation;
	private String title;
	
	private Style style;
	
	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public DJCrosstabMeasure(String propertyName, String className, ColumnsGroupVariableOperation operation, String title) {
		super();
		this.property = new ColumnProperty(propertyName, className);
		this.operation = operation;
		this.title = title;
	}
	
	public DJCrosstabMeasure(ColumnProperty measure, ColumnsGroupVariableOperation operation, String title) {
		super();
		this.property = measure;
		this.operation = operation;
		this.title = title;
	}
	
	public DJCrosstabMeasure() {
		super();
	}

	public ColumnProperty getProperty() {
		return property;
	}
	public void setProperty(ColumnProperty measure) {
		this.property = measure;
	}
	public ColumnsGroupVariableOperation getOperation() {
		return operation;
	}
	public void setOperation(ColumnsGroupVariableOperation operation) {
		this.operation = operation;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
