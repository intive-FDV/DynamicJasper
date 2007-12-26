package ar.com.fdvs.dj.domain.entities.columns;

import ar.com.fdvs.dj.core.BarcodeTypes;

public class BarCodeColumn extends ImageColumn implements BarcodeTypes {
	
	private int barcodeType = _2_OF_7;
	private boolean showText = false;
	private boolean checkSum = false;
	private String applicationIdentifier = null;
	private boolean haltWhenException = false;

	public boolean isHaltWhenException() {
		return haltWhenException;
	}

	public void setHaltWhenException(boolean haltWhenException) {
		this.haltWhenException = haltWhenException;
	}

	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}

	public void setApplicationIdentifier(String applicationIdentifier) {
		this.applicationIdentifier = applicationIdentifier;
	}

	public boolean isShowText() {
		return showText;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	public int getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(int barcodeType) {
		this.barcodeType = barcodeType;
	}

	public boolean isCheckSum() {
		return checkSum;
	}

	public void setCheckSum(boolean checkSum) {
		this.checkSum = checkSum;
	}

}
