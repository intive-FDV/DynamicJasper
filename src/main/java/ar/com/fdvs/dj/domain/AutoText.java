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
	public static final byte FOOTER = 0;
	public static final byte HEADER = 1;

	public static final byte PAGE_X_OF_Y = 0;
	public static final byte PAGE_X_SLASH_Y = 1;
	public static final byte PAGE_X = 2;
	public static final byte CREATED_ON = 3;
	public static final byte CUSTOM_MESSAGE = 4;
	
	private HorizontalBandAlignment alignment;

	private byte type;

	private byte position;
	
	private String message;

	public AutoText(byte type, byte position, HorizontalBandAlignment alignment){
		this.type = type;
		this.position = position;
		this.alignment = alignment;
	}
	
	public AutoText(String message, byte position, HorizontalBandAlignment alignment) {
		this.type = CUSTOM_MESSAGE;
		this.position = position;
		this.alignment = alignment;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
