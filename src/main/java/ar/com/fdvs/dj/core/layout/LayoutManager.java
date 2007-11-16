/**
 * 
 */
package ar.com.fdvs.dj.core.layout;

import net.sf.jasperreports.engine.design.JasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;

/**
 * An interface that represents a a Manager to make elements respect a desired Layout.
 * @author msimone
 */
public interface LayoutManager {

	/**
	 * Entry point for applying a given layout.
	 * @param design The deseign to be used
	 * @param report The report to show
	 * @throws LayoutException
	 */
	void applyLayout(JasperDesign design, DynamicReport report) throws LayoutException;

	
}
