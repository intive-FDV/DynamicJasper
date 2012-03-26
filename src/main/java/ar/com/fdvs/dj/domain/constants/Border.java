/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.domain.constants;

import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.engine.type.PenEnum;

import java.awt.*;

/**
 * See value of constants here: 
 * http://jasperreports.sourceforge.net/api/constant-values.html
 * @author mamana
 *
 */
public class Border  extends BaseDomainConstant {

	private static final long serialVersionUID = 1L;
	
	public static Border NO_BORDER = new Border(PenEnum.ONE_POINT.getValue());
	public static Border THIN = new Border( PenEnum.THIN.getValue() );

	public static Border PEN_1_POINT = new Border( PenEnum.ONE_POINT.getValue() );
	public static Border PEN_2_POINT = new Border( PenEnum.TWO_POINT.getValue() );
	public static Border PEN_4_POINT = new Border( PenEnum.FOUR_POINT.getValue() );
	public static Border DOTTED = new Border( PenEnum.DOTTED.getValue() );

    public static byte BORDER_STYLE_SOLID = LineStyleEnum.SOLID.getValue();
    public static byte BORDER_STYLE_DASHED = LineStyleEnum.DASHED.getValue();
    public static byte BORDER_STYLE_DOTTED = LineStyleEnum.DOTTED.getValue();
    public static byte BORDER_STYLE_DOUBLE = LineStyleEnum.DOUBLE.getValue();

    public static float BORDER_WIDTH_NONE = 0f;
    public static float BORDER_WIDTH_THIN = 0.2f;
    public static float BORDER_WIDTH_1POINT = 0.5f;
    public static float BORDER_WIDTH_2POINT = 1f;
    public static float BORDER_WIDTH_4POINT = 2f;


	private byte value;
    
    private float width = BORDER_WIDTH_THIN;
    private Color color;
    private byte lineStyle = LineStyleEnum.SOLID.getValue();


	public byte getValue() {
		return value;
	}

	private Border(byte value){
		this.value = value;
	}
    
    public Border(float width, byte lineStyle) {
        this.lineStyle = lineStyle;
        this.width = width; 
    }

    public Border(float width, byte lineStyle, Color color) {
        this.lineStyle = lineStyle;
        this.width = width;
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public byte getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(byte lineStyle) {
        this.lineStyle = lineStyle;
    }
}
