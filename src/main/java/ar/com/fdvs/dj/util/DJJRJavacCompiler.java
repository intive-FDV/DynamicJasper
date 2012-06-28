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

package ar.com.fdvs.dj.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRJavacCompiler;

/**
 * @author Alejandro Gomez (alejandro.gomez@fdvsolutions.com)
 *         Date: Oct 8, 2007
 *         Time: 11:07:07 AM
 */
public class DJJRJavacCompiler extends JRJavacCompiler {

    public String compileClasses(File[] sourceFiles, String classpath) throws JRException {
        String[] source = new String[sourceFiles.length + 5];
        source[0] = "javac";
        source[1] = "-classpath";
        source[2] = classpath;
        source[3] = "-encoding";
        source[4] = System.getProperty("file.encoding");
        for (int i = 0; i < sourceFiles.length; i++) {
            source[i + 5] = sourceFiles[i].getPath();
        }

        try {
            // Compile the source file and arrange to read the errors if any.
            Process compile = Runtime.getRuntime().exec(source);
            InputStream errFile = compile.getErrorStream();

            // Read the error messages (if any) into the ByteArrayOutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            do {
                count = errFile.read(buffer);
                if (count > 0) {
                    baos.write(buffer, 0, count);
                }
            } while (count >= 0);

            if (baos.toString().indexOf("error") != -1) {
                return baos.toString();
            }

            return null;
        }
        catch (Exception e) {
            StringBuffer files = new StringBuffer();
            for (int i = 0; i < sourceFiles.length; ++i) {
                files.append(sourceFiles[i].getPath());
                files.append(' ');
            }
            throw new JRException("Error compiling report java source files : " + files, e);
        }
    }
}
