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

import ar.com.fdvs.dj.domain.DJCrosstab;

public class CrossTabColorShema {

	public Color[][] getColors() {
		return colors;
	}


	Color[][] colors;

	protected CrossTabColorShema(){
		
	}
	
	/**
	 * The number of rows and columns
	 * @param rows
	 * @param cols
	 */
	public CrossTabColorShema(int rows, int cols){
		colors =  new Color[cols+1][rows+1];
	}
	
	/**
	 * The DJCrosstab is used to get the number of columns and rows within it
	 * @param djcross
	 */
	public CrossTabColorShema(DJCrosstab djcross){
		int cols = djcross.getColumns().size();
		int rows = djcross.getRows().size();
		colors =  new Color[cols+1][rows+1];
	}
	
	/**
	 * To be overwritten
	 * @param numCols
	 * @param numRows
	 */
	public void create(int numCols, int numRows){
		
	}

	/**
	 * Set the color for each total for the column
	 * @param column the number of the column (starting from 1)
	 * @param color
	 */
	public void setTotalColorForColumn(int column, Color color){
		int map = (colors.length-1) - column;
		colors[map][colors[0].length-1]=color;
	}
	
	/***
	 * Sets the color for each total for the row
	 * @param row (starting from 1)
	 * @param color
	 */
	public void setTotalColorForRow(int row, Color color){
		int map = (colors[0].length-1) - row;
		colors[colors.length-1][map]=color;		
	}
	
	/**
	 * Sets the color for the big total between the column and row
	 * @param row row index (starting from 1)
	 * @param column column index (starting from 1)
	 * @param color
	 */
	public void setColorForTotal(int row, int column, Color color){
		int mapC = (colors.length-1) - column;
		int mapR = (colors[0].length-1) - row;
		colors[mapC][mapR]=color;		
	}
	
	/**
	 * Color in the area reserved for the measures
	 * @param col
	 */
	public void setColorForMeasure(Color col){
		colors[colors.length-1][colors[0].length-1]=col;
	}


	public static Color[][] createSchema(CrossTabColorShema ctColorScheme, int length, int length2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


