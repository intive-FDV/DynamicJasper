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
	
	private boolean preSorted = false; //for cross-tabs

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

	public boolean isPreSorted() {
		return preSorted;
	}

	public void setPreSorted(boolean preSorted) {
		this.preSorted = preSorted;
	}

}
