package ar.com.fdvs.dj.domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class DJChartColors {

	public static List pale() {
		ArrayList array = new ArrayList();
		array.add(new Color(0xFFA07A));
		array.add(new Color(0xD2B48C));
		array.add(new Color(0xFFD700));
		array.add(new Color(0xFFE4E1));
		array.add(new Color(0xE0FFFF));
		array.add(new Color(0x90EE90));

		return array;
	}

	public static List msOfficePurples() {
		ArrayList array = new ArrayList();
		array.add(new Color(0x660066));
		array.add(new Color(0xFF8080));
		array.add(new Color(0x9999FF));
		array.add(new Color(0x993366));
		array.add(new Color(0xFFFFCC));
		array.add(new Color(0xCCFFFF));

		return array;
	}

	public static List simpleColors() {
		ArrayList array = new ArrayList();
		array.add(new Color(0xFD0100));
		array.add(new Color(0x32CB32));
		array.add(new Color(0x007EF8));
		array.add(new Color(0xFFFF01));
		array.add(new Color(0xAF2421));
		array.add(new Color(0xFF7F02));

		return array;
	}
	
	public static List desert() {
		ArrayList array = new ArrayList();
		array.add(new Color(0xffffd4));
		array.add(new Color(0xfed98e));
		array.add(new Color(0xfe9929));
		array.add(new Color(0xd95f0e));
		array.add(new Color(0xf03b20));
		array.add(new Color(0x993404));

		return array;
	}
	
	public static List justAnotherColorScheme() {
		ArrayList array = new ArrayList();
		array.add(new Color(0xd7191c));
		array.add(new Color(0xfdae61));
		array.add(new Color(0xffffbf));
		array.add(new Color(0xabdda4));
		array.add(new Color(0x2b83ba));
		array.add(new Color(0xFF7F02));

		return array;
	}
}
