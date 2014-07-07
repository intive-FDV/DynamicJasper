package ar.com.fdvs.dj.domain.entities.container;

import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.Style;

/**
 * Created by mamana on 7/7/14.
 */
public class GraphicElement extends DJBaseElement {

    private Integer posX = new Integer(0);
    private Integer posY = new Integer(0);
    private Integer width = new Integer(100);
    private Integer height = new Integer(30);
    private Boolean fixedWidth = Boolean.FALSE;
    private Style style = new Style();

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Boolean getFixedWidth() {
        return fixedWidth;
    }

    public void setFixedWidth(Boolean fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
