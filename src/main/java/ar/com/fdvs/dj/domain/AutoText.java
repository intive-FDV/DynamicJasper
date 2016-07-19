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

/**
 *
 */
package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.domain.entities.Entity;
import net.sf.jasperreports.engine.JRExpression;
/**
 * @author msimone
 *
 */
public class AutoText extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public static final Integer WIDTH_NOT_SET = new Integer(Integer.MIN_VALUE);
	public static final Integer DEFAULT_WIDTH = new Integer(80);
	public static final Integer DEFAULT_WIDTH2 = new Integer(30);
	public static final byte POSITION_FOOTER = 0;
	public static final byte POSITION_HEADER = 1;

	public static final byte AUTOTEXT_PAGE_X_OF_Y = 0;
	public static final byte AUTOTEXT_PAGE_X_SLASH_Y = 1;
	public static final byte AUTOTEXT_PAGE_X = 2;
	public static final byte AUTOTEXT_CREATED_ON = 3;
	public static final byte AUTOTEXT_CUSTOM_MESSAGE = 4;
	public static final byte AUTOTEXT_JREXPRESSION = 5;
	
	/**
	 *@Deprecated due to miss spell
	 *use ALIGNMENT_LEFT 
	 */
	public static final byte ALIGMENT_LEFT = 1;
	/**
	 *@Deprecated due to miss spell
	 *use ALIGNMENT_CENTER
	 */
	public static final byte ALIGMENT_CENTER = 2;
	/**
	 *@Deprecated due to miss spell
	 *use ALIGNMENT_RIGHT
	 */
	public static final byte ALIGMENT_RIGHT = 3;

	public static final byte ALIGNMENT_LEFT = 1;
	public static final byte ALIGNMENT_CENTER = 2;
	public static final byte ALIGNMENT_RIGHT = 3;
	
	public static final byte PATTERN_DATE_DATE_ONLY = 1;
	public static final byte PATTERN_DATE_TIME_ONLY = 2;
	public static final byte PATTERN_DATE_DATE_TIME = 3;

	private HorizontalBandAlignment alignment;

	private byte type;

	private byte position;

	private String messageKey;
	private JRExpression expresion;
	private int pageOffset=0;
	private boolean printPageLegend;
	private byte pattern; //Applies for CREATED_ON, its the pattern used for dates

	private Integer height = new Integer(15);

	private Style style = null;

	/**
	 * 
	 */
	private Integer width = WIDTH_NOT_SET;
	
	/**
	 * For autotexts that consists in two parts (like: "page x of y" and "x / y"). <br>
	 * These autotext are compound by 2 text-fields with different render time (one is immediate, the other generally "time-report")<br> 
	 * The first part is the "immediate" second part is the "y" (time-report)<br>
	 * width2 defines how width is the second part. This is for fine tuning of the layout. depending on the size of report
	 * the total pages can be a small or big number, making this width wide enough should prevent the text to
	 * override the space given 
	 */
	private Integer width2 = WIDTH_NOT_SET;
	

	/**
	 * tells if the API can modify the with if needed
	 */
	private boolean fixedWith = true;
	private BooleanExpression printWhenExpression;
	
	public boolean isFixedWith() {
		return fixedWith;
	}
	public void setFixedWith(boolean fixedWith) {
		this.fixedWith = fixedWith;
	}
	/**
	 * returns the style
	 * @return can be null if no style has been set
	 */
	public Style getStyle() {
		return style;
	}
	public AutoText setStyle(Style newStyle) {
		style = newStyle;
		return this;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getWidth2() {
		return width2;
	}
	public void setWidth2(Integer width2) {
		this.width2 = width2;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public AutoText(byte type, byte position, HorizontalBandAlignment alignment){
		this.type = type;
		this.position = position;
		this.alignment = alignment;
	}
	public AutoText(byte type, byte position, HorizontalBandAlignment alignment,byte pattern){
		this.type = type;
		this.position = position;
		this.alignment = alignment;
		this.pattern = pattern;
	}
	public AutoText(byte type, byte position, HorizontalBandAlignment alignment,byte pattern, int width){
		this.type = type;
		this.position = position;
		this.alignment = alignment;
		this.pattern = pattern;
		this.width = new Integer(width);
	}
	public AutoText(byte type, byte position, HorizontalBandAlignment alignment,byte pattern, int width, int width2){
		this.type = type;
		this.position = position;
		this.alignment = alignment;
		this.pattern = pattern;
		this.width = new Integer(width);
		this.width2 = new Integer(width2);
	}

	public AutoText(String message, byte position, HorizontalBandAlignment alignment) {
		this.type = AUTOTEXT_CUSTOM_MESSAGE;
		this.position = position;
		this.alignment = alignment;
		this.messageKey = message;
		this.fixedWith = false;
	}

	public AutoText(String message, byte position, HorizontalBandAlignment alignment, Integer with) {
		this.type = AUTOTEXT_CUSTOM_MESSAGE;
		this.position = position;
		this.alignment = alignment;
		this.messageKey = message;
		this.width = with;
		this.fixedWith = false;
	}

	public AutoText(JRExpression expression, byte position, HorizontalBandAlignment alignment, Integer with) {
		this.type = AUTOTEXT_JREXPRESSION;
		this.position = position;
		this.alignment = alignment;
		this.expresion = expression;
		this.width = with;
		this.fixedWith = false;
	}

	public HorizontalBandAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(HorizontalBandAlignment alignment) {
		this.alignment = alignment;
	}

	public byte getPosition() {
		return position;
	}

	public void setPosition(byte position) {
		this.position = position;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String message) {
		this.messageKey = message;
	}

	public byte getPattern() {
		return pattern;
	}
	
	public void setPrintWhenExpression(BooleanExpression printWhenExpression) {
		this.printWhenExpression = printWhenExpression;
	}

	public BooleanExpression getPrintWhenExpression() {
		return printWhenExpression;
	}
	
	public JRExpression getExpresion() {
		return expresion;
	}

	public void setExpresion(JRExpression expresion) {
		this.expresion = expresion;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageStartNumber) {
		this.pageOffset = pageStartNumber;
	}

	public boolean isPrintPageLegend() {
		return printPageLegend;
	}

	public void setPrintPageLegend(boolean printPageLegend) {
		this.printPageLegend = printPageLegend;
	}
}
