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
