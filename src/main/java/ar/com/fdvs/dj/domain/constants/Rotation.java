package ar.com.fdvs.dj.domain.constants;

public class Rotation {
	
	public static Rotation NONE = new Rotation((byte)0);
	public static Rotation LEFT = new Rotation((byte)1);
	public static Rotation RIGHT = new Rotation((byte)2);

	private byte value;
	
	private Rotation(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

}
