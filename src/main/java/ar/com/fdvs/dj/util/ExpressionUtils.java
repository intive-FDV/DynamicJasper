package ar.com.fdvs.dj.util;

import java.sql.ResultSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.entities.Subreport;

public class ExpressionUtils {
	
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
	 * @param ds
	 * @return
	 */
	public static JRDesignExpression getDataSourceExpression(DJDataSource ds) {
		JRDesignExpression exp = new JRDesignExpression();
		exp.setValueClass(JRDataSource.class);
		
		String dsType = getDataSourceTypeStr(ds.getDataSourceType());
		String expText = null;
		if (ds.getDataSourceOrigin() == DJConstants.DATA_SOURCE_ORIGIN_FIELD){
			expText = dsType + "$F{" + ds.getDataSourceExpression() + "})";
		} else if (ds.getDataSourceOrigin() == DJConstants.DATA_SOURCE_ORIGIN_PARAMETER){
			expText = dsType + REPORT_PARAMETERS_MAP + ".get( \""+ ds.getDataSourceExpression() +"\" ) )";
		}
		
		exp.setText(expText);
		
		return exp;
	}
	
	protected static String getDataSourceTypeStr(int datasourceType) {
		//TODO Complete all other possible types
		String dsType = "(";
		if (DJConstants.DATA_SOURCE_TYPE_COLLECTION == datasourceType){
			 dsType = "new "+JRBeanCollectionDataSource.class.getName()+"((java.util.Collection)";
		}
		else if (DJConstants.DATA_SOURCE_TYPE_ARRAY == datasourceType){
			dsType = "new "+JRBeanArrayDataSource.class.getName()+"((Object[])";
		}
		else if (DJConstants.DATA_SOURCE_TYPE_RESULTSET == datasourceType){
			dsType = "new "+JRResultSetDataSource.class.getName()+"(("+ResultSet.class.getName() +")";
		}
		else if (DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE == datasourceType){
			dsType = "(("+JRDataSource.class.getName() +")";
		}
		return dsType;
	}

	public static JRDesignExpression createStringExpression(String text) {
		JRDesignExpression exp = new JRDesignExpression();
		exp.setValueClass(String.class);
		exp.setText(text);
		return exp;
	}
	public static JRDesignExpression createExpression(String text, Class clazz) {
		JRDesignExpression exp = new JRDesignExpression();
		exp.setValueClass(clazz);
		exp.setText(text);
		return exp;
	}
	public static JRDesignExpression createExpression(String text, String className) {
		JRDesignExpression exp = new JRDesignExpression();
		exp.setValueClassName(className);
		exp.setText(text);
		return exp;
	}	
	
}
