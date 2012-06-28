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

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.Style;

public class CrosstabColumnBuilder {

	DJCrosstabColumn column = new DJCrosstabColumn();

	public DJCrosstabColumn build(){
		return this.column;
	}

	public CrosstabColumnBuilder setProperty(String property, String className){
		column.setProperty(new ColumnProperty(property,className));
		return this;
	}
	public CrosstabColumnBuilder setHeaderHeight(int height){
		column.setHeaderHeight(height);
		return this;
	}
	public CrosstabColumnBuilder setWidth(int width){
		column.setWidth(width);
		return this;
	}
	public CrosstabColumnBuilder setTitle(String title){
		column.setTitle(title);
		return this;
	}
	public CrosstabColumnBuilder setTotalLegend(String legend){
		column.setTotalLegend(legend);
		return this;
	}	
	public CrosstabColumnBuilder setShowTotals(boolean showTotal){
		column.setShowTotals(showTotal);
		return this;
	}
	public CrosstabColumnBuilder setTotalStyle(Style style){
		column.setTotalStyle(style);
		return this;
	}
	public CrosstabColumnBuilder setTotalHeaderStyle(Style style){
		column.setTotalHeaderStyle(style);
		return this;
	}
	public CrosstabColumnBuilder setHeaderStyle(Style style){
		column.setHeaderStyle(style);
		return this;
	}

}
