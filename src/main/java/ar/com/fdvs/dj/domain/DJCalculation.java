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

import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.OperationColumn;
import net.sf.jasperreports.engine.type.CalculationEnum;

/**
 * Operations that can be shown as a group variable.<br>
 * <br>
 * @see OperationColumn
 */
public class DJCalculation extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;

	public static DJCalculation AVERAGE = new DJCalculation((byte) 3); // CalculationEnum.AVERAGE
	public static DJCalculation COUNT = new DJCalculation((byte) 1); // CalculationEnum.COUNT
	public static DJCalculation FIRST = new DJCalculation((byte) 9); // CalculationEnum.FIRST
	public static DJCalculation HIGHEST = new DJCalculation((byte) 5); // CalculationEnum.HIGHEST
	public static DJCalculation LOWEST = new DJCalculation((byte) 4); // CalculationEnum.LOWEST
	public static DJCalculation NOTHING = new DJCalculation((byte) 0); // CalculationEnum.NOTHING
	public static DJCalculation STANDARD_DEVIATION = new DJCalculation((byte) 6); // CalculationEnum.STANDARD_DEVIATION
	public static DJCalculation SUM = new DJCalculation((byte) 2); // CalculationEnum.SUM
	public static DJCalculation SYSTEM = new DJCalculation((byte) 8); // CalculationEnum.SYSTEM
	public static DJCalculation VARIANCE = new DJCalculation((byte) 7); // CalculationEnum.VARIANCE
	public static DJCalculation DISTINCT_COUNT = new DJCalculation((byte) 10); // CalculationEnum.DISTINCT_COUNT

	private byte value;

	private DJCalculation(byte value){
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

}
