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

/**
 * See value of constants here: 
 * http://jasperreports.sourceforge.net/api/constant-values.html
 * @author mamana
 *
 */
public class Border  extends BaseDomainConstant {

	private static final long serialVersionUID = 1L;
	
	public static Border NO_BORDER = new Border(JRGraphicElement.PEN_NONE);
	public static Border THIN = new Border(JRGraphicElement.PEN_THIN);

	public static Border PEN_1_POINT = new Border(JRGraphicElement.PEN_1_POINT);
	public static Border PEN_2_POINT = new Border(JRGraphicElement.PEN_2_POINT);
	public static Border PEN_4_POINT = new Border(JRGraphicElement.PEN_4_POINT);
	public static Border DOTTED = new Border(JRGraphicElement.PEN_DOTTED);

	private byte value;

	public byte getValue() {
		return value;
	}

	private Border(byte value){
		this.value = value;
	}

}
