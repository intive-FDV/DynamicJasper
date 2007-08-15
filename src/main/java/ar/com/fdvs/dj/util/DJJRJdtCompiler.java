package ar.com.fdvs.dj.util;

import net.sf.jasperreports.engine.design.JRJdtCompiler;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

import java.util.Map;

/**
 * @author Alejandro Gomez (alejandro.gomez@fdvsolutions.com)
 *         Date: Aug 15, 2007
 *         Time: 1:47:28 PM
 */
public class DJJRJdtCompiler extends JRJdtCompiler {

    protected Map getJdtSettings() {
        final Map settings = super.getJdtSettings();
        final String encoding = System.getProperty("file.encoding");
        if (encoding != null) {
            settings.put(CompilerOptions.OPTION_Encoding, encoding);
        }
        return settings;
    }
}
