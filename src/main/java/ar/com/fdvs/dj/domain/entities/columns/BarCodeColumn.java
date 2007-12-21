package ar.com.fdvs.dj.domain.entities.columns;

public class BarCodeColumn extends ImageColumn {
	
	private int barcodeType = 0;
	private boolean showText = false;
	private boolean checkSum = false;
	private String applicationIdentifier = null;

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
