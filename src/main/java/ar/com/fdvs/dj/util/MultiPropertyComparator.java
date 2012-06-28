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

package ar.com.fdvs.dj.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Alejandro Gomez
*         Date: Feb 26, 2007
*         Time: 10:27:32 AM
*/
public class MultiPropertyComparator implements Comparator {

    private static final Log LOGGER = LogFactory.getLog(MultiPropertyComparator.class);

    private List info;

    /**
     * 
     * @param _info List<SortInfo>
     */
    public MultiPropertyComparator(final List _info) {
        info = _info;
    }

    public int compare(final Object _o1, final Object _o2) {
        int result = 0;
        for (int i = 0; result == 0 && i < info.size(); i++) {
            final SortInfo sortInfo = (SortInfo)info.get(i);
            try {
                final String propertyName = sortInfo.getPropertyName();
                final Comparable value1 = getValue(_o1, propertyName);
                final Comparable value2 = getValue(_o2, propertyName);
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
