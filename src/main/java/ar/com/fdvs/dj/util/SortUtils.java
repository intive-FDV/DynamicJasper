package ar.com.fdvs.dj.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

public class SortUtils {

    @SuppressWarnings("unchecked")
	public static List sortCollection(Collection dummyCollection, List columns) {
        ArrayList l = new ArrayList(dummyCollection);
        ArrayList info = new ArrayList();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Object object = iter.next();
            if (object instanceof String) {
                info.add(new SortInfo((String)object, true));
            } else if (object instanceof PropertyColumn) {
                info.add(new SortInfo(((PropertyColumn)object).getColumnProperty().getProperty(), true));
            }
        }
        MultiPropertyComparator mpc = new MultiPropertyComparator(info);
        Collections.sort(l, mpc);
        return l;
    }

}
