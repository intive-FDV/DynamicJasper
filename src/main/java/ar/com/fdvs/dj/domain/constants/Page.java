/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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

import net.sf.jasperreports.engine.JRReport;

public class Page {

	private int height = 0;
	private int width = 0;
	private byte orientation = ORIENTATION_PORTRAIT;
	
	public static final byte ORIENTATION_PORTRAIT = JRReport.ORIENTATION_PORTRAIT;
	public static final byte ORIENTATION_LANDSCAPE = JRReport.ORIENTATION_LANDSCAPE;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Page() {

	}

	public Page(int height, int width) {
		super();
		this.height = height;
		this.width = width;
	}

	public Page(int height, int width, byte orientation) {
		super();
		this.height = height;
		this.width = width;
		if (orientation != ORIENTATION_LANDSCAPE && orientation != ORIENTATION_PORTRAIT)
			this.orientation = ORIENTATION_PORTRAIT;
		else
			this.orientation = orientation;
	}

	public static Page Page_A4_Portrait(){
		return new Page(842,595,ORIENTATION_PORTRAIT);
	}

	public static Page Page_A4_Landscape(){
		return new Page(595,842,ORIENTATION_LANDSCAPE);
	}

	public static Page Page_Legal_Portrait(){
		return new Page(1008,612,ORIENTATION_PORTRAIT);
	}

	public static Page Page_Legal_Landscape(){
		return new Page(612,1008,ORIENTATION_LANDSCAPE);
	}

	public static Page Page_Letter_Portrait(){
		return new Page(792,612,ORIENTATION_PORTRAIT);
	}

	public static Page Page_Letter_Landscape(){
		return new Page(612,792,ORIENTATION_LANDSCAPE);
	}

	public byte getOrientation() {
		return orientation;
	}

	public void setOrientation(byte orientation) {
		this.orientation = orientation;
	}

}
