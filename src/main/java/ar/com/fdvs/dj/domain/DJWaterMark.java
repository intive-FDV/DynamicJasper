package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.constants.Font;

import java.awt.*;

/**
 * Created by DJ Mamana on 04/12/2015.
 */
public class DJWaterMark extends DJBaseElement {

    public static final int ANGLE_0 = 0;
    public static final int ANGLE_BOTTOM_LEFT_TO_TOP_RIGTH = 315;
    public static final int ANGLE_TOP_LEFT_TO_BOTTOM_RIGTH = 45;

    String text;

    Font font;

    Color textColor = Color.PINK;

    int angle = ANGLE_BOTTOM_LEFT_TO_TOP_RIGTH;

    public DJWaterMark(String text) {
        this(text, null, null, ANGLE_BOTTOM_LEFT_TO_TOP_RIGTH);

    }

    public DJWaterMark(String text, Font font, Color textColor, int angle) {
        this.text = text;
        if (font != null)
            this.font = font;
        else {
            this.font = (Font) Font.ARIAL_BIG.clone();
            this.font.setFontSize(80);
        }
        if (textColor!= null)
            this.textColor = textColor;
        if (angle >= 0 && angle <= 360)
            this.angle = angle;

    }

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }

    public Color getTextColor() {
        return textColor;
    }

    public int getAngle() {
        return angle;
    }
}
