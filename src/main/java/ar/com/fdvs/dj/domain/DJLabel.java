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

import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.entities.Entity;

public class DJLabel extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	protected boolean isJasperExpression = false;

	protected String text;
	protected CustomExpression labelExpression;
	protected Style style;	
	protected int height = 15;
	
	public DJLabel(){};

	public DJLabel(String text, Style labelStyle) {
		super();
		this.text = text;
		this.style = labelStyle;
	}

	public DJLabel(String text, Style labelStyle, boolean isJasperExpression) {
		super();
		this.text = text;
		this.style = labelStyle;
		this.isJasperExpression = isJasperExpression;
	}
	
	/**
	 * @Deprecated
	 */	
	public DJLabel(String text, Style labelStyle,
			LabelPosition labelPosition) {
		super();
		this.text = text;
		this.style = labelStyle;
	}

	public DJLabel(CustomExpression labelExpression, Style labelStyle) {
		super();
		this.labelExpression = labelExpression;
		this.style = labelStyle;
	}	

	public CustomExpression getLabelExpression() {
		return labelExpression;
	}


	public void setLabelExpression(CustomExpression labelExpression) {
		this.labelExpression = labelExpression;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isJasperExpression() {
		return isJasperExpression;
	}

	public void setJasperExpression(boolean isJasperExpression) {
		this.isJasperExpression = isJasperExpression;
	}


}
