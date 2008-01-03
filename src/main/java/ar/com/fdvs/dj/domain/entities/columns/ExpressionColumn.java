/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
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

import ar.com.fdvs.dj.domain.CustomExpression;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Column created to handle Custom Expressions.</br>
 * @see CustomExpression
 */
public class ExpressionColumn extends SimpleColumn {

	private static final Log log = LogFactory.getLog(ExpressionColumn.class);

	private CustomExpression expression;

	private Collection columns;

	public Collection getColumns() {
		return columns;
	}

	public void setColumns(Collection columns) {
		this.columns = columns;
	}

	public CustomExpression getExpression() {
		return expression;
	}

	public void setExpression(CustomExpression expression) {
		this.expression = expression;
	}

//	public String getTextForExpression() {
//		return "(("+CustomExpression.class.getName()+")$P{"+getColumnProperty().getProperty()+"})."+CustomExpression.EVAL_METHOD_NAME+"($F{"+getColumnProperty().getProperty()+"})";
//	}

	public String getValueClassNameForExpression() {
		return String.class.getName();
	}

	public String getTextForExpression() {
		StringBuffer sb = new StringBuffer("new  ar.com.fdvs.dj.util.PropertiesMap()");
		ArrayList properties = new ArrayList();
		for (Iterator iter = columns.iterator(); iter.hasNext();) {
			AbstractColumn col = (AbstractColumn) iter.next();
			if (col instanceof SimpleColumn && !(col instanceof ExpressionColumn)) {
				SimpleColumn propcol = (SimpleColumn) col;
				properties.add(propcol.getColumnProperty());
			}
		}
//		for (Iterator iter = .iterator(); iter.hasNext();) {
//			type element = (type) iter.next();
//
//		}


		for (Iterator iter = columns.iterator(); iter.hasNext();) {
			AbstractColumn col = (AbstractColumn) iter.next();
			if (col instanceof SimpleColumn && !(col instanceof ExpressionColumn)) {
				SimpleColumn propcol = (SimpleColumn) col;
				String propname = propcol.getColumnProperty().getProperty();
				sb.append(".with(\"" +  propname + "\",$F{" + propname + "})");
			}

		}
		String stringExpression = "((("+CustomExpression.class.getName()+")$P{"+getColumnProperty().getProperty()+"})."+CustomExpression.EVAL_METHOD_NAME+"( "+ sb.toString() +" ))";
		//		return "(("+getValueClassNameForExpression()+")$P{"+getColumnProperty().getProperty()+"})."+CustomExpression.EVAL_METHOD_NAME+"( "+ sb.toString() +" )";
		log.debug("Expression for CustomExpression = " + stringExpression);
		return stringExpression;
//		return "($P{"+getColumnProperty().getProperty()+"})."+CustomExpression.EVAL_METHOD_NAME+"( "+ sb.toString() +" )";
	}

}
