package ar.com.fdvs.dj.util;

import net.sf.jasperreports.engine.util.JRClassLoader;

/**
 * @author Alejandro Gomez (alejandro.gomez@fdvsolutions.com)
 *         Date: Oct 8, 2007
 *         Time: 10:36:07 AM
 */
public class DJCompilerFactory {

    public static String getCompilerClassName() {
        try {
            if (JRClassLoader.loadClassForName("org.eclipse.jdt.internal.compiler.Compiler") != null)
            	 return DJJRJdtCompiler.class.getName();
        } catch (ClassNotFoundException ex) {
        	//nothing to do
        } catch (NoClassDefFoundError e) {
        	//nothing to do
		}    	

    	if (DJJRJdk13Compiler.isValid()) {
            return DJJRJdk13Compiler.class.getName();
        } else if (DJJRJdk12Compiler.isValid()) {
            return DJJRJdk12Compiler.class.getName();
        } else {
            return DJJRJavacCompiler.class.getName();
        }
    }
}
