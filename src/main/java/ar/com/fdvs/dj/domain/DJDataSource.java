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

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.entities.Entity;

/**
 * Describes a data source.
 *
 * The properties {@link #dataSourceOrigin} and {@link #dataSourceType} are constants from {@link DJConstants}
 * @author Juan Manuel
 *
 */
public class DJDataSource extends DJBaseElement{

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
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
