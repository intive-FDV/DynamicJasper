package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.core.DJConstants;

/**
 * Describes a data source.
 * 
 * The properties {@link #dataSourceOrigin} and {@link #dataSourceType} are constants from {@link DJConstants}
 * @author Juan Manuel
 *
 */
public class DJDataSource {
	
	/**
	 * This expression must point to a JRDataSource object
	 */
	private String dataSourceExpression;
	
	/**
	 * Tells form where to look up the data source expression
	 */
	private int dataSourceOrigin = DJConstants.DATA_SOURCE_ORIGIN_PARAMETER;

	private int dataSourceType = DJConstants.DATA_SOURCE_TYPE_COLLECTION;

	public DJDataSource(String dataSourceExpression, int dataSourceOrigin, int dataSourceType) {
		super();
		this.dataSourceExpression = dataSourceExpression;
		this.dataSourceOrigin = dataSourceOrigin;
		this.dataSourceType = dataSourceType;
	}

	public String getDataSourceExpression() {
		return dataSourceExpression;
	}

	public void setDataSourceExpression(String dataSourceExpression) {
		this.dataSourceExpression = dataSourceExpression;
	}

	public int getDataSourceOrigin() {
		return dataSourceOrigin;
	}

	public void setDataSourceOrigin(int dataSourceOrigin) {
		this.dataSourceOrigin = dataSourceOrigin;
	}

	public int getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(int dataSourceType) {
		this.dataSourceType = dataSourceType;
	}	

}
