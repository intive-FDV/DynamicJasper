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
			comps[i] = (comps[i]*pr);
		}
		return new Color(comps[0],comps[1],comps[2]);
	}
	

}
