/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
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

package ar.com.fdvs.dj.domain.entities.columns;

import ar.com.fdvs.dj.core.BarcodeTypes;

public class BarCodeColumn extends ImageColumn implements BarcodeTypes {

	private int barcodeType = _2_OF_7;
	private boolean showText = false;
	private boolean checkSum = false;
	private String applicationIdentifier = null;
	private boolean haltWhenException = false;

	public boolean isHaltWhenException() {
		return haltWhenException;
	}

	public void setHaltWhenException(boolean haltWhenException) {
		this.haltWhenException = haltWhenException;
	}

	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}

	public void setApplicationIdentifier(String applicationIdentifier) {
		this.applicationIdentifier = applicationIdentifier;
	}

	public boolean isShowText() {
		return showText;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	public int getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(int barcodeType) {
		this.barcodeType = barcodeType;
	}

	public boolean isCheckSum() {
		return checkSum;
	}

	public void setCheckSum(boolean checkSum) {
		this.checkSum = checkSum;
	}

}
