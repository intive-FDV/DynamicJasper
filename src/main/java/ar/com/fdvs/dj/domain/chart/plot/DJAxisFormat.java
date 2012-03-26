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

import java.awt.Color;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.hyperlink.LiteralExpression;

public class DJAxisFormat extends DJBaseElement {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	private StringExpression labelExpression = null;
	private Font labelFont = null;
	private Color labelColor = null;
	private Font tickLabelFont = null;
	private Color tickLabelColor = null;
	private String tickLabelMask = null;
	private Color lineColor = null;
	private CustomExpression rangeMinValueExpression = null;
	private CustomExpression rangeMaxValueExpression = null;
	
	public DJAxisFormat() {		
	}
	
	public DJAxisFormat(String label) {
		this(new LiteralExpression(label));
	}

	public DJAxisFormat(StringExpression labelExpression) {
		this.labelExpression = labelExpression;
	}
	
	public StringExpression getLabelExpression() {
		return labelExpression;
	}
	
	/**
	 * Sets the label expression.
	 *
	 * @param labelExpression the label expression
	 **/
	public void setLabelExpression(StringExpression labelExpression) {
		this.labelExpression = labelExpression;
	}
	
	/**
	 * Returns the label font.
	 *
	 * @return the label font
	 **/
	public Font getLabelFont() {
		return labelFont;
	}
	
	/**
	 * Sets the label font.
	 *
	 * @param labelFont the label font
	 **/
	public void setLabelFont(Font labelFont) {
		this.labelFont = labelFont;
	}
	
	/**
	 * Returns the label color.
	 *
	 * @return the label color
	 **/
	public Color getLabelColor() {
		return labelColor;
	}
	
	/**
	 * Sets the label color.
	 *
	 * @param labelColor the label color
	 **/
	public void setLabelColor(Color labelColor) {
		this.labelColor = labelColor;
	}
	
	/**
	 * Returns the tick label font.
	 *
	 * @return the tick label font 
	 **/
	public Font getTickLabelFont() {
		return tickLabelFont;
	}
	
	/**
	 * Sets the tick label font.
	 *
	 * @param tickLabelFont the tick label font
	 **/
	public void setTickLabelFont(Font tickLabelFont) {
		this.tickLabelFont = tickLabelFont;
	}
	
	/**
	 * Returns the tick label color.
	 *
	 * @return the tick label color 
	 **/
	public Color getTickLabelColor() {
		return tickLabelColor;
	}
	
	/**
	 * Sets the tick label color.
	 *
	 * @param tickLabelColor the tick label color
	 **/
	public void setTickLabelColor(Color tickLabelColor) {
		this.tickLabelColor = tickLabelColor;
	}
	
	/**
	 * Returns the tick label mask.
	 *
	 * @return the tick label mask 
	 **/
	public String getTickLabelMask() {
		return tickLabelMask;
	}
	
	/**
	 * Sets the tick label mask.
	 *
	 * @param tickLabelMask the tick label mask
	 **/
	public void setTickLabelMask(String tickLabelMask) {
		this.tickLabelMask = tickLabelMask;
	}
	
	/**
	 * Returns the line color.
	 *
	 * @return the line color 
	 **/
	public Color getLineColor() {
		return lineColor;
	}
	
	/**
	 * Sets the line color.
	 *
	 * @param lineColor the line color
	 **/
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	
	/**
	 * Sets the range axis minimum value expression.
	 *
	 * @param rangeMinValueExpression the range axis minimum value expression
	 **/
	public void setRangeMinValueExpression(CustomExpression rangeMinValueExpression) {
		this.rangeMinValueExpression = rangeMinValueExpression;
	}

	/**
	 * Returns the range axis minimum value expression.
	 *
	 * @return the range axis minimum value expression 
	 **/
	public CustomExpression getRangeMinValueExpression() {
		return rangeMinValueExpression;
	}
	
	/**
	 * Sets the range axis maximum value expression.
	 *
	 * @param rangeMaxValueExpression the range axis maximum value expression
	 **/
	public void setRangeMaxValueExpression(CustomExpression rangeMaxValueExpression) {
		this.rangeMaxValueExpression = rangeMaxValueExpression;
	}

	/**
	 * Returns the range axis maximum value expression.
	 *
	 * @return the range axis maximum value expression 
	 **/
	public CustomExpression getRangeMaxValueExpression() {
		return rangeMaxValueExpression;
	}
}
