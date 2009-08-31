package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.entities.Entity;

public class DJLabel extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	protected boolean isJasperExpression = false;

	protected String text;
	protected CustomExpression labelExpression;
	protected Style style;	
	protected int height = 15;
	
	public DJLabel(){};

	public DJLabel(String text, Style labelStyle) {
		super();
		this.text = text;
		this.style = labelStyle;
	}

	public DJLabel(String text, Style labelStyle, boolean isJasperExpression) {
		super();
		this.text = text;
		this.style = labelStyle;
		this.isJasperExpression = isJasperExpression;
	}
	
	public DJLabel(String text, Style labelStyle,
			LabelPosition labelPosition) {
		super();
		this.text = text;
		this.style = labelStyle;
	}

	public DJLabel(CustomExpression labelExpression, Style labelStyle) {
		super();
		this.labelExpression = labelExpression;
		this.style = labelStyle;
	}	

	public CustomExpression getLabelExpression() {
		return labelExpression;
	}


	public void setLabelExpression(CustomExpression labelExpression) {
		this.labelExpression = labelExpression;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isJasperExpression() {
		return isJasperExpression;
	}

	public void setJasperExpression(boolean isJasperExpression) {
		this.isJasperExpression = isJasperExpression;
	}


}
