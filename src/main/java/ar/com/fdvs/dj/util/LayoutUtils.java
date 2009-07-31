package ar.com.fdvs.dj.util;

import java.util.Iterator;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignParameter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.registration.EntitiesRegistrationException;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;

public class LayoutUtils {
	
	static final Log log = LogFactory.getLog(LayoutUtils.class);
	
	/**
	 * Finds "Y" coordinate value in which more elements could be added in the band
	 * @param band
	 * @return
	 */
	public static int findVerticalOffset(JRDesignBand band) {
		int finalHeight = 0;
		if (band != null) {
			for (Iterator iter = band.getChildren().iterator(); iter.hasNext();) {
				JRDesignElement element = (JRDesignElement) iter.next();
				int currentHeight = element.getY() + element.getHeight();
				if (currentHeight > finalHeight) finalHeight = currentHeight;
			}
			return finalHeight;
		}
		return finalHeight;
	}
	
	/**
	 * Copy bands elements from source to dest band, also makes sure copied elements
	 * are placed below existing ones (Y offset is calculated)
	 * @param destBand
	 * @param sourceBand
	 */
	public static void copyBandElements(JRDesignBand destBand, JRBand sourceBand) {
		int offset = findVerticalOffset(destBand);
		for (Iterator iterator = sourceBand.getChildren().iterator(); iterator.hasNext();) {
			JRDesignElement element = (JRDesignElement) iterator.next();
			JRDesignElement dest = null;
			try {
				dest = (JRDesignElement) element.getClass().newInstance();
				BeanUtils.copyProperties(dest, element);
				dest.setY(dest.getY() + offset);
			} catch (Exception e) {
				e.printStackTrace();
			}
			destBand.addElement((JRDesignElement) dest);
		}
	}
	
	/**
	 * Moves the elements contained in "band" in the Y axis "yOffset"
	 * @param intValue
	 * @param band
	 */
	public static void moveBandsElemnts(int yOffset, JRDesignBand band) {
		if (band == null)
			return;

		for (Iterator iterator = band.getChildren().iterator(); iterator.hasNext();) {
			JRDesignElement elem = (JRDesignElement) iterator.next();
			elem.setY(elem.getY()+yOffset);
		}
	}	
	
	public static void registerCustomExpressionParameter(DynamicJasperDesign design,String name, CustomExpression customExpression) {
		if (customExpression == null){
			return;
		}
		JRDesignParameter dparam = new JRDesignParameter();
		dparam.setName(name);
		dparam.setValueClassName(CustomExpression.class.getName());
		log.debug("Registering customExpression parameter with name " + name );
		try {
			design.addParameter(dparam);
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage());
		}
		design.getParametersWithValues().put(name, customExpression);
	}		

}
