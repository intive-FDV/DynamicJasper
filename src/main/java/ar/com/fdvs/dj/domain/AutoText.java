/**
 * 
 */
package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;

/**
 * @author msimone
 * 
 */
public class AutoText {
	public static final byte POSITION_FOOTER = 0;
	public static final byte POSITION_HEADER = 1;

	public static final byte AUTOTEXT_PAGE_X_OF_Y = 0;
	public static final byte AUTOTEXT_PAGE_X_SLASH_Y = 1;
	public static final byte AUTOTEXT_PAGE_X = 2;
	public static final byte AUTOTEXT_CREATED_ON = 3;
	public static final byte AUTOTEXT_CUSTOM_MESSAGE = 4;

	public static final byte ALIGMENT_LEFT = 1;
	public static final byte ALIGMENT_CENTER = 2;
	public static final byte ALIGMENT_RIGHT = 3;

	public static final byte PATTERN_DATE_DATE_ONLY = 1;
	public static final byte PATTERN_DATE_TIME_ONLY = 2;
	public static final byte PATTERN_DATE_DATE_TIME = 3;
	
	private HorizontalBandAlignment alignment;

	private byte type;

	private byte position;
	
	private String messageKey;

	private byte pattern; //Applies for CREATED_ON, its the pattern used for dates
	
	private Integer height = new Integer(15); 

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
	
	public AutoText(String message, byte position, HorizontalBandAlignment alignment) {
		this.type = AUTOTEXT_CUSTOM_MESSAGE;
		this.position = position;
		this.alignment = alignment;
		this.messageKey = message;
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
}
