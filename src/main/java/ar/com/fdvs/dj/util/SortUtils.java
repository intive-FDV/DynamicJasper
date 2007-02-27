package ar.com.fdvs.dj.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

public class SortUtils {
	
	public static List sortCollection(Collection dummyCollection, List columns) {
		ArrayList l = new ArrayList(dummyCollection);
		ArrayList info = new ArrayList();
		for (Iterator iter = columns.iterator(); iter.hasNext();) {
			AbstractColumn  column = (AbstractColumn) iter.next();
			if (column instanceof PropertyColumn){
				PropertyColumn col = (PropertyColumn)column;
				SortInfo si = new SortInfo(col.getColumnProperty().getProperty(),true);
				info.add(si);
			}
			
		}
		
		MultiPropertyComparator mpc = new MultiPropertyComparator(info);
		Collections.sort(l,mpc);
		return l;
	}		

}
