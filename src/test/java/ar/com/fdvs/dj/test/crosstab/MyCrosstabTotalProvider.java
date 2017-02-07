package ar.com.fdvs.dj.test.crosstab;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import ar.com.fdvs.dj.domain.DJCRosstabMeasurePrecalculatedTotalProvider;

public class MyCrosstabTotalProvider implements DJCRosstabMeasurePrecalculatedTotalProvider {
	
	Map values = Collections.EMPTY_MAP;

	public Object getValueFor(String[] colProp, Object colValue[], String rowProp[], Object rowValue[]) {
		System.out.println("row " + Arrays.toString(rowProp) + " = " + Arrays.toString(rowValue));
		System.out.println("col " + Arrays.toString(colProp) + " = " + Arrays.toString(colValue));
		
		/*
		  Here make some logic to determine proper value for the total.
		 */
		
//		return values.get("" + colProp + "|" + rowProp);
		return 99L;
	}

	public void setValues(Map values) {
		this.values = values;
	}

}
