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

import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.entities.Entity;

public class ImageBanner extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;

	@Deprecated
	public static final byte ALIGN_LEFT = 0;

	@Deprecated
	public static final byte ALIGN_RIGHT = 1;

	@Deprecated
	public static final byte ALIGN_CENTER = 2;

	private String imagePath;
	private int width = 0;
	private int height= 0;
	private Alignment align = Alignment.Left;

	private ImageScaleMode scaleMode = ImageScaleMode.FILL_PROPORTIONALLY;
	
	public ImageBanner(){};

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Alignment getAlign() {
		return align;
	}

	@Deprecated
	public void setAlign(byte orientation) {
		this.align = Alignment.fromValue(orientation);
	}
	public void setAlign(Alignment orientation) {
		this.align = orientation;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public ImageScaleMode getScaleMode() {
		return scaleMode;
	}

	public void setScaleMode(ImageScaleMode scaleMode) {
		this.scaleMode = scaleMode;
	}

	@Deprecated
	public ImageBanner(String imagePath, int width, int height, byte align) {
		this.imagePath = imagePath;
		this.width = width;
		this.height = height;
		Alignment alignment = Alignment.fromValue(align);
		this.align = (alignment!=null?alignment:Alignment.Left);
	}

	public ImageBanner(String imagePath, int width, int height, Alignment align) {
		this.imagePath = imagePath;
		this.width = width;
		this.height = height;
		this.align = align!=null?align:Alignment.Left;
	}

	public static enum Alignment {
		Left((byte)0), Right((byte)1), Center((byte)2);

		private Alignment(byte value){
			this.value = value;
		}

		private byte value;

		public byte getValue() {
			return value;
		}

		public static Alignment fromValue(byte value) {
			for (Alignment alignment : Alignment.values()) {
				if (alignment.getValue() == value)
					return alignment;
			}

			return null;
		}
	}

}
