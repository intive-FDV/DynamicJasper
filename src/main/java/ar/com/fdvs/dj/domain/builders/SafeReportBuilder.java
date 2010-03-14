package ar.com.fdvs.dj.domain.builders;

import ar.com.fdvs.dj.domain.DynamicReport;

public class SafeReportBuilder extends DynamicReportBuilder {
	private boolean built = false;

	/**
	 * delegates to the super class after checking that we have not already been
	 * built.
	 * 
	 * @see DynamicReportBuilder.build()
	 * @throws IllegalStateException
	 *             if the report has already been built
	 */
	public DynamicReport build() {
		if (built == true) {
			throw new IllegalStateException("Cannot build the dynamic jasper report	twice");
		}
		built = true;
		return super.build();
	}
}
