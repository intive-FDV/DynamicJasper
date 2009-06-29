package ar.com.fdvs.dj.core.layout;

import java.awt.Color;

public class ConfigurableCrossTabColorShema extends CrossTabColorShema {

	public void create(int numCols, int numRows) {
		for (int i = numCols-1; i >= 0; i--) {
			for (int j =  numRows-1; j >= 0; j--) {
				colors[i][j] = Color.WHITE;
			}
		}		
		colors[0][1] = Color.RED;
		colors[1][0] = Color.BLUE;

//		colors[0][2] = Color.CYAN;
		colors[2][0] = Color.ORANGE;
		colors[2][1] = Color.CYAN;
	}
	

}
