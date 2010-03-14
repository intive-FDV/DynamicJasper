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

package ar.com.fdvs.dj.domain;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import ar.com.fdvs.dj.domain.entities.Entity;

/**
 * Some color templates to be used by charts.
 *
 * @author msimone
 */
public class DJChartColors extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public static List pale() {
		Color[] colors = { new Color(0xFFA07A), new Color(0xD2B48C),
				new Color(0xFFD700), new Color(0xFFE4E1), new Color(0xE0FFFF),
				new Color(0x90EE90) };
		return Arrays.asList(colors);
	}

	public static List msOfficePurples() {
		Color[] colors = { new Color(0x660066), new Color(0xFF8080),
				new Color(0x9999FF), new Color(0x993366), new Color(0xFFFFCC),
				new Color(0xCCFFFF) };
		return Arrays.asList(colors);
	}

	public static List simpleColors() {
		Color[] colors = { new Color(0xFD0100), new Color(0x32CB32),
				new Color(0x007EF8), new Color(0xFFFF01), new Color(0xAF2421),
				new Color(0xFF7F02) };
		return Arrays.asList(colors);
	}

	public static List desert() {
		Color[] colors = { new Color(0xFFFFD4), new Color(0xFED98E),
				new Color(0xFE9929), new Color(0xD95F0E), new Color(0xF03B20),
				new Color(0x993404) };
		return Arrays.asList(colors);
	}

	public static List justAnotherColorScheme() {
		Color[] colors = { new Color(0xD7191C), new Color(0xFDAE61),
				new Color(0xFFFFBF), new Color(0xABDDA4), new Color(0x2B83BA),
				new Color(0xFF7F02) };
		return Arrays.asList(colors);
	}

	public static List googleAnalytics() {
		Color[] colors = { new Color(0x9BBDDE), new Color(0xFFBC46),
				new Color(0xA2C488), new Color(0xCFC29F), new Color(0xD6B9DB),
				new Color(0xDEA19B) };
		return Arrays.asList(colors);
	}
}
