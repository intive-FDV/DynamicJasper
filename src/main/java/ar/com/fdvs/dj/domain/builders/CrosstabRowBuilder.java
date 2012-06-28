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
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.Style;

public class CrosstabRowBuilder {

	DJCrosstabRow row = new DJCrosstabRow();

	public DJCrosstabRow build(){
		return this.row;
	}

/**
 * 		DJCrosstabRow row = new DJCrosstabRow();
		row.setProperty(new ColumnProperty("productLine",String.class.getName()));
		row.setHeaderWidth(100);
		row.setHeight(30);
		row.setTitle("Product Line my mother teressa");
		row.setShowTotals(true);
		row.setTotalStyle(totalStyle);
		row.setTotalHeaderStyle(totalHeader);
		row.setHeaderStyle(colAndRowHeaderStyle);
 * @param property
 * @param className
 * @return
 */

	public CrosstabRowBuilder setProperty(String property, String className){
		row.setProperty(new ColumnProperty(property,className));
		return this;
	}
	public CrosstabRowBuilder setHeaderWidth(int height){
		row.setHeaderWidth(height);
		return this;
	}
	public CrosstabRowBuilder setHeight(int height){
		row.setHeight(height);
		return this;
	}
	public CrosstabRowBuilder setTitle(String title){
		row.setTitle(title);
		return this;
	}
	public CrosstabRowBuilder setTotalLegend(String legend){
		row.setTotalLegend(legend);
		return this;
	}
	public CrosstabRowBuilder setShowTotals(boolean showTotal){
		row.setShowTotals(showTotal);
		return this;
	}
	public CrosstabRowBuilder setTotalStyle(Style style){
		row.setTotalStyle(style);
		return this;
	}
	public CrosstabRowBuilder setTotalHeaderStyle(Style style){
		row.setTotalHeaderStyle(style);
		return this;
	}
	public CrosstabRowBuilder setHeaderStyle(Style style){
		row.setHeaderStyle(style);
		return this;
	}

}
