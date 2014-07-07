package ar.com.fdvs.dj.domain.entities.container;

/**
 * Created by mamana on 7/7/14.
 */
public class StaticTextElement extends GraphicElement {

    public StaticTextElement() {
        setWidth(50);
        setHeight(30);
    }

    public StaticTextElement(String text) {
        this();
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
