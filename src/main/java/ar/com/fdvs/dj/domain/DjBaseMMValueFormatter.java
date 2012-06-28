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
