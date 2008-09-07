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

package ar.com.fdvs.dj.domain.entities.conditionalStyle;

import java.util.Map;

import ar.com.fdvs.dj.domain.CustomExpression;

/**
 * Special CustomExpression that complements very well with Conditionl Styles.
 */
public class StatusLightCondition implements CustomExpression {

	private Double min;
	private Double max;

	private int mode = 0; // 0: x < min, 1: min < x < max, 2: x > max

	public StatusLightCondition(Double min, Double max) {
		this.min = min;
		this.max = max;

		if (min != null && max == null)
			mode = 0;
		else if (min != null && max != null)
			mode = 1;
		else if (min == null && max != null)
			mode = 2;
	}

	public Object evaluate(Map fields, Map variables, Map parameters) {
		//FIXME get this nasty logic working
		/**if (object == null)
			return null;

		Number number = (Number)object;

		if (mode == 0){
			return Boolean.valueOf((min.doubleValue() > number.doubleValue()));
		} else if (mode == 1) {
			return Boolean.valueOf(min.doubleValue() <= number.doubleValue() && max.doubleValue() > number.doubleValue());
		} else {
			return Boolean.valueOf(max.doubleValue() <= number.doubleValue());
		}
**/
		return Boolean.TRUE;
	}

	public String getClassName() {
		return Boolean.class.getName();
	}

}
