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

/**
 *
 */
package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.domain.entities.Entity;

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
}
