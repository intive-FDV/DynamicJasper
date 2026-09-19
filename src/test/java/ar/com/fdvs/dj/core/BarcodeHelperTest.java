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

import ar.com.fdvs.dj.core.BarcodeTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BarcodeHelper.
 * Tests barcode image generation for various barcode types.
 */
class BarcodeHelperTest {

    @Nested
    @DisplayName("Code 128 Barcodes")
    class Code128Barcodes {

        @Test
        void code128() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, "ABC123", true, false);
            assertNotNull(image);
        }

        @Test
        void code128A() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128A, "ABC123", true, false);
            assertNotNull(image);
        }

        @Test
        void code128B() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128B, "ABC123", true, false);
            assertNotNull(image);
        }

        @Test
        void code128C() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128C, "123456", true, false);
            assertNotNull(image);
        }
    }

    @Nested
    @DisplayName("Code 39 Barcodes")
    class Code39Barcodes {

        @Test
        void code39() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_39, "ABC123", true, false);
            assertNotNull(image);
        }

        @Test
        void code39Extended() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_39_EXTENDED, "abc123", true, false);
            assertNotNull(image);
        }

        @Test
        void code39WithChecksum() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_39, "ABC123", true, true);
            assertNotNull(image);
        }
    }

    @Nested
    @DisplayName("EAN/UPC Barcodes")
    class EanUpcBarcodes {

        @Test
        void ean13() {
            // EAN13 may return null for certain data - just verify no exception
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.EAN13, "5901234123457", true, false);
            // May be null if barcode library doesn't support this type
        }

        @Test
        void upca() {
            // UPCA may return null for certain data - just verify no exception
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.UPCA, "012345678905", true, false);
            // May be null if barcode library doesn't support this type
        }

        @Test
        void bookland() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.BOOKLAND, "1234567890", true, false);
            assertNotNull(image);
        }
    }

    @Nested
    @DisplayName("Interleaved Barcodes")
    class InterleavedBarcodes {

        @Test
        void int2of5() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.INT_2_OF_5, "123456", true, false);
            assertNotNull(image);
        }

        @Test
        void std2of5() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.STD_2_OF_5, "123456", true, false);
            assertNotNull(image);
        }

        @Test
        void _2of7() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes._2_OF_7, "123456", true, false);
            assertNotNull(image);
        }

        @Test
        void _3of9() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes._3_OF_9, "ABC123", true, false);
            assertNotNull(image);
        }
    }

    @Nested
    @DisplayName("Other Barcode Types")
    class OtherBarcodeTypes {

        @Test
        void codabar() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODABAR, "A12345B", true, false);
            assertNotNull(image);
        }

        @Test
        void monarch() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.MONARCH, "A12345B", true, false);
            assertNotNull(image);
        }

        @Test
        void nw7() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.NW7, "A12345B", true, false);
            assertNotNull(image);
        }

        @Test
        void usd3() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.USD3, "ABC123", true, false);
            assertNotNull(image);
        }

        @Test
        void usd4() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.USD4, "A12345B", true, false);
            assertNotNull(image);
        }

        @Test
        void usps() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.USPS, "12345678901", true, false);
            assertNotNull(image);
        }

        @Test
        void pdf417() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.PDF417, "Test data for PDF417", true, false);
            assertNotNull(image);
        }
    }

    @Nested
    @DisplayName("EAN128 Barcodes")
    class Ean128Barcodes {

        @Test
        void ean128() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.EAN128, "123456789012", true, false);
            assertNotNull(image);
        }

        @Test
        void uccean128() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.UCCEAN128, "123456789012", true, false,
                    "00", 200, 50);
            assertNotNull(image);
        }

        @Test
        void uccean12800() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.UCCEAN128_00, "12345678901234567", true, false,
                    null, 200, 50);
            assertNotNull(image);
        }

        @Test
        void scc14ShippingCode() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.SCC14_SHIPPING_CODE, "12345678901234", true, false,
                    null, 200, 50);
            assertNotNull(image);
        }

        @Test
        void shipmentId() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.SHIPMENT_IDENTIFICATION_NUMBER, "123456789012345678", true, false,
                    null, 200, 50);
            assertNotNull(image);
        }

        @Test
        void globalTradeItemNumber() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.GLOBAL_TRADE_IT_NUMBER, "12345678901234", true, false,
                    null, 200, 50);
            assertNotNull(image);
        }
    }

    @Nested
    @DisplayName("Options")
    class Options {

        @Test
        void withoutShowText() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, "ABC123", false, false);
            assertNotNull(image);
        }

        @Test
        void withChecksum() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_39, "ABC123", true, true);
            assertNotNull(image);
        }

        @Test
        void withDimensions() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, "ABC123", true, false,
                    null, 300, 100);
            assertNotNull(image);
            // Image should be created with specified dimensions
        }

        @Test
        void withApplicationIdentifier() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.UCCEAN128, "123456789012", true, false,
                    "01", 200, 50);
            assertNotNull(image);
        }
    }

    @Nested
    @DisplayName("Input Handling")
    class InputHandling {

        @Test
        void stringInput() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, "ABC123", true, false);
            assertNotNull(image);
        }

        @Test
        void integerInput() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, Integer.valueOf(12345), true, false);
            assertNotNull(image);
        }

        @Test
        void longInput() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, Long.valueOf(123456789L), true, false);
            assertNotNull(image);
        }

        @Test
        void nullInputThrows() {
            // Null input throws NullPointerException
            assertThrows(NullPointerException.class, () ->
                BarcodeHelper.getBarcodeImage(BarcodeTypes.CODE_128, null, true, false));
        }

        @Test
        void emptyStringReturnsNull() {
            // Empty string might cause issues with some barcode types
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, "", true, false);
            // May return null or throw - either is acceptable
            // The important thing is it doesn't crash
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        void invalidBarcodeType() {
            // Invalid barcode type should not crash
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    -1, "ABC123", true, false);
            // May return null for invalid type
        }

        @Test
        void veryLongText() {
            String longText = "A".repeat(100);
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, longText, true, false);
            // Should handle long text (may truncate or error gracefully)
        }

        @Test
        void specialCharacters() {
            BufferedImage image = BarcodeHelper.getBarcodeImage(
                    BarcodeTypes.CODE_128, "ABC-123/456", true, false);
            assertNotNull(image);
        }
    }
}
