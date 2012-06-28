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

package ar.com.fdvs.dj.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.fill.JRFillField;

public class FieldMapWrapper implements Map {
	
	private Map map;
	
	public FieldMapWrapper(Map map){
		this.map = map;
	}

	public FieldMapWrapper() {
		this.map = Collections.EMPTY_MAP;
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
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

	public Set keySet() {
		return map.keySet();
	}

	public Object put(Object arg0, Object arg1) {
		return map.put(arg0, arg1);
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

	public void setMap(Map fldsm) {
		this.map = fldsm;
	}

	

}
