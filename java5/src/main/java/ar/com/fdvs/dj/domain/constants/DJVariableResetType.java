package ar.com.fdvs.dj.domain.constants;

public class DJVariableResetType extends BaseDomainConstant {
	
	private static final long serialVersionUID = 1L;
	
	private byte value;
	
	public static final DJVariableResetType REPORT	= new DJVariableResetType((byte)1);
	public static final DJVariableResetType PAGE 	= new DJVariableResetType((byte)2);
	public static final DJVariableResetType COLUMN 	= new DJVariableResetType((byte)3);
	public static final DJVariableResetType GROUP 	= new DJVariableResetType((byte)4);
	public static final DJVariableResetType NONE 	= new DJVariableResetType((byte)5);
	
	public DJVariableResetType(byte val){
		this.value = val;
	}

	public byte getValue() {
		return value;
	}

}
