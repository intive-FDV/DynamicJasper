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

public class HorizontalImageAlign extends BaseDomainConstant {

	private static final long serialVersionUID = 1L;

	public static HorizontalImageAlign LEFT = new HorizontalImageAlign("Left");
	public static HorizontalImageAlign RIGHT = new HorizontalImageAlign("Right");
	public static HorizontalImageAlign CENTER = new HorizontalImageAlign("Center");

	private final String name;

	public String getName() {
		return name;
	}

	private HorizontalImageAlign(String name){
		this.name = name;
	}

	public static HorizontalImageAlign fromLegacy(byte value) {
		switch (value) {
			case (byte)1: return LEFT;
			case (byte)4:  //Justified is same as center
			case (byte)2: return CENTER;
			case (byte)3: return RIGHT;
			default:
				throw new IllegalArgumentException("Value needs to be from 1 to 3");
		}

	}

}
