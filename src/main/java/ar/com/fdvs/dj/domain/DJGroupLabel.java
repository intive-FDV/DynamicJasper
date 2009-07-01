package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.entities.Entity;

/**
 * Labels to added in groups footer or headers variables (i.e: Subtotal).
 * 
 * A simple string can be used
 * @author mamana
 *
 */
public class DJGroupLabel implements Entity {

	private String text;
	private CustomExpression labelExpression;
	private Style style;
	private LabelPosition labelPosition = LabelPosition.TOP;
	private int height = 15;
	
	public DJGroupLabel(){};

	public DJGroupLabel(String text, Style labelStyle) {
		super();
		this.text = text;
		this.style = labelStyle;
	}
	
	public DJGroupLabel(String text, Style labelStyle,
			LabelPosition labelPosition) {
		super();
		this.text = text;
		this.style = labelStyle;
		this.labelPosition = labelPosition;
	}

	public DJGroupLabel(CustomExpression labelExpression, Style labelStyle,
			LabelPosition labelPosition) {
		super();
		this.labelExpression = labelExpression;
		this.style = labelStyle;
		this.labelPosition = labelPosition;
	}	


	public LabelPosition getLabelPosition() {
		return labelPosition;
	}
	public void setLabelPosition(LabelPosition labelPosition) {
		this.labelPosition = labelPosition;
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




	
}
