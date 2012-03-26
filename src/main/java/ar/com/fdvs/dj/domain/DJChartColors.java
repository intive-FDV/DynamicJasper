/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
