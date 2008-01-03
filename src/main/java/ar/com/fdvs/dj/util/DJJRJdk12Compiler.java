/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRJdk12Compiler;
import net.sf.jasperreports.engine.util.JRClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Alejandro Gomez (alejandro.gomez@fdvsolutions.com)
 *         Date: Oct 8, 2007
 *         Time: 11:04:47 AM
 */
public class DJJRJdk12Compiler extends JRJdk12Compiler {

    public String compileClasses(File[] sourceFiles, String classpath) throws JRException {
        String[] source = new String[sourceFiles.length + 4];
        for (int i = 0; i < sourceFiles.length; i++) {
            source[i] = sourceFiles[i].getPath();
        }
        source[sourceFiles.length] = "-classpath";
        source[sourceFiles.length + 1] = classpath;
        source[sourceFiles.length + 2] = "-encoding";
        source[sourceFiles.length + 3] = System.getProperty("file.encoding");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Class javacClass = JRClassLoader.loadClassForName("sun.tools.javac.Main");
            Constructor constructor = javacClass.getConstructor(new Class[]{OutputStream.class, String.class});
            Method compileMethod = javacClass.getMethod("compile", new Class[]{String[].class});
            Object javac = constructor.newInstance(new Object[]{baos, source[0]});

            compileMethod.invoke(javac, new Object[]{source});
        }
        catch (Exception e) {
            StringBuffer files = new StringBuffer();
            for (int i = 0; i < sourceFiles.length; ++i) {
                files.append(sourceFiles[i].getPath());
                files.append(' ');
            }
            throw new JRException("Error compiling report java source files : " + files, e);
        }

        if (baos.toString().indexOf("error") != -1) {
            return baos.toString();
        }

        return null;
    }

    public static boolean isValid() {
        try {
            return JRClassLoader.loadClassForName("sun.tools.javac.Main") != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
}
