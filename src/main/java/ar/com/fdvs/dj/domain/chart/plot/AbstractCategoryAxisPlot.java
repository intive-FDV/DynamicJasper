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

package ar.com.fdvs.dj.domain.chart.plot;

import ar.com.fdvs.dj.domain.entities.Entity;


public abstract class AbstractCategoryAxisPlot extends AbstractPlot {
	
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private DJAxisFormat categoryAxisFormat = new DJAxisFormat();
	private DJAxisFormat valueAxisFormat = new DJAxisFormat();

	/**
	 * Sets the category axis format.
	 * 
	 * @param categoryAxisFormat the category axis format
	 **/
	public void setCategoryAxisFormat(DJAxisFormat categoryAxisFormat) {
		this.categoryAxisFormat = categoryAxisFormat;
	}

	/**
	 * Returns the category axis format.
	 * 
	 * @return the category axis format
	 **/
	public DJAxisFormat getCategoryAxisFormat() {
		return categoryAxisFormat;
	}

	/**
	 * Sets the value axis format.
	 * 
	 * @param valueAxisFormat the value axis format
	 **/
	public void setValueAxisFormat(DJAxisFormat valueAxisFormat) {
		this.valueAxisFormat = valueAxisFormat;
	}

	/**
	 * Returns the value axis format.
	 * 
	 * @return the value axis format
	 **/
	public DJAxisFormat getValueAxisFormat() {
		return valueAxisFormat;
	}
}
