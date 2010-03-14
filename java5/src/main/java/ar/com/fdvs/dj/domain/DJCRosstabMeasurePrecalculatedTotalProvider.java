package ar.com.fdvs.dj.domain;

/**
 * Interfase to provide implementations for crosstab measure total providers (precalculated)
 * @author mamana
 *
 */
public interface DJCRosstabMeasurePrecalculatedTotalProvider {

	/**
	 * 
	 * @param colProp column property names (in order)
	 * @param colValue column values so far
	 * @param rowProp row property names (in order)
	 * @param rowValue row values so far
	 * @return
	 */
	public Object getValueFor(String[] colProp, Object colValue[], String rowProp[], Object rowValue[]);
}
