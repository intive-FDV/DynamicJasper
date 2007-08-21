package ar.com.fdvs.dj.domain.builders;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.entities.Subreport;

public class SubReportBuilder {

	private Subreport subreport = new Subreport();
	
	public Subreport build(){
		return subreport;
	}
	
	/**
	 * Indicates from where to get the data source.<br>
	 * 
	 * @param origin Must be one of these constans located in DJConstants inteface<br>
	 * - SUBREPORT_DATAS_OURCE_ORIGIN_PARAMETER<br>
	 * - SUBREPORT_DATAS_OURCE_ORIGIN_FIELD<br>
	 * - SUBREPORT_DATAS_OURCE_ORIGIN_INTERNAL<br>
	 * @param type tell if the datasource is a Collection, an Array, a ResultSet or whatever.<br>
	 * Its value must be a constant from {@link DJConstants} of the like DATA_SOURCE_TYPE_...
	 * @param expression is -depending on the origin- te path to the datasource<br>
	 * ie: if origin is SUBREPORT_DATAS_OURCE_ORIGIN_PARAMETER, then expression can be "subreport_datasource".<br>
	 * You must in the parameters map an object using "subreport_datasource" as the key.<br>
	 * The object must be an instance of {@link JRDataSource} or any of the following<br>
	 * Collection, Array, ResultSet, or any of the data source types provided by Jasper Reports
	 * @return
	 */
	public SubReportBuilder addDataSource(int origin, int type, String expression) {
		subreport.setDataSourceOrigin(origin);
		subreport.setDataSourceType(type);
		subreport.setDataSourceExpression(expression);
		return this;
	}

	/**
	 * like addDataSource(int origin, int type, String expression) but the type will be of the {@link JRDataSource}
	 * @param origin
	 * @param expression
	 * @return
	 */
	public SubReportBuilder addDataSource(int origin, String expression) {
		subreport.setDataSourceOrigin(origin);
		subreport.setDataSourceType(DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE);
		subreport.setDataSourceExpression(expression);
		return this;
	}

	/**
	 * like addDataSource(int origin, String expression) but the origin will be from a Parameter
	 * @param origin
	 * @param expression
	 * @return
	 */
	public SubReportBuilder addDataSource(String expression) {
		subreport.setDataSourceOrigin(DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_PARAMETER);
		subreport.setDataSourceType(DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE);
		subreport.setDataSourceExpression(expression);
		return this;
	}
	
	public SubReportBuilder addReport(JasperReport jasperReport) {
		subreport.setReport(jasperReport);
		return this;
	}
	
	
	
	
}
