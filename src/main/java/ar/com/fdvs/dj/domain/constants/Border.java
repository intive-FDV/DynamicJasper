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

package ar.com.fdvs.dj.domain.constants;

import net.sf.jasperreports.engine.JRGraphicElement;

/**
 * See value of constants here: 
 * http://jasperreports.sourceforge.net/api/constant-values.html
 * @author mamana
 *
 */
public class Border  extends BaseDomainConstant {

	private static final long serialVersionUID = 1L;
	
	public static Border NO_BORDER = new Border(JRGraphicElement.PEN_NONE);
	public static Border THIN = new Border(JRGraphicElement.PEN_THIN);

	public static Border PEN_1_POINT = new Border(JRGraphicElement.PEN_1_POINT);
	public static Border PEN_2_POINT = new Border(JRGraphicElement.PEN_2_POINT);
	public static Border PEN_4_POINT = new Border(JRGraphicElement.PEN_4_POINT);
	public static Border DOTTED = new Border(JRGraphicElement.PEN_DOTTED);

	private byte value;

	public byte getValue() {
		return value;
	}

	private Border(byte value){
		this.value = value;
	}

}
