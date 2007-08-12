package ar.com.fdvs.dj.domain.entities;

import ar.com.fdvs.dj.domain.Style;
import net.sf.jasperreports.engine.JasperReport;

public class Subreport {
	
	private JasperReport report;
	private String dataSourceExpression;
	private Style style;
	
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	public JasperReport getReport() {
		return report;
	}
	public void setReport(JasperReport design) {
		this.report = design;
	}
	public String getDataSourceExpression() {
		return dataSourceExpression;
	}
	public void setDataSourceExpression(String dataSourceExpression) {
		this.dataSourceExpression = dataSourceExpression;
	}

}
