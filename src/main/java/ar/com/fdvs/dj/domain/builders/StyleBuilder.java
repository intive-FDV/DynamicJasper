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

import java.awt.Color;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Rotation;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

public class StyleBuilder {

	Style style = null;

	public StyleBuilder(boolean blank) {
		super();

		if (blank)
			style = Style.createBlankStyle(null);
		else
			style = new Style();

	}

	public StyleBuilder(boolean blank, String name) {
		super();

		if (blank)
			style = Style.createBlankStyle(null);
		else
			style = new Style();

		style.setName(name);
	}

	public StyleBuilder(boolean blank, String name, String parentName) {
		super();

		if (blank)
			style = Style.createBlankStyle(null);
		else
			style = new Style();

		style.setName(name);
		style.setParentStyleName(parentName);
	}

	public Style build(){
		return style;
	}


	public StyleBuilder setName(String name){
		style.setName(name);
		return this;
	}

	public StyleBuilder setPattern(String pattern){
		style.setPattern(pattern);
		return this;
	}

	public StyleBuilder setFont(Font font){
		style.setFont(font);
		return this;
	}

	public StyleBuilder setHorizontalAlign(HorizontalAlign horizontalAlign){
		style.setHorizontalAlign(horizontalAlign);
		return this;
	}

	public StyleBuilder setVerticalAlign(VerticalAlign verticalAlign){
		style.setVerticalAlign(verticalAlign);
		return this;
	}

	public StyleBuilder setStretching(Stretching streching){
		style.setStreching(streching);
		return this;
	}

	public StyleBuilder setTextColor(Color textColor){
		style.setTextColor(textColor);
		return this;
	}

	public StyleBuilder setBackgroundColor(Color backgroundColor){
		style.setBackgroundColor(backgroundColor);
		return this;
	}

	public StyleBuilder setTransparency(Transparency transparency){
		style.setTransparency(transparency);
		return this;
	}

	public StyleBuilder setTransparent(boolean isTransparent){
		style.setTransparent(isTransparent);
		return this;
	}

	public StyleBuilder setBorderBottom(Border borderBottom) {
		style.setBorderBottom(borderBottom);
		return this;
	}

	public StyleBuilder setBorderColor(Color borderColor) {
		style.setBorderColor(borderColor);
		return this;
	}

	public StyleBuilder setBorderLeft(Border borderLeft) {
		style.setBorderLeft(borderLeft);
		return this;
	}

	public StyleBuilder setBorderRight(Border borderRight) {
		style.setBorderRight(borderRight);
		return this;
	}

	public StyleBuilder setBorderTop(Border borderTop) {
		style.setBorderTop(borderTop);
		return this;
	}

	public StyleBuilder setPadding(Integer padding) {
		style.setPadding(padding);
		return this;
	}

	public StyleBuilder setPaddingBottom(Integer paddingBottom) {
		style.setPaddingBottom(paddingBottom);
		return this;
	}

	/**
	 * @deprecated due to miss spelling
	 * @param paddingBotton
	 * @return
	 */
	public StyleBuilder setPaddingBotton(Integer paddingBotton) {
		return setPaddingBottom(paddingBotton);
	}

	public StyleBuilder setPaddingLeft(Integer paddingLeft) {
		style.setPaddingLeft(paddingLeft);
		return this;
	}

	public StyleBuilder setPaddingRight(Integer paddingRight) {
		style.setPaddingRight(paddingRight);
		return this;
	}

	public StyleBuilder setPaddingTop(Integer paddingTop) {
		style.setPaddingTop(paddingTop);
		return this;
	}

	public StyleBuilder setParentStyleName(String parentStyleName) {
		style.setParentStyleName(parentStyleName);
		return this;
	}


	public StyleBuilder setRotation(Rotation rotation) {
		style.setRotation(rotation);
		return this;
	}

	public StyleBuilder setBorder(Border border) {
		style.setBorder(border);
		return this;
	}
	
	public StyleBuilder setStretchWithOverflow(boolean stretchWithOverflow) {
		style.setStretchWithOverflow(stretchWithOverflow);
		return this;
	}
	


}
