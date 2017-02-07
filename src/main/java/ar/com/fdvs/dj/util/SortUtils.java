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

import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.ImageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SortUtils {

	public static List sortCollection(Collection dummyCollection, List columns) {
        ArrayList l = new ArrayList(dummyCollection);
        ArrayList info = new ArrayList();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Object object = iter.next();
            if (object instanceof String) {
                info.add(new SortInfo((String)object, true));
            } else if (object instanceof ExpressionColumn || object instanceof ImageColumn) {
            	//do nothing with expression columns
            	continue;
	        } else if (object instanceof PropertyColumn) {
	        	info.add(new SortInfo(((PropertyColumn)object).getColumnProperty().getProperty(), true));
	        }
        }
        MultiPropertyComparator mpc = new MultiPropertyComparator(info);
        Collections.sort(l, mpc);
        return l;
    }
	
	public static List sortCollection(Collection dummyCollection, String[] properties) {
		ArrayList l = new ArrayList(dummyCollection);
		ArrayList info = new ArrayList();
		for (int i = 0; i < properties.length; i++) {
			info.add(new SortInfo(properties[i], true));
		}
		MultiPropertyComparator mpc = new MultiPropertyComparator(info);
		Collections.sort(l, mpc);
		return l;
	}
	
	public static List sortCollection(Collection dummyCollection, DJCrosstab crosstab) {
		ArrayList l = new ArrayList(dummyCollection);
		ArrayList info = new ArrayList();
		for (DJCrosstabRow row : crosstab.getRows()) {
			info.add(new SortInfo(row.getProperty().getProperty(), true));
		}

		for (DJCrosstabColumn col : crosstab.getColumns()) {
			info.add(new SortInfo(col.getProperty().getProperty(), true));

		}

		MultiPropertyComparator mpc = new MultiPropertyComparator(info);
		Collections.sort(l, mpc);
		return l;
	}

}
