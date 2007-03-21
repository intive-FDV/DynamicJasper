package ar.com.fdvs.dj.domain;

import java.awt.Color;

public class DJChartOptions {

	public static final byte POSITION_FOOTER = 1;

	public static final byte POSITION_HEADER = 2;

	private boolean showLegend;
	private Color backColor;
	private int height;
	private int width;
	private boolean centered;
	private byte position;
	private int y;
	private int x;
	private boolean showLabels;

	public DJChartOptions(boolean showLegend, Color backColor, int height,
			int width, boolean centered, byte position, int y, int x,
			boolean showLabels) {
		super();
		this.showLegend = showLegend;
		this.backColor = backColor;
		this.height = height;
		this.width = width;
		this.centered = centered;
		this.position = position;
		this.y = y;
		this.x = x;
		this.showLabels = showLabels;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	public boolean isCentered() {
		return centered;
	}

	public void setCentered(boolean centered) {
		this.centered = centered;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public byte getPosition() {
		return position;
	}

	public void setPosition(byte position) {
		this.position = position;
	}

	public boolean isShowLabels() {
		return showLabels;
	}

	public void setShowLabels(boolean showLabels) {
		this.showLabels = showLabels;
	}

	public boolean isShowLegend() {
		return showLegend;
	}

	public void setShowLegend(boolean showLegend) {
		this.showLegend = showLegend;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
