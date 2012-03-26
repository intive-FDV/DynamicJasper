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

package ar.com.fdvs.dj.domain;

import net.sf.jasperreports.engine.JRVariable;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.OperationColumn;

/**
 * Operations that can be shown as a group variable.</br>
 * </br>
 * @see OperationColumn
 */
public class DJCalculation extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;

	public static DJCalculation AVERAGE = new DJCalculation( JRVariable.CALCULATION_AVERAGE);
	public static DJCalculation COUNT = new DJCalculation( JRVariable.CALCULATION_COUNT);
	public static DJCalculation FIRST = new DJCalculation(JRVariable.CALCULATION_FIRST);
	public static DJCalculation HIGHEST = new DJCalculation(JRVariable.CALCULATION_HIGHEST);
	public static DJCalculation LOWEST = new DJCalculation(JRVariable.CALCULATION_LOWEST);
	public static DJCalculation NOTHING = new DJCalculation(JRVariable.CALCULATION_NOTHING);
	public static DJCalculation STANDARD_DEVIATION = new DJCalculation(JRVariable.CALCULATION_STANDARD_DEVIATION);
	public static DJCalculation SUM = new DJCalculation(JRVariable.CALCULATION_SUM);
	public static DJCalculation SYSTEM = new DJCalculation(JRVariable.CALCULATION_SYSTEM);
	public static DJCalculation VARIANCE = new DJCalculation(JRVariable.CALCULATION_VARIANCE);
	public static DJCalculation DISTINCT_COUNT = new DJCalculation(JRVariable.CALCULATION_DISTINCT_COUNT);

	private byte value;

	private DJCalculation(byte value){
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

}
