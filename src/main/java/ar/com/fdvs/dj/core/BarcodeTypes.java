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

package ar.com.fdvs.dj.core;

public interface BarcodeTypes {
	 int _2_OF_7 		= 0;
		int _3_OF_9 		= 1;
		int BOOKLAND 		= 2;
		int CODABAR 		= 3;
		int CODE_128		= 4;
		int CODE_128A 		= 5;
		int CODE_128B 		= 6;
		int CODE_128C 		= 7;
		int CODE_39 		= 8;
		int EAN128 		= 9;
		int EAN13 			= 10;
		int GLOBAL_TRADE_IT_NUMBER = 11;
		int INT_2_OF_5 	= 12;
		int MONARCH 		= 13;
		int NW7 			= 14;
		int SCC14_SHIPPING_CODE = 15;
		int PDF417 		= 16;
		int SHIPMENT_IDENTIFICATION_NUMBER = 17;
		int UCCEAN128 		= 18;
		int STD_2_OF_5	 	= 19;
		int UCCEAN128_00 	= 20;
		int UPCA 			= 21;
		int USD3 			= 22;
		int USD4 			= 23;
		int USPS 			= 24;
		int CODE_39_EXTENDED = 25;
}
