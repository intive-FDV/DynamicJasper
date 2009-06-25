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

import java.awt.Color;
import java.io.Serializable;

import net.sf.jasperreports.engine.design.JRDesignStyle;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.constants.Rotation;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

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

	private static final long serialVersionUID = 1L;

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
		this.font = (Font) font.clone();
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

	public JRDesignStyle transform() {

		JRDesignStyle transformedStyle = new JRDesignStyle();
		if (getBorder()!=null)
			transformedStyle.setBorder(getBorder().getValue());

		transformedStyle.setName(this.name);
		transformedStyle.setParentStyleNameReference(this.parentStyleName);

		//Borders
		if (getBorderBottom()!= null)
			transformedStyle.setBottomBorder(getBorderBottom().getValue());
		if (getBorderTop()!= null)
			transformedStyle.setTopBorder(getBorderTop().getValue());
		if (getBorderLeft()!= null)
			transformedStyle.setLeftBorder(getBorderLeft().getValue());
		if (getBorderRight()!= null)
			transformedStyle.setRightBorder(getBorderRight().getValue());

		transformedStyle.setPadding(getPadding());

		if (paddingBottom != null)
			transformedStyle.setBottomPadding(paddingBottom);
		if (paddingTop != null)
			transformedStyle.setTopPadding(paddingTop);
		if (paddingLeft != null)
			transformedStyle.setLeftPadding(paddingLeft);
		if (paddingRight != null)
			transformedStyle.setRightPadding(paddingRight);

		if (getHorizontalAlign() != null)
			transformedStyle.setHorizontalAlignment(getHorizontalAlign().getValue());

		if (getVerticalAlign() != null)
			transformedStyle.setVerticalAlignment(getVerticalAlign().getValue());

		transformedStyle.setBlankWhenNull(blankWhenNull);

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
		
		

		return transformedStyle;
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

	/**
	 * @deprecated due to miss spelling
	 * @return
	 */
//	public Integer getPaddingBotton() {
//		return paddingBottom;
//	}

	public Integer getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(Integer paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	/**
	 * @deprecated due to miss spelling
	 * @param paddingBotton
	 */
//	public void setPaddingBotton(Integer paddingBotton) {
//		this.paddingBottom = paddingBotton;
//	}

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
	 * Usefull when we need a style with a parent style, not defined properties (null ones) will be inherited
	 * from parent style
	 *
	 * @param name
	 * @return
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

}
