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

package ar.com.fdvs.dj.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alejandro Gomez
*         Date: Feb 26, 2007
*         Time: 10:27:32 AM
*/
public class MultiPropertyComparator<T> implements Comparator<T> {

    private static final Log LOGGER = LogFactory.getLog(MultiPropertyComparator.class);

    private final List info;

    /**
     * 
     * @param _info List<SortInfo>
     */
    public MultiPropertyComparator(final List _info) {
        info = _info;
    }

    @Override
    public int compare(T o1, T o2) {
        int result = 0;
        for (int i = 0; result == 0 && i < info.size(); i++) {
            final SortInfo sortInfo = (SortInfo)info.get(i);
            try {
                final String propertyName = sortInfo.getPropertyName();
                final Comparable value1 = getValue(o1, propertyName);
                final Comparable value2 = getValue(o2, propertyName);
                result = compare(value1, value2) * (sortInfo.isAscending() ? 1 : -1);
            } catch (IllegalAccessException ex) {
                LOGGER.warn(ex);
            } catch (InvocationTargetException ex) {
                LOGGER.warn(ex);
            } catch (NoSuchMethodException ex) {
                LOGGER.warn(ex);
            }
        }
        return result;
    }

    private static Comparable getValue(final Object _object, final String _field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Object value = PropertyUtils.getProperty(_object, _field);
        if (value == null)
        	return null;
        
        if (!(value instanceof Comparable))
        	throw new RuntimeException("Objects are not Comparable, class " + value.getClass().getName());
		
		return (Comparable) value;
    }

	@SuppressWarnings("unchecked")
    private static int compare(final Comparable _value1, final Comparable _value2) {
        if (_value1 == null) {
            if (_value2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else if (_value2 == null) {
            return 1;
        } else {
            return _value1.compareTo(_value2);
        }
    }
}
