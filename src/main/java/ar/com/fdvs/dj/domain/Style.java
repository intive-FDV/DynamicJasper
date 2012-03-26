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

import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.Entity;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;

import java.awt.*;
import java.io.Serializable;

/**
 * Class that should be used to define the different styles in a friendly </br>
 * and strict way.</br>
 * </br>
 * Usage example:</br>
 * Style headerStyle = new Style();</br>
 * headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);</br>
 * headerStyle.setBorder(Border.PEN_2_POINT);</br>
 * headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);</br>
 * headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);</br>
 */
public class Style implements Serializable, Cloneable {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;

	private String name;
	private String parentStyleName;

	private Color backgroundColor = Color.WHITE;
	private Color textColor = Color.BLACK;
	private Color borderColor = Color.BLACK;

	private Font font = Font.ARIAL_MEDIUM;

	private Border border = Border.NO_BORDER;

	private Border borderTop = null;
	private Border borderBottom = null;
	private Border borderLeft = null;
	private Border borderRight = null;

	private Integer paddingBottom, paddingTop, paddingLeft, paddingRight;

	private Integer padding = new Integer(2);
	private Integer radius = new Integer(0);

	private Transparency transparency = Transparency.TRANSPARENT;

    private VerticalAlign verticalAlign = VerticalAlign.BOTTOM;
    private HorizontalAlign horizontalAlign = HorizontalAlign.LEFT;
    private Rotation rotation = Rotation.NONE;

    private Stretching streching = Stretching.RELATIVE_TO_TALLEST_OBJECT;

    private boolean stretchWithOverflow = true;
    private boolean blankWhenNull = true;

    private String pattern;

    /**
     * If true and another style exists in the design with the same name, this style overrides the existing one.
     */
    private boolean overridesExistingStyle = false;

    public boolean isOverridesExistingStyle() {
		return overridesExistingStyle;
	}

	public void setOverridesExistingStyle(boolean overridesExistingStyle) {
		this.overridesExistingStyle = overridesExistingStyle;
	}

	public Style(){}

    public Style(String name){
    	this.name = name;
    }
    public Style(String name, String parentName){
    	this.name = name;
    	this.parentStyleName = parentName;
    }

	public boolean isBlankWhenNull() {
		return blankWhenNull;
	}

	public void setBlankWhenNull(boolean blankWhenNull) {
		this.blankWhenNull = blankWhenNull;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		if (font != null)
			this.font = (Font) font.clone();
		else this.font = null;
	}

	public HorizontalAlign getHorizontalAlign() {
		return horizontalAlign;
	}

	public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}

	public Integer getPadding() {
		return padding;
	}

	public void setPadding(Integer padding) {
		this.padding = padding;
	}

	public Stretching getStreching() {
		return streching;
	}

	public void setStreching(Stretching streching) {
		this.streching = streching;
	}

	public boolean isStretchWithOverflow() {
		return stretchWithOverflow;
	}

	public void setStretchWithOverflow(boolean stretchWithOverflow) {
		this.stretchWithOverflow = stretchWithOverflow;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Transparency getTransparency() {
		return transparency;
	}

	public void setTransparency(Transparency transparency) {
		this.transparency = transparency;
	}

	public void setTransparent(boolean transparent) {
		if (transparent)
			this.setTransparency(Transparency.TRANSPARENT);
		else
			this.setTransparency(Transparency.OPAQUE);
	}

	public boolean isTransparent(){
		return this.transparency.equals(Transparency.TRANSPARENT);
	}

	public VerticalAlign getVerticalAlign() {
		return verticalAlign;
	}

	public void setVerticalAlign(VerticalAlign verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public JRDesignConditionalStyle transformAsConditinalStyle() {
		JRDesignConditionalStyle ret = new JRDesignConditionalStyle();
		setJRBaseStyleProperties(ret);
		return ret;
		
	}

	public JRDesignStyle transform() {
		JRDesignStyle transformedStyle = new JRDesignStyle();
		transformedStyle.setName(this.name);
		transformedStyle.setParentStyleNameReference(this.parentStyleName);
		setJRBaseStyleProperties(transformedStyle);
		return transformedStyle;
	}

	protected void setJRBaseStyleProperties(JRBaseStyle transformedStyle) {
		if (getBorder()!=null)
			transformedStyle.setBorder(getBorder().getValue());

		//Borders
		if (getBorderBottom()!= null)
			transformedStyle.setBottomBorder(getBorderBottom().getValue());
		if (getBorderTop()!= null)
			transformedStyle.setTopBorder(getBorderTop().getValue());
		if (getBorderLeft()!= null)
			transformedStyle.setLeftBorder(getBorderLeft().getValue());
		if (getBorderRight()!= null)
			transformedStyle.setRightBorder(getBorderRight().getValue());

		//Padding
		transformedStyle.setPadding(getPadding());

		if (paddingBottom != null)
			transformedStyle.setBottomPadding(paddingBottom);
		if (paddingTop != null)
			transformedStyle.setTopPadding(paddingTop);
		if (paddingLeft != null)
			transformedStyle.setLeftPadding(paddingLeft);
		if (paddingRight != null)
			transformedStyle.setRightPadding(paddingRight);

		//Aligns
		if (getHorizontalAlign() != null)
			transformedStyle.setHorizontalAlignment(getHorizontalAlign().getValue());

		if (getVerticalAlign() != null)
			transformedStyle.setVerticalAlignment(getVerticalAlign().getValue());

		transformedStyle.setBlankWhenNull(blankWhenNull);

		//Font
		if (font != null) {
			transformedStyle.setFontName(font.getFontName());
			transformedStyle.setFontSize(font.getFontSize());
			transformedStyle.setBold(font.isBold());
			transformedStyle.setItalic(font.isItalic());
			transformedStyle.setUnderline(font.isUnderline());
			transformedStyle.setPdfFontName(font.getPdfFontName());
			transformedStyle.setPdfEmbedded(font.isPdfFontEmbedded());
			transformedStyle.setPdfEncoding(font.getPdfFontEncoding());
		}

		transformedStyle.setBackcolor(getBackgroundColor());
		transformedStyle.setForecolor(getTextColor());
		transformedStyle.setBorderColor(borderColor);
		if (getTransparency() != null)
			transformedStyle.setMode(getTransparency().getValue());

		if (getRotation() != null)
			transformedStyle.setRotation(getRotation().getValue());

		if (getRadius() != null)
			transformedStyle.setRadius(getRadius().intValue());

		transformedStyle.setPattern(this.pattern);

		/**
		 * This values are needed when exporting to JRXML
		 */
		transformedStyle.setPen((byte)0);
		transformedStyle.setFill((byte)1);
		transformedStyle.setScaleImage(ImageScaleMode.NO_RESIZE.getValue());
	
	}

	public Border getBorderBottom() {
		return borderBottom;
	}

	public void setBorderBottom(Border borderBottom) {
		this.borderBottom = borderBottom;
	}

	public Border getBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(Border borderLeft) {
		this.borderLeft = borderLeft;
	}

	public Border getBorderRight() {
		return borderRight;
	}

	public void setBorderRight(Border borderRight) {
		this.borderRight = borderRight;
	}

	public Border getBorderTop() {
		return borderTop;
	}

	public void setBorderTop(Border borderTop) {
		this.borderTop = borderTop;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(Integer paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

    public Integer getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(Integer paddingTop) {
		this.paddingTop = paddingTop;
	}

	public Integer getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(Integer paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public Integer getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(Integer paddingRight) {
		this.paddingRight = paddingRight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentStyleName() {
		return parentStyleName;
	}

	public void setParentStyleName(String parentStyleName) {
		this.parentStyleName = parentStyleName;
	}

	/**
	 * Creates a blank style (no default values).
	 * Useful when we need a style with a parent style, not defined properties (null ones) will be inherited
	 * from parent style
	 *
	 * @param name  style name
	 * @return  Style
	 */
	public static Style createBlankStyle(String name){
		Style style = new Style(name);

		style.setBackgroundColor(null);
		style.setBorderColor(null);
		style.setTransparency(null);
		style.setTextColor(null);
		style.setBorder(null);
		style.setFont(null);
		style.setPadding(null);
		style.setRadius(null);
		style.setVerticalAlign(null);
		style.setHorizontalAlign(null);
		style.setRotation(null);
		style.setStreching(null);

		return style;

	}

	public static Style createBlankStyle(String name, String parent){
		Style s = createBlankStyle(name);
		s.setParentStyleName(parent);
		return s;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Object clone() throws CloneNotSupportedException {
		Style style = (Style) super.clone();
		style.setFont(this.font);
		return style;
	}
}
