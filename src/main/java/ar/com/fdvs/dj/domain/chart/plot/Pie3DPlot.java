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

package ar.com.fdvs.dj.domain.chart.plot;

import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.entities.Entity;

import java.util.Map;

public class Pie3DPlot extends AbstractPiePlot {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	private Double depthFactor = null;

	/**
	 * Sets the depth factor.
	 *
	 * @param depthFactor the depth factor
	 **/
	public void setDepthFactor(Double depthFactor) {
		this.depthFactor = depthFactor;
	}

	/**
	 * Returns the depth factor.
	 *
	 * @return the depthFactor
	 **/
	public Double getDepthFactor() {
		return depthFactor;
	}

	public void transform(JRChartPlot plot, DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars, int width) {
		super.transform(plot, design, name, group, parentGroup, vars, width);
		JRDesignPie3DPlot piePlot = (JRDesignPie3DPlot) plot;
		if (getCircular() != null)
			piePlot.setCircular(getCircular());
		if (getLabelFormat() != null)
			piePlot.setLabelFormat(getLabelFormat());
		if (getLegendLabelFormat() != null)
			piePlot.setLegendLabelFormat(getLegendLabelFormat());
		if (getDepthFactor() != null)
			piePlot.setDepthFactor(getDepthFactor());
	}
}
