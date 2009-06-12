package ar.com.fdvs.dj.util;

import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;

import ar.com.fdvs.dj.core.DJException;


public class Utils {
	
	private Utils(){
		
	}
	
	/**
	 * Returns true if collection is null or empty
	 * @param col
	 * @return 
	 */
	public static boolean isEmpty(Collection col){
		if (col == null)
			return true;
				
		return col.isEmpty();
		
	}

	/**
	 * 
	 * @param dest
	 * @param orig
	 */
	public static void copyProperties(Object dest, Object orig){
        try {
        	if (orig != null && dest != null)
        		BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			throw new DJException("Could not copy properties for shared object: " + orig +", message: " + e.getMessage(),e);
		}		
	}
	
	
}
