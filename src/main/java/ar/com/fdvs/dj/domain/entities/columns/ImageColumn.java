/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.domain.entities.columns;

import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Just for marking the column as ImageColum
 * @author Juan Manuel
 *
 */
public class ImageColumn extends ExpressionColumn {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private static final Log log = LogFactory.getLog(ImageColumn.class);
	
	private ImageScaleMode scaleMode = ImageScaleMode.FILL_PROPORTIONALLY;

	public ImageScaleMode getScaleMode() {
		return scaleMode;
	}

	public void setScaleMode(ImageScaleMode scaleMode) {
		this.scaleMode = scaleMode;
	}

	public String getValueClassNameForExpression() {
		if (getExpression() != null)
			return getExpression().getClassName();

		if (getColumnProperty() != null)
			return getColumnProperty().getValueClassName();
		
		return Object.class.getName();

	}

	public String getTextForExpression() {

		if (getExpression() != null) {
			if (getCalculatedExpressionText() != null)
				return getCalculatedExpressionText();
			
			String stringExpression = ExpressionUtils.createCustomExpressionInvocationText(getExpression(),getColumnProperty().getProperty(), false);
			
			log.debug("Image Column Expression for CustomExpression = " + stringExpression);
			
			setCalculatedExpressionText( stringExpression);
			return stringExpression;
		}
		
		if (getColumnProperty() != null)
			return  "$F{" + getColumnProperty().getProperty() + "}";
		
		throw new DJException("Neither a CustomExpression or a ColumnProperty was set for colulm \"" + getName() + "\"");
		
		
	}
	
	
}
