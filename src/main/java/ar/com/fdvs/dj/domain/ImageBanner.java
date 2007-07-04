package ar.com.fdvs.dj.domain;

public class ImageBanner {
	
	public static final byte ALIGN_LEFT = 0;
	public static final byte ALIGN_RIGHT = 1;
	public static final byte ALIGN_CENTER = 2;
	
	private String imagePath;
	private int width = 0;
	private int height= 0;
	private byte align = 0;
	
	public ImageBanner(){
	};
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public byte getAlign() {
		return align;
	}
	public void setAlign(byte orientation) {
		this.align = orientation;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public ImageBanner(String imagePath, int width, int height, byte align) {
		this.imagePath = imagePath;
		this.width = width;
		this.height = height;
		this.align = align;
	}

}
