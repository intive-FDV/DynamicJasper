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

public class Page {

	private int height = 0;
	private int width = 0;

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
		this.height = height;
		this.width = width;
	}

	public static Page Page_A4_Portrait(){
		return new Page(842,595);
	}

	public static Page Page_A4_Landscape(){
		return new Page(595,842);
	}

	public static Page Page_Legal_Portrait(){
		return new Page(1008,612);
	}

	public static Page Page_Legal_Landscape(){
		return new Page(612,1008);
	}

	public static Page Page_Letter_Portrait(){
		return new Page(792,612);
	}

	public static Page Page_Letter_Landscape(){
		return new Page(612,792);
	}

}

