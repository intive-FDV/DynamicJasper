/*
 * DynamicJasper: A library for creating reports dynamically by specifying
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
//        } else if (DJJRJdk12Compiler.isValid()) {
//            return DJJRJdk12Compiler.class.getName();
        } else {
            return DJJRJavacCompiler.class.getName();
        }
    }
}
