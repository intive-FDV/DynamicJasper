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

package ar.com.fdvs.dj.core.layout;

import java.awt.Color;

public class TwoSeedCrossTabColorShema extends CrossTabColorShema {
	
	private Color rowSeed;
	private Color columnSeed;
	
	public TwoSeedCrossTabColorShema(Color rowSeed, Color columnSeed){
		this.rowSeed = rowSeed;
		this.columnSeed = columnSeed;
	}

	public void create(int numCols, int numRows) {
		//we need one more for the calculations because the matrix 
		//should be of cols+1 x rows+1
		numCols++; 
		numRows++;
		
		if (colors == null){
			colors = new Color[numCols][numRows];
		}

		colors[numCols-1][numRows-2] = this.columnSeed;
		colors[numCols-2][numRows-1] = this.rowSeed;
		
		if (numRows >= 2)
			for (int i = numRows-2; i >= 1; i--) {
				colors[numCols-1][i-1] = getDarker(colors[numCols-1][i]);
				                                   
			}	
		
		if (numCols >= 2)
			for (int j = numCols-2; j >= 1; j--) {
				colors[j-1][numRows-1] = getDarker(colors[j][numRows-1]);
				
			}	
		
		for (int i = numRows-2; i >= 0; i--) {
			for (int j = numCols-2; j >= 0; j--) {
				colors[j][i] = getDarker(getAvergage(colors[j][i+1],colors[j+1][i]));
				
			}	
		}	
	}

	private Color getAvergage(Color color, Color color2) {
		float[] comps = color.getColorComponents(null);
		float[] comps2 = color2.getColorComponents(null);
		
		for (int i = 0; i < comps2.length; i++) {
			comps[i] = (float) ((comps[i]+comps2[i])/2.0);
		}
		
		return new Color(comps[0],comps[1],comps[2]);
	}

	private Color getDarker(Color color) {
		float pr = (float) .90;
		float[] comps = color.getColorComponents(null);
		for (int i = 0; i < comps.length; i++) {
			comps[i] = (float) ((comps[i]*pr));
		}
		return new Color(comps[0],comps[1],comps[2]);
	}
	

}
