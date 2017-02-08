package ar.com.fdvs.dj.core.layout;

import java.awt.*;

public abstract class CrossTabColorShemaGenerator extends CrossTabColorShema {

	public static Color[][] createSchema(int schema, int numCols, int numRows){

		CrossTabColorShema generator;

		if (schema == 0)
			generator = new Schema0();
		else if (schema == 1)
			generator = new Schema1();
		else if (schema == 2)
			generator = new Schema2();
		else if (schema == 3)
			generator = new Schema3();
		else if (schema == 4)
			generator = new Schema4();
		else if (schema == 5)
			generator = new Schema5();
		else if (schema == 6)
			generator = new Schema6();
		else
			generator = new Schema1();

		return createSchema(generator,numCols,numRows);
	}

	public static Color[][] createSchema(CrossTabColorShema ctColorScheme, int numCols, int numRows) {
		ctColorScheme.colors = new Color[numCols+1][numRows+1];
		ctColorScheme.create(numCols+1, numRows+1);
		return ctColorScheme.colors;
	}
	
}

class Schema0 extends CrossTabColorShema{

	public void create(int numCols, int numRows) {
		for (int i = numCols-1; i >= 0; i--) {
			for (int j =  numRows-1; j >= 0; j--) {
				colors[i][j] =  Color.WHITE;
			}
		}
	}
}

/**
 * Violet
 * @author Juan Manuel
 *
 */
class Schema1 extends CrossTabColorShema{

	public void create(int numCols, int numRows) {
		int base = 220;
		int base2 = 150;

		int coli =(255-base) / (numCols);
		int colj = (255-base2) / (numRows);
		for (int i = numCols-1; i >= 0; i--) {
			int auxi = base + coli * i;
			for (int j =  numRows-1; j >= 0; j--) {
				int auxj = base2 + colj * j;
				colors[i][j] = new Color(auxj,(auxj*auxi)/255,auxi);
			}
		}
	}
}

/**
 * Pink
 * @author Juan Manuel
 *
 */
class Schema2 extends CrossTabColorShema{

	public void create(int numCols, int numRows) {
		int base = 220;
		int base2 = 150;

		int coli =(255-base) / (numCols);
		int colj = (255-base2) / (numRows);
		for (int i = numCols-1; i >= 0; i--) {
			int auxi = base + coli * i;
			for (int j =  numRows-1; j >= 0; j--) {
				int auxj = base2 + colj * j;
				colors[i][j] = new Color(auxi,(auxj*auxi)/255,auxj);
			}
		}
	}
}

/**
 * Light pink/brown
 * @author Juan Manuel
 *
 */
class Schema3 extends CrossTabColorShema{

	public void create(int numCols, int numRows) {
		int base = 220;
		int base2 = 150;

		int coli =(255-base) / (numCols);
		int colj = (255-base2) / (numRows);
		for (int i = numCols-1; i >= 0; i--) {
			int auxi = base + coli * i;
			for (int j =  numRows-1; j >= 0; j--) {
				int auxj = base2 + colj * j;
				colors[i][j] = new Color(auxi,auxj,(auxj*auxi)/255);
			}
		}
	}
}

/**
 * light green
 * @author Juan Manuel
 *
 */
class Schema4 extends CrossTabColorShema{

	public void create(int numCols, int numRows) {
		int base = 220;
		int base2 = 150;

		int coli =(255-base) / (numCols);
		int colj = (255-base2) / (numRows);
		for (int i = numCols-1; i >= 0; i--) {
			int auxi = base + coli * i;
			for (int j =  numRows-1; j >= 0; j--) {
				int auxj = base2 + colj * j;
				colors[i][j] = new Color((auxj*auxi)/255,auxi,auxj);
			}
		}
	}
}

/**
 * blue
 * @author Juan Manuel
 *
 */
class Schema5 extends CrossTabColorShema{

	public void create(int numCols, int numRows) {
		int base2 = 220;
		int base = 150;

		int coli =(255-base) / (numCols);
		int colj = (255-base2) / (numRows);
		for (int i = numCols-1; i >= 0; i--) {
			int auxi = base + coli * i;
			for (int j =  numRows-1; j >= 0; j--) {
				int auxj = base2 + colj * j;
				colors[i][j] = new Color((auxj*auxi)/255,auxi,auxj);
			}
		}
	}
}

/**
 * gray
 * @author Juan Manuel
 *
 */
class Schema6 extends CrossTabColorShema{

	public void create(int numCols, int numRows) {
		int base = 200;

		int coli =(255-base) / (numCols + numRows);
		for (int i = numCols-1; i >= 0; i--) {
			for (int j =  numRows-1; j >= 0; j--) {
				int auxi = base + coli * (j + i);
				colors[i][j] = new Color(auxi,auxi,auxi);
			}
		}
	}
}

