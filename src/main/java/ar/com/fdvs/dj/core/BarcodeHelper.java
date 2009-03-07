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

import java.awt.image.BufferedImage;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.linear.code39.Code39Barcode;
import net.sourceforge.barbecue.linear.ean.UCCEAN128Barcode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BarcodeHelper implements BarcodeTypes {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(BarcodeHelper.class);

	private static Barcode bc = null;


	public static BufferedImage getBarcodeImage(int type, Object aText, boolean showText, boolean checkSum)
    {
        return getBarcodeImage(type, aText, showText, checkSum, "", 0, 0);
    }

    public static BufferedImage getBarcodeImage(int type, Object aText, boolean showText, boolean checkSum, Object applicationIdentifier, int width, int height)
    {

        String text = null;
        if (aText instanceof String)
        	text = ((String) (aText));
        else text = aText.toString();


        try {
	        switch (type) {
			case _2_OF_7:
				bc = BarcodeFactory.create2of7(text);
				break;
			case _3_OF_9:
				bc = BarcodeFactory.create3of9(text, checkSum);
				break;
			case BOOKLAND:
				bc = BarcodeFactory.createBookland(text);
				break;
			case CODABAR:
				bc = BarcodeFactory.createCodabar(text);
				break;
			case CODE_128:
				bc = BarcodeFactory.createCode128(text);
				break;
			case CODE_128A:
				bc = BarcodeFactory.createCode128A(text);
				break;
			case CODE_128B:
				bc = BarcodeFactory.createCode128B(text);
				break;
			case CODE_128C:
				bc = BarcodeFactory.createCode128B(text);
				break;
			case CODE_39:
				bc = BarcodeFactory.createCode39(text, checkSum);
				break;
			case EAN128:
				bc = BarcodeFactory.createEAN128(text);
				break;
			case EAN13:
				bc = BarcodeFactory.createEAN13(text);
				break;
			case GLOBAL_TRADE_IT_NUMBER:
				bc = BarcodeFactory.createGlobalTradeItemNumber(text);
				break;
			case INT_2_OF_5:
				bc = BarcodeFactory.createInt2of5(text, checkSum);
				break;
			case MONARCH:
				bc = BarcodeFactory.createMonarch(text);
				break;
			case NW7:
				bc = BarcodeFactory.createNW7(text);
				break;
			case PDF417:
				bc = BarcodeFactory.createPDF417(text);
				break;
			case SCC14_SHIPPING_CODE:
				bc = BarcodeFactory.createSCC14ShippingCode(text);
				break;
			case SHIPMENT_IDENTIFICATION_NUMBER:
				bc = BarcodeFactory.createShipmentIdentificationNumber(text);
				break;
			case UCCEAN128_00:
				bc = new UCCEAN128Barcode("00", text, checkSum);
				break;
			case STD_2_OF_5:
				bc = BarcodeFactory.createStd2of5(text, checkSum);
				break;
			case UCCEAN128:
				if (applicationIdentifier == null)
					applicationIdentifier = "";
				bc = new UCCEAN128Barcode(applicationIdentifier.toString(), text, checkSum);
				break;
			case UPCA:
				bc = BarcodeFactory.createUPCA(text);
				break;
			case USD3:
				bc = BarcodeFactory.createUSD3(text, checkSum);
				break;
			case USD4:
				bc = BarcodeFactory.createUSD4(text);
				break;
			case USPS:
				bc = BarcodeFactory.createUSPS(text);
				break;
			case CODE_39_EXTENDED:
				bc = new Code39Barcode(text, checkSum, true);
				break;

			default:
				break;
			}
	        if(width > 0)
	        	bc.setBarWidth(width);
	        if(height > 0)
	        	bc.setBarHeight(height);
	        bc.setDrawingText(showText);
//	        bc.setResolution(96);
	        return BarcodeImageHandler.getImage(bc);

        } catch (Exception e) {
			logger.error("Error generating BarCode," + e.getMessage(),e);
		}
		return null;
    }




}
