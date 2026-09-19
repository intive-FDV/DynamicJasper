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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.test;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageBannerFromBytesReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {
		byte[] logoBytes = loadBannerBytes();

		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(), 30)
				.addColumn("Branch", "branch", String.class.getName(), 30)
				.addColumn("Amount", "amount", Float.class.getName(), 70, true)
				.setTitle("Image banner from bytes")
				.setUseFullPageWidth(true)
				.addFirstPageImageBanner(logoBytes, 197, 60, ImageBanner.Alignment.Left)
				.addImageBanner(new ByteArrayInputStream(logoBytes), 100, 25, ImageBanner.Alignment.Right, ImageScaleMode.FILL);

		return drb.build();
	}

	@Override
	public void testReport() throws Exception {
		super.testReport();
		assertReportNotEmpty();
		boolean foundImage = false;
		for (JRPrintElement element : jp.getPages().get(0).getElements()) {
			if (element instanceof JRPrintImage) {
				foundImage = true;
				break;
			}
		}
		assertTrue("Expected at least one JRPrintImage on the first page", foundImage);
	}

	private byte[] loadBannerBytes() throws Exception {
		InputStream resource = getClass().getResourceAsStream("/images/logo_fdv_solutions_60.png");
		if (resource != null) {
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int n;
				while ((n = resource.read(buffer)) != -1) {
					out.write(buffer, 0, n);
				}
				return out.toByteArray();
			} finally {
				resource.close();
			}
		}
		BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		return out.toByteArray();
	}
}
