package ar.com.fdvs.dj.test.groups;

import java.util.Map;

import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.fill.JRFillParameter;
import net.sf.jasperreports.engine.fill.JRFillVariable;

import org.apache.log4j.Logger;

import ar.com.fdvs.dj.core.DJDefaultScriptlet;

/**
 * It is very important to extend {@link DJDefaultScriptlet} because it is needed for the normal operation of the Report.
 * @author mamana
 *
 */
public class MyDjScriptlet extends DJDefaultScriptlet {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MyDjScriptlet.class);
	private Map precalculatedValues;

	public void afterGroupInit(String groupName) throws JRScriptletException {		
		super.afterGroupInit(groupName);
		
		String currentGroupValue = (String) getFieldValue("state");
		
		logger.debug("afterGroupInit [" + groupName + "] = " + currentGroupValue);

        JRFillParameter report = parametersMap.get("JASPER_REPORT");
        String prefix = ((JasperReport)report.getValue()).getName() + "_";
		
		/*
		  Recover JasperReport variable (the holder for the calculated value)
		  The naming convention used in DJ is: <ReportName>_variable-<[header|footer]>_<grouped column property>_<column with the variable>
		  As an example: XXXX_variable-header_state_quantity stands for: a header variable placed in the group for the column "state"
		  which calculates in the column "quantity"
          In the example, XXXX is the report name which is obtained from current JasperReport object (from the parameters map)
		 */
		JRFillVariable var = variablesMap.get(prefix + "variable-header_state_quantity");
		
		/*
		  Here We get the precalculated value for the current group
		 */
		Object precalculatedValue = getPrecalculatedValue("state", currentGroupValue);
		
		var.setValue(precalculatedValue);
		
	}

	private Object getPrecalculatedValue(String fieldName, String currentValue) {
		return precalculatedValues.get(currentValue);
	}

	public void setPrecalculatedValues(Map precalculateValues) {
		this.precalculatedValues = precalculateValues;
		
	}

}
