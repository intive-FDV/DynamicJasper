/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ar.com.fdvs.dj.util;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStyle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
		int offset = findVerticalOffset(destBand);
		
		if (destBand == null)
			throw new DJException("destination band cannot be null");
		
		if (sourceBand == null)
			return;
		
		for (Iterator iterator = sourceBand.getChildren().iterator(); iterator.hasNext();) {
			JRDesignElement element = (JRDesignElement) iterator.next();
			JRDesignElement dest = null;
			try {
				if (element instanceof JRDesignGraphicElement){
					Constructor<? extends JRDesignElement> constructor = element.getClass().getConstructor(JRDefaultStyleProvider.class);
					JRDesignStyle style = new JRDesignStyle();
					dest = constructor.newInstance(new Object[]{style.getDefaultStyleProvider()});
				} else {
					dest = (JRDesignElement) element.getClass().newInstance();
				}
				
				BeanUtils.copyProperties(dest, element);
				dest.setY(dest.getY() + offset);
			} catch (Exception e) {
				log.error("Exception copying elements from band to band: " + e.getMessage(),e);
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
	
	/**
	 * Returns the firs band from the section
	 * @param section
	 * @return
	 */
	public static JRDesignBand getBandFromSection(JRDesignSection section) {
		return (JRDesignBand) section.getBandsList().get(0);		
	}
	
}
