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

package ar.com.fdvs.dj.test.util;

import java.awt.Color;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

public class StyleFactory {

	public static Style createDetailStyle(String name){
		Style detailStyle = new Style(name);
		detailStyle.setVerticalAlign(VerticalAlign.TOP);
		return detailStyle;
	}

	public static Style createGroupTileStyle(String name){
		Style groupTitleStyle = new Style(name);
		groupTitleStyle.setFont(Font.ARIAL_BIG);
		return groupTitleStyle;
	}

	public static Style createGroupDetailStyle(String name){
		Style style = new Style(name);
		style.setFont(Font.ARIAL_BIG_BOLD);
		style.setBorderBottom(Border.THIN);
		style.setVerticalAlign(VerticalAlign.TOP);
		return style;
	}

	public static Style createHeaderStyle(String name){
		Style headerStyle = new Style(name);
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBackgroundColor(Color.gray);
		headerStyle.setTextColor(Color.white);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);
		return headerStyle;
	}
	public static Style createHeaderStyle2(String name){
		Style headerStyle = new Style(name);
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setBorderBottom(Border.THIN);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
		headerStyle.setTransparency(Transparency.OPAQUE);
		return headerStyle;
	}

	public static Style createGroupVariableStyle(String name){
		Style style = new Style(name);
		style.setFont(Font.ARIAL_MEDIUM_BOLD);
		style.setBorderTop(Border.THIN);
		style.setHorizontalAlign(HorizontalAlign.RIGHT);
		style.setVerticalAlign(VerticalAlign.MIDDLE);
		style.setTextColor(new Color(50,50,150));
		return style;
	}

	public static Style createGroup2VariableStyle(String name){
		Style style = new Style(name);
		style.setFont(Font.ARIAL_MEDIUM_BOLD);
		style.setTextColor(new Color(150,150,150));
		style.setHorizontalAlign(HorizontalAlign.RIGHT);
		style.setVerticalAlign(VerticalAlign.MIDDLE);
		return style;
	}

	public static Style createTitleStyle(String name){
		Style titleStyle = new Style(name);
		titleStyle.setFont(new Font(18, Font._FONT_VERDANA, true));
		return titleStyle;
	}

}
