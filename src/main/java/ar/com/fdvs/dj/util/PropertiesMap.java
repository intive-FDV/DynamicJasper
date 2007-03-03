package ar.com.fdvs.dj.util;

import java.util.HashMap;

public class PropertiesMap extends HashMap {
	
	private static final long serialVersionUID = -8443176521038066760L;

	public PropertiesMap with(String key, Object value) {
		put(key,value);
		return this;
	}

	public static void main(String[] args) {
		String a = new PropertiesMap().with("a","a").with("b", "b").toString();
		System.out.println(a);
	}
}
