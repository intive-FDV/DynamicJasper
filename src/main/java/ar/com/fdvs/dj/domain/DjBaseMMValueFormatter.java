package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.core.DJException;

import java.util.Map;

/**
 * FDV Solutions
 * User: Juan Lagostena
 * Date: 12/20/11
 */

/**
 * This class is like a ValueFormatter but with some tricks to get the values and names
 * of all propertyMeasures in a crosstab
 */
public abstract class DjBaseMMValueFormatter implements DJValueFormatter {

    private String[] propertyMeasures;

    public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
        return innerEvaluate((Object[]) value, fields, variables, parameters);
    }

    public String[] getPropertyMeasures() {
        return propertyMeasures;
    }

    public void setPropertyMeasures(String[] propertyMeasures) {
        this.propertyMeasures = propertyMeasures;
    }

    public <T> T getValueFor(Object[] values, String propertyMeasure) {
        String measureName = null;
        int idx = 0;
        while (!propertyMeasure.equals(measureName) && idx < this.getPropertyMeasures().length) {
            measureName = this.getPropertyMeasures()[idx];
            idx++;
        }
        if (!propertyMeasure.equals(measureName)) {
            throw new DJException("The measure " + propertyMeasure + " does not exist");
        }
        return (T) values[--idx];
    }

    protected abstract Object innerEvaluate(Object[] value, Map fields, Map variables, Map parameters);


}
