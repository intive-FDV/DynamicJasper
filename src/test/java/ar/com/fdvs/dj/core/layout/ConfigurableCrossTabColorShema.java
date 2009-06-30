package ar.com.fdvs.dj.core.layout;

import java.awt.Color;

public class ConfigurableCrossTabColorShema extends CrossTabColorShema {

	public void create(int numCols, int numRows) {
		for (int i = numCols-1; i >= 0; i--) {
			for (int j =  numRows-1; j >= 0; j--) {
				colors[i][j] = Color.WHITE;
			}
		}
		
		colors[numCols-1][numRows-2] = new Color(240,248,255);
		colors[numCols-2][numRows-1] = new Color(255,248,240);
		
		if (numRows > 2)
			for (int i = numRows-2; i >= 1; i--) {
				colors[numCols-1][i-1] = getDarker(colors[numCols-1][i]);
				                                   
			}	
		
		if (numCols > 2)
			for (int j = numCols-2; j >= 1; j--) {
				colors[j-1][numRows-1] = getDarker(colors[j][numRows-1]);
				
			}	
		
		for (int i = numRows-2; i >= 0; i--) {
			for (int j = numCols-2; j >= 0; j--) {
				colors[j][i] = getDarker(getAvergage(colors[j][i+1],colors[j+1][i]));
				
			}	
		}	
		
//		for (int j =  numRows-2; j >= 1; j--) {
//			colors[numRows+1][j] = colors[numRows-1][j+1].darker(); 
//		}
		
/*		colors[0][0] = Color.GREEN;
		colors[1][0] = Color.BLUE;
		colors[2][0] = Color.GRAY;

		colors[0][1] = Color.RED;
		colors[1][1] = Color.PINK;
		colors[2][1] = Color.ORANGE;

		colors[0][2] = Color.YELLOW;
		colors[1][2] = Color.CYAN;

		colors[2][2] = Color.MAGENTA; //
	*/	
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
