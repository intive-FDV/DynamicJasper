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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.ImageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

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
		for (Iterator iter = crosstab.getRows().iterator(); iter.hasNext();) {
			Object object = iter.next();
			if (object instanceof String) {
				info.add(new SortInfo((String)object, true));
			} else if (object instanceof DJCrosstabRow) {
				info.add(new SortInfo(((DJCrosstabRow)object).getProperty().getProperty(), true));
			}
		}
		for (Iterator iter = crosstab.getColumns().iterator(); iter.hasNext();) {
			Object object = iter.next();
			if (object instanceof String) {
				info.add(new SortInfo((String)object, true));
			} else if (object instanceof DJCrosstabColumn) {
				info.add(new SortInfo(((DJCrosstabColumn)object).getProperty().getProperty(), true));
			}
		}
		MultiPropertyComparator mpc = new MultiPropertyComparator(info);
		Collections.sort(l, mpc);
		return l;
	}

}
