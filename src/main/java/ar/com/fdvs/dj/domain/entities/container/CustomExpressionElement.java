package ar.com.fdvs.dj.domain.entities.container;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.Style;


public class CustomExpressionElement extends GraphicElement {

    private CustomExpression customExpression;

    public CustomExpressionElement() {
        setWidth(50);
        setHeight(18);
    }

    public CustomExpressionElement(CustomExpression customExpression) {
        this();
        this.customExpression = customExpression;
    }

    public CustomExpressionElement(CustomExpression customExpression, int width, boolean fixedWidth, Style style) {
        this();
        this.customExpression = customExpression;
        setWidth(width);
        setFixedWidth(fixedWidth);
        if (style!=null)
            setStyle(style);
    }

    public CustomExpression getCustomExpression() {
        return customExpression;
    }

    public void setCustomExpression(CustomExpression customExpression) {
        this.customExpression = customExpression;
    }
}
