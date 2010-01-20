package ar.com.fdvs.dj.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignStyle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.CoreException;
import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.registration.EntitiesRegistrationException;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.DJGroup;

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
		if (sourceBand == null){
			log.debug("nothing to copy from a null band");
			return;
		}
			
		int offset = findVerticalOffset(destBand);
		for (Iterator iterator = sourceBand.getChildren().iterator(); iterator.hasNext();) {
			JRDesignElement element = (JRDesignElement) iterator.next();
			JRDesignElement dest = null;
			try {
				
				dest = (JRDesignElement) createInstanceFrom(element);
//				dest = (JRDesignElement) element.getClass().newInstance();
				BeanUtils.copyProperties(dest, element);
				dest.setY(dest.getY() + offset);
			} catch (Exception e) {
				e.printStackTrace();
			}
			destBand.addElement((JRDesignElement) dest);
		}
	}
	
	private static JRDesignElement createInstanceFrom(JRDesignElement element) throws Exception {
		if (element == null)
			return null;
		
		Constructor cc = null;

		try {
			cc = element.getClass().getConstructor(new Class[]{});
		} catch (NoSuchMethodException e) {			
			//nothing to do, there is just no default constructor.
		}

		if (cc != null)
			return (JRDesignElement) cc.newInstance(new Object[]{});
		
		try {
			cc = element.getClass().getConstructor(new Class[]{JRDefaultStyleProvider.class});
		} catch (NoSuchMethodException e) {			
			//nothing to do, there is just no default constructor.
		}
		
		if (cc != null)
			return (JRDesignElement) cc.newInstance(new Object[]{new JRDesignStyle().getDefaultStyleProvider()});
		
		
		
		throw new CoreException("Cannot copy element of the class " + element.getClass().getName());
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
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
		design.getParametersWithValues().put(name, customExpression);
	}		

	/**
	 * Returns the JRDesignGroup for the DJGroup passed
	 * @param jd
	 * @param layoutManager
	 * @param group
	 * @return
	 */
	public static JRDesignGroup getJRDesignGroup(DynamicJasperDesign jd, LayoutManager layoutManager, DJGroup group) {
		Map references = layoutManager.getReferencesMap();
		for (Iterator iterator = references.keySet().iterator(); iterator.hasNext();) {
			String groupName = (String) iterator.next();
			DJGroup djGroup = (DJGroup) references.get(groupName);
			if (group == djGroup) {
				return (JRDesignGroup) jd.getGroupsMap().get(groupName);
			}
		}
		return null;
	}
	
	public static JRDesignGroup findParentJRGroup(DJGroup djgroup, DynamicReport dr, DynamicJasperDesign djd, LayoutManager layoutManager) {
		JRDesignGroup registeredGroup;
		int gidx = dr.getColumnsGroups().indexOf(djgroup);
		if (gidx > 0) {
			gidx--;
			DJGroup djParentGroup = (DJGroup) dr.getColumnsGroups().get(gidx);
			JRDesignGroup jrParentGroup = LayoutUtils.getJRDesignGroup(djd, layoutManager, djParentGroup);
			registeredGroup = jrParentGroup;
		} else
			registeredGroup = null;
		return registeredGroup;
	}	

	public static DJGroup findChildDJGroup(DJGroup djgroup, DynamicReport dr) {
		DJGroup child = null;
		int gidx = dr.getColumnsGroups().indexOf(djgroup);
		if (gidx+1 < dr.getColumnsGroups().size()) {
			gidx++;
			child = (DJGroup) dr.getColumnsGroups().get(gidx);
		} 
		return child;
	}	
	
}
