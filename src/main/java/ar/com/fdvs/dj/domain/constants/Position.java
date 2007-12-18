package ar.com.fdvs.dj.domain.constants;

public class Position {

	private byte value;
	
	
	public static Position POSITION_TYPE_FIX_RELATIVE_TO_TOP = new Position((byte) 2);
	public static Position POSITION_TYPE_FIX_RELATIVE_TO_BOTTOM = new Position((byte) 3);
	public static Position POSITION_TYPE_FLOAT = new Position((byte) 1);
	
	private Position(byte value){
		this.value = value;
	}

	public byte getValue() {
		return value;
	}	
}
