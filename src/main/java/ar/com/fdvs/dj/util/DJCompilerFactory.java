package ar.com.fdvs.dj.util;

/**
 * @author Alejandro Gomez (alejandro.gomez@fdvsolutions.com)
 *         Date: Oct 8, 2007
 *         Time: 10:36:07 AM
 */
public class DJCompilerFactory {

    public static String getCompilerClassName() {
        if (DJJRJdtCompiler.isValid()) {
            return DJJRJdtCompiler.class.getName();
        } else if (DJJRJdk13Compiler.isValid()) {
            return DJJRJdk13Compiler.class.getName();
        } else if (DJJRJdk12Compiler.isValid()) {
            return DJJRJdk12Compiler.class.getName();
        } else {
            return DJJRJavacCompiler.class.getName();
        }
    }
}
