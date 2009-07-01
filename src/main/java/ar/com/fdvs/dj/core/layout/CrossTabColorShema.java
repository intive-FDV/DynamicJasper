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


