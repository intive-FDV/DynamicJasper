package ar.com.fdvs.dj.domain.builders;

/**
 * This exception can be thrown by any builder used in DynamicJasper
 * @author djmamana
 *
 */
public class DJBuilderException extends Exception {

	private static final long serialVersionUID = 1070114283725367932L;

	public DJBuilderException() {
		super();
	}

	public DJBuilderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DJBuilderException(String arg0) {
		super(arg0);
	}

	public DJBuilderException(Throwable arg0) {
		super(arg0);
	}
	
	

}
