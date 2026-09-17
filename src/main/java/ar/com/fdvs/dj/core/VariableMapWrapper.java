package ar.com.fdvs.dj.core;

import net.sf.jasperreports.engine.fill.JRFillVariable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class VariableMapWrapper implements Map {
	
	private Map<String,JRFillVariable> map;
    private String reportName;

    public VariableMapWrapper(Map<String,JRFillVariable> map){
		this.map = map;
	}

	public VariableMapWrapper() {
		this.map = Collections.emptyMap();
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		boolean contains = map.containsKey(key);
		return contains || map.containsKey(reportName + "_" + key);
	}

	public boolean containsValue(Object value) {
		throw new DJException("Method not implemented");
	}

	public Set entrySet() {
		return map.entrySet();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public Object get(Object key) {
		Object value = map.get(key);
		if (value == null){
            value = map.get(reportName + "_" + key);
        }
        if (value == null){
			return null;
        }

		return ((JRFillVariable)value).getValue();
	}

	public int hashCode() {
		return map.hashCode();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set keySet() {
		return map.keySet();
	}

	public Object put(Object arg0, Object arg1) {
		return map.put((String)arg0,(JRFillVariable) arg1);
	}

	public void putAll(Map arg0) {
		map.putAll(arg0);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection values() {
		throw new DJException("Method not implemented");
	}

	public void setMap(Map<String,JRFillVariable> varsm) {
		this.map = varsm;
		
	}


    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
