package ar.com.fdvs.dj.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.fill.JRFillField;

public class FieldMapWrapper implements Map<String, Object> {

	private Map<String, JRFillField> map;
	
	public FieldMapWrapper(Map map){
		this.map = map;
	}

	public FieldMapWrapper() {
		this.map = Collections.EMPTY_MAP;
	}

	public void clear() {
		map.clear();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		throw new DJException("Method not implemented");
	}

	@Override
	public Object get(Object key) {
		return null;
	}

	public Set entrySet() {
		return map.entrySet();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public Object get(String key) {
		Object value = map.get(key);
		if (value == null)
			return null;
		
		return ((JRFillField)value).getValue();
	}

	public int hashCode() {
		return map.hashCode();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Object put(String arg0, Object arg1) {
		throw new DJException("Method not supported");
	}

	public void putAll(Map arg0) {
		throw new DJException("Method not supported");
	}

	public Object remove(Object key) {
		throw new DJException("Method not supported");
	}

	public int size() {
		return map.size();
	}

	public Collection values() {
		throw new DJException("Method not implemented");
	}

	public void setMap(Map fldsm) {
		this.map = fldsm;
	}

	public Map<String, JRFillField> getMap(){
		return map;
	}

	public JRFillField getJRFillField(String key){
		return map.get(key);
	}
	

}
