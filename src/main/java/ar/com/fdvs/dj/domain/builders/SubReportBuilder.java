package ar.com.fdvs.dj.domain.builders;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Subreport;

public class SubReportBuilder {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(SubReportBuilder.class);


	private Subreport subreport = new Subreport();
	
	public Subreport build() throws DJBuilderException {
		if (subreport.getPath() == null && subreport.getDynamicReport() == null && subreport.getReport() == null)
			throw new DJBuilderException("No subreport origin defined (path, dynamicReport, jasperReport)");
		
		//If the subreport comes from a file, then we load it now
		if (subreport.getPath() != null) {
			JasperReport jr = null;
			File file = new File(subreport.getPath());
			if (file.exists()){
				logger.debug("Loading subreport from file path");
				try {
					jr = (JasperReport) JRLoader.loadObject(file);
				} catch (JRException e) {
					throw new DJBuilderException("Could not load subreport.",e);
				}
			} else {
				logger.debug("Loading subreport from classpath");
				URL url = DynamicJasperHelper.class.getClassLoader().getResource(subreport.getPath());
				try {
					jr = (JasperReport) JRLoader.loadObject(url.openStream());
				} catch (IOException e) {
					throw new DJBuilderException("Could not open subreport as an input stream",e);
				} catch (JRException e) {
					throw new DJBuilderException("Could not load subreport.",e);
				}
			}
			
			subreport.setReport(jr);
		}
		
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
	public SubReportBuilder setDataSource(int origin, int type, String expression) {
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
	public SubReportBuilder setDataSource(int origin, String expression) {
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
	public SubReportBuilder setDataSource(String expression) {
		subreport.setDataSourceOrigin(DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_PARAMETER);
		subreport.setDataSourceType(DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE);
		subreport.setDataSourceExpression(expression);
		return this;
	}
	
	public SubReportBuilder setReport(JasperReport jasperReport) {
		subreport.setReport(jasperReport);
		return this;
	}

	public SubReportBuilder setDynamicReport(DynamicReport dynamicReport, LayoutManager layoutManager) {
		subreport.setDynamicReport(dynamicReport);
		subreport.setLayoutManager(layoutManager);
		return this;
	}

	public SubReportBuilder setPathToReport(String path) {
		subreport.setPath(path);
		return this;
	}
	
	
	
	
}
