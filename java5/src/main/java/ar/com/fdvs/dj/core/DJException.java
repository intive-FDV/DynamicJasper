package ar.com.fdvs.dj.core;

public class DJException extends CoreException {

	public DJException() {
		super();
	}

	public DJException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2882998510543544480L;

	public DJException(String arg0, Throwable th) {
		super(arg0, th);
	}

	public DJException(String arg0) {
		super(arg0);
	}

}
