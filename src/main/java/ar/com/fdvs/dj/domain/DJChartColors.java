package ar.com.fdvs.dj.domain;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * Some color templates to be used by charts.
 * 
 * @author msimone
 */
public class DJChartColors {

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
