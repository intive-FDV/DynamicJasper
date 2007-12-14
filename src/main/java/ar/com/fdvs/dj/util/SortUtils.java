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
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

public class SortUtils {

	public static List sortCollection(Collection dummyCollection, List columns) {
        ArrayList l = new ArrayList(dummyCollection);
        ArrayList info = new ArrayList();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Object object = iter.next();
            if (object instanceof String) {
                info.add(new SortInfo((String)object, true));
            } else if (object instanceof ExpressionColumn) {
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
