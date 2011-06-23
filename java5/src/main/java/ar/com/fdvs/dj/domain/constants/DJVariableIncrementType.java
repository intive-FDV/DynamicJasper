package ar.com.fdvs.dj.domain.constants;

public class DJVariableIncrementType extends BaseDomainConstant {
	
	private static final long serialVersionUID = 1L;
	
	private byte value;
	
	public static final DJVariableIncrementType REPORT	= new DJVariableIncrementType((byte)1);
	public static final DJVariableIncrementType PAGE 	= new DJVariableIncrementType((byte)2);
	public static final DJVariableIncrementType COLUMN 	= new DJVariableIncrementType((byte)3);
	public static final DJVariableIncrementType GROUP 	= new DJVariableIncrementType((byte)4);
	public static final DJVariableIncrementType NONE 	= new DJVariableIncrementType((byte)5);
	
	public DJVariableIncrementType(byte val){
		this.value = val;
	}

	public byte getValue() {
		return value;
	}

}
