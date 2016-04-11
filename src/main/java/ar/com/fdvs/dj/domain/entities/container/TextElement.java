package ar.com.fdvs.dj.domain.entities.container;

import ar.com.fdvs.dj.domain.Style;

/**
 * Created by mamana on 7/7/14.
 */
public class TextElement extends GraphicElement {

    public TextElement() {
        setWidth(50);
        setHeight(18);
    }

    public TextElement(String text) {
        this();
        this.text = text;
    }

    public TextElement(String text, int width, boolean fixedWidth, Style style) {
        this();
        this.text = text;
        setWidth(width);
        setFixedWidth(fixedWidth);
        if (style!=null)
            setStyle(style);
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
