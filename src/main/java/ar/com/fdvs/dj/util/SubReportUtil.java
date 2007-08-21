package ar.com.fdvs.dj.util;

import java.sql.ResultSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.entities.Subreport;

public class SubReportUtil {
	
	private static final String REPORT_PARAMETERS_MAP = "$P{REPORT_PARAMETERS_MAP}";

	/**
	 * Returns an expression that points to a java.util.Map object with the parameters to 
	 * be used during the subreport fill time.
	 * Posibilities are:<br> 
	 * - Use Partent report Map<br>
	 * - Use a Map that is a parameter of the partents map<br>
	 * - Use a property of the current row.
	 * @param sr
	 * @return
	 */
	public static JRDesignExpression getParameterExpression(Subreport sr) {
		JRDesignExpression exp = new JRDesignExpression();
		exp.setValueClassName(java.util.Map.class.getName());
		if (sr.isUseParentReportParameters()){
			exp.setText(REPORT_PARAMETERS_MAP);			
			return exp;
		}
		
		if (sr.getParametersExpression() == null)
			return null;
		
		if (sr.getParametersMapOrigin() == DJConstants.SUBREPORT_PARAMETER_MAP_ORIGIN_PARAMETER){
			exp.setText(REPORT_PARAMETERS_MAP + ".get( \""+ sr.getParametersExpression() +"\" )");
			return exp;
		}
		
		if (sr.getParametersMapOrigin() == DJConstants.SUBREPORT_PARAMETER_MAP_ORIGIN_FIELD){
			exp.setText("$F{"+ sr.getParametersExpression() +"}");
			return exp;
		}
		
		return null;
	}

	/**
	 * Returns the expression string required
	 * @param sr
	 * @return
	 */
	public static JRExpression getDataSourceExpression(Subreport sr) {
		JRDesignExpression exp = new JRDesignExpression();
		exp.setValueClass(JRDataSource.class);
		
		String dsType = getDataSourceTypeStr(sr);
		String dsOrigin = "$F";
		if (sr.getDataSourceOrigin() == DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_PARAMETER)
			dsOrigin = "$P";
		
		exp.setText(dsType + dsOrigin+"{" + sr.getDataSourceExpression() + "})");
		
		return exp;
	}

	private static String getDataSourceTypeStr(Subreport sr) {
		//TODO Complete all other possible types
		String dsType = "(";
		if (DJConstants.DATA_SOURCE_TYPE_COLLECTION == sr.getDataSourceType()){
			 dsType = "new "+JRBeanCollectionDataSource.class.getName()+"((java.util.Collection)";
		}
		else if (DJConstants.DATA_SOURCE_TYPE_ARRAY == sr.getDataSourceType()){
			dsType = "new "+JRBeanArrayDataSource.class.getName()+"((Object[])";
		}
		else if (DJConstants.DATA_SOURCE_TYPE_RESULTSET == sr.getDataSourceType()){
			dsType = "new "+JRResultSetDataSource.class.getName()+"(("+ResultSet.class.getName() +")";
		}
		else if (DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE == sr.getDataSourceType()){
			dsType = "(("+JRDataSource.class.getName() +")";
		}
		return dsType;
	}

}
