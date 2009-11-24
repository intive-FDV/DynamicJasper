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
public class DJGroupLabel extends DJLabel {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	
	protected LabelPosition labelPosition = LabelPosition.TOP;
	
	public DJGroupLabel(){};

	public DJGroupLabel(String text, Style labelStyle) {
		super(text,labelStyle);
	}
	
	public DJGroupLabel(String text, Style labelStyle,
			LabelPosition labelPosition) {
		super(text,labelStyle,labelPosition);
		this.labelPosition = labelPosition;
	}

	public DJGroupLabel(CustomExpression labelExpression, Style labelStyle,
			LabelPosition labelPosition) {
		super(labelExpression,labelStyle);
		this.labelPosition = labelPosition;
	}	


	public LabelPosition getLabelPosition() {
		return labelPosition;
	}

	public void setLabelPosition(LabelPosition labelPosition) {
		this.labelPosition = labelPosition;
	}
	
}
