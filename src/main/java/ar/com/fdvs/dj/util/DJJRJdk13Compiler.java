package ar.com.fdvs.dj.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRJdk13Compiler;
import net.sf.jasperreports.engine.util.JRClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author Alejandro Gomez (alejandro.gomez@fdvsolutions.com)
 *         Date: Oct 8, 2007
 *         Time: 10:41:27 AM
 */
public class DJJRJdk13Compiler extends JRJdk13Compiler {

    private static final int MODERN_COMPILER_SUCCESS = 0;

    public String compileClasses(File[] sourceFiles, String classpath) throws JRException {
        String[] source = new String[sourceFiles.length + 4];
        for (int i = 0; i < sourceFiles.length; i++) {
            source[i] = sourceFiles[i].getPath();
        }
        source[sourceFiles.length] = "-classpath";
        source[sourceFiles.length + 1] = classpath;
        source[sourceFiles.length + 2] = "-encoding";
        source[sourceFiles.length + 3] = System.getProperty("file.encoding");

        String errors = null;


        try {
            Class clazz = JRClassLoader.loadClassForName("com.sun.tools.javac.Main");
            Object compiler = clazz.newInstance();

            try {
                Method compileMethod = clazz.getMethod("compile", new Class[]{String[].class, PrintWriter.class});
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int result = ((Integer)compileMethod.invoke(compiler, new Object[]{source, new PrintWriter(baos)})).intValue();

                if (result != MODERN_COMPILER_SUCCESS) {
                    errors = baos.toString();
                }
            }
            catch (NoSuchMethodException ex) {
                Method compileMethod = clazz.getMethod("compile", new Class[]{String[].class});

                int result = ((Integer)compileMethod.invoke(compiler, new Object[]{source})).intValue();
                if (result != MODERN_COMPILER_SUCCESS) {
                    errors = "See error messages above.";
                }
            }
        }
        catch (Exception e) {
            StringBuffer files = new StringBuffer();
            for (int i = 0; i < sourceFiles.length; ++i) {
                files.append(sourceFiles[i].getPath());
                files.append(' ');
            }
            throw new JRException("Error compiling report java source files : " + files, e);
        }

        return errors;
    }

    public static boolean isValid() {
        try {
            return JRClassLoader.loadClassForName("com.sun.tools.javac.Main") != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
}
