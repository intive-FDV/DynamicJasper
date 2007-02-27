package ar.com.fdvs.dj.util;

/**
 * @author Alejandro Gomez
*         Date: Feb 26, 2007
*         Time: 10:27:04 AM
*/
public class SortInfo {

    private String field;
    private boolean ascending;

    public SortInfo(final String _field, final boolean _ascending) {
        field = _field;
        ascending = _ascending;
    }

    public String getPropertyName() {
        return field;
    }

    public boolean isAscending() {
        return ascending;
    }
}
