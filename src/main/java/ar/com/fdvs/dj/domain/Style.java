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

import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.LayoutUtils;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.base.JRBoxPen;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.type.*;

import java.awt.*;
import java.io.Serializable;

/**
 * Class that should be used to define the different styles in a friendly <br>
 * and strict way.<br>
 * <br>
 * Usage example:<br>
 * Style headerStyle = new Style();<br>
 * headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);<br>
 * headerStyle.setBorder(Border.PEN_2_POINT());<br>
 * headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);<br>
 * headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);<br>
 */
public class Style implements Serializable, Cloneable {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;

	private String name;
	private String parentStyleName;

	private Color backgroundColor = Color.WHITE;
	private Color textColor = Color.BLACK;

	private Font font = Font.ARIAL_MEDIUM;

	private Border border = Border.NO_BORDER();

	private Border borderTop = null;
	private Border borderBottom = null;
	private Border borderLeft = null;
	private Border borderRight = null;

	private Integer paddingBottom, paddingTop, paddingLeft, paddingRight;

	private Integer padding = 2;
	private Integer radius = 0;

	private Transparency transparency = Transparency.TRANSPARENT;

    private VerticalTextAlign verticalTextAlign = VerticalTextAlign.BOTTOM;
    private VerticalImageAlign verticalImageAlign = VerticalImageAlign.BOTTOM;

    private HorizontalTextAlign horizontalTextAlign = HorizontalTextAlign.LEFT;
    private HorizontalImageAlign horizontalImageAlign = HorizontalImageAlign.LEFT;

    private Rotation rotation = Rotation.NONE;

    private StretchTypeEnum stretchType = StretchTypeEnum.ELEMENT_GROUP_BOTTOM;

	private TextAdjustEnum textAdjust = TextAdjustEnum.STRETCH_HEIGHT;

    private boolean blankWhenNull = true;

    private String pattern;

    /**
     * If true and another style exists in the design with the same name, this style overrides the existing one.
     */
    private boolean overridesExistingStyle = false;

	public Style(){}

    public Style(String name){
    	this.name = name;
    }

    public Style(String name, String parentName){
    	this.name = name;
    	this.parentStyleName = parentName;
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
		style.setStretchType(null);
		style.SetTextAdjust(null);
		
		return style;

	}

	public static Style createBlankStyle(String name, String parent){
		Style s = createBlankStyle(name);
		s.setParentStyleName(parent);
		return s;
	}

    public boolean isOverridesExistingStyle() {
		return overridesExistingStyle;
	}

	public void setOverridesExistingStyle(boolean overridesExistingStyle) {
		this.overridesExistingStyle = overridesExistingStyle;
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

	/**
	 * @deprecated Use #Style.setHorizontalTextAlign(...) and #Style.setHorizontalImageAlign(...) instead
	 * @param horizontalAlign
	 */
	@Deprecated
	public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
		if (horizontalAlign == null) {
			horizontalTextAlign = null;
			horizontalImageAlign = null;
		}
		else {
			horizontalTextAlign = HorizontalTextAlign.fromLegacy(horizontalAlign.getValue());
			horizontalImageAlign = HorizontalImageAlign.fromLegacy(horizontalAlign.getValue());
		}
	}

	public HorizontalTextAlign getHorizontalTextAlign() {
            return horizontalTextAlign;
	}

	public void setHorizontalTextAlign(HorizontalTextAlign horizontalTextAlign) {
		this.horizontalTextAlign = horizontalTextAlign;
	}

	public HorizontalImageAlign getHorizontalImageAlign() {
            return horizontalImageAlign;
	}

	public void setHorizontalImageAlign(HorizontalImageAlign horizontalImageAlign) {
		this.horizontalImageAlign = horizontalImageAlign;
	}

	public Integer getPadding() {
		return padding;
	}

	public void setPadding(Integer padding) {
		this.padding = padding;
	}

	/**
	 * @deprecated Use {@link #getStretchType(StretchTypeEnum)}
	 * @return
	 */
	@Deprecated
	public Stretching getStreching() {
		if (StretchTypeEnum.NO_STRETCH.equals(stretchType)) {
			return Stretching.NO_STRETCH;
		}
		else if (StretchTypeEnum.RELATIVE_TO_TALLEST_OBJECT.equals(stretchType)) {
			return Stretching.RELATIVE_TO_TALLEST_OBJECT;
		}
		else if (StretchTypeEnum.RELATIVE_TO_BAND_HEIGHT.equals(stretchType)) {
			return Stretching.RELATIVE_TO_BAND_HEIGHT;
		}
		return null;
	}

	/**
	 * @deprecated Use {@link #setStretchType(StretchTypeEnum)}
	 * @param streching
	 */
	@Deprecated
	public void setStreching(Stretching streching) {
		if (Stretching.NO_STRETCH.equals(streching)) {
			stretchType = StretchTypeEnum.NO_STRETCH;
		}
		else if (Stretching.RELATIVE_TO_TALLEST_OBJECT.equals(streching)) {
			stretchType = StretchTypeEnum.RELATIVE_TO_TALLEST_OBJECT;
		}
		else if (Stretching.RELATIVE_TO_BAND_HEIGHT.equals(streching)) {
			stretchType = StretchTypeEnum.RELATIVE_TO_BAND_HEIGHT;
		}
	}

	public StretchTypeEnum getStretchType() {
		return stretchType;
	}

	public void setStretchType(StretchTypeEnum stretchType) {
		this.stretchType = stretchType;
	}

	/**
	 * @deprecated Use {@link #getTextAdjust()}
	 * @return
	 */
	@Deprecated
	public boolean isStretchWithOverflow() {
		return TextAdjustEnum.STRETCH_HEIGHT.equals(textAdjust);
	}

	/**
	 * @deprecated Use {@link #SetTextAdjust(TextAdjustEnum)}
	 * @param stretchWithOverflow
	 */
    @Deprecated
	public void setStretchWithOverflow(boolean stretchWithOverflow) {
		if (stretchWithOverflow) {
			this.textAdjust = TextAdjustEnum.STRETCH_HEIGHT;
		}		
	}

	public TextAdjustEnum getTextAdjust() {
		return textAdjust;
	}

	public void SetTextAdjust(TextAdjustEnum textAdjust) {
		this.textAdjust = textAdjust;
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

	public boolean isTransparent(){
		return this.transparency.equals(Transparency.TRANSPARENT);
	}

	public void setTransparent(boolean transparent) {
		if (transparent)
			this.setTransparency(Transparency.TRANSPARENT);
		else
			this.setTransparency(Transparency.OPAQUE);
	}

	/**
	 * @deprecated Use #Style.setVerticalTextAlign(...) and #Style.setVerticalImageAlign(...) instead
	 * @param verticalAlign
	 */
	@Deprecated
	public void setVerticalAlign(VerticalAlign verticalAlign) {
		if (verticalAlign == null) {
			verticalTextAlign = null;
			verticalImageAlign = null;
		}
		else {
			verticalTextAlign = VerticalTextAlign.fromLegacy(verticalAlign.getValue());
			verticalImageAlign = VerticalImageAlign.fromLegacy(verticalAlign.getValue());
		}
	}

	public void setVerticalTextAlign(VerticalTextAlign verticalTextAlign) {
		this.verticalTextAlign = verticalTextAlign;
	}

	public VerticalTextAlign getVerticalTextAlign() {
		return verticalTextAlign;
	}

	public void setVerticalImageAlign(VerticalImageAlign verticalImageAlign) {
		this.verticalImageAlign = verticalImageAlign;
	}

	public VerticalImageAlign getVerticalImageAlign() {
		return verticalImageAlign;
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
        JRBoxPen pen = transformedStyle.getLineBox().getPen();
        if (getBorder()!=null){
            LayoutUtils.convertBorderToPen(getBorder(),transformedStyle.getLineBox().getPen());
        }

		if (getBorderBottom()!= null)
            LayoutUtils.convertBorderToPen(getBorderBottom(),transformedStyle.getLineBox().getBottomPen());
		if (getBorderTop()!= null)
            LayoutUtils.convertBorderToPen(getBorderTop(),transformedStyle.getLineBox().getTopPen());
		if (getBorderLeft()!= null)
            LayoutUtils.convertBorderToPen(getBorderLeft(),transformedStyle.getLineBox().getLeftPen());
		if (getBorderRight()!= null)
            LayoutUtils.convertBorderToPen(getBorderRight(),transformedStyle.getLineBox().getRightPen());

		//Padding
		transformedStyle.getLineBox().setPadding(getPadding());

		if (paddingBottom != null)
            transformedStyle.getLineBox().setBottomPadding(paddingBottom);
		if (paddingTop != null)
            transformedStyle.getLineBox().setTopPadding(paddingTop);
		if (paddingLeft != null)
            transformedStyle.getLineBox().setLeftPadding(paddingLeft);
		if (paddingRight != null)
            transformedStyle.getLineBox().setRightPadding(paddingRight);


		//horizontal TEXT Aligns
		if (horizontalTextAlign != null) {
			transformedStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.getByName(horizontalTextAlign.getName()));
		}

		//Vertical TEXT aligns
		if (verticalTextAlign != null){
			transformedStyle.setVerticalTextAlign(VerticalTextAlignEnum.getByName(verticalTextAlign.getName()));
		}

		//Horizontal Image align
		if (horizontalImageAlign != null) {
			transformedStyle.setHorizontalImageAlign(HorizontalImageAlignEnum.getByName(horizontalImageAlign.getName()));
		}
		//Vertical Image align
		if (verticalImageAlign != null){
			transformedStyle.setVerticalImageAlign(VerticalImageAlignEnum.getByName(verticalImageAlign.getName()));
		}

		transformedStyle.setBlankWhenNull(Boolean.valueOf(blankWhenNull));

		//Font
		if (font != null) {
			transformedStyle.setFontName(font.getFontName());
			transformedStyle.setFontSize(font.getFontSize());
			transformedStyle.setBold(Boolean.valueOf(font.isBold()));
			transformedStyle.setItalic(Boolean.valueOf(font.isItalic()));
			transformedStyle.setUnderline(Boolean.valueOf(font.isUnderline()));
			transformedStyle.setPdfFontName(font.getPdfFontName());
			transformedStyle.setPdfEmbedded(Boolean.valueOf(font.isPdfFontEmbedded()));
			transformedStyle.setPdfEncoding(font.getPdfFontEncoding());
		}

		transformedStyle.setBackcolor(getBackgroundColor());
		transformedStyle.setForecolor(getTextColor());

		if (getTransparency() != null)
			transformedStyle.setMode(ModeEnum.getByValue( getTransparency().getValue() ));

		if (getRotation() != null)
			transformedStyle.setRotation(RotationEnum.getByValue( getRotation().getValue() ));

		if (getRadius() != null)
			transformedStyle.setRadius(Integer.valueOf(getRadius().intValue()));

		transformedStyle.setPattern(this.pattern);

		/*
		  This values are needed when exporting to JRXML
		 */
        //TODO Check if this is still necessary
        /*transformedStyle.setPen((byte)0);
		transformedStyle.setFill((byte)1);
		transformedStyle.setScaleImage(ImageScaleMode.NO_RESIZE.getValue());*/

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

    /**
     * @deprecated use #Style.getBorder().getColor() instead
     * @return
     */
    @Deprecated
	public Color getBorderColor() {
        if (getBorder() == null)
            return null;
		return getBorder().getColor();
	}

    /**
     * @deprecated Use #Style.setBorder(...) instead
     * @param borderColor
     */
    @Deprecated
	public void setBorderColor(Color borderColor) {
        if (getBorder() == null)
            return;

        this.getBorder().setColor(borderColor);
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
