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

package ar.com.fdvs.dj.domain;

import java.util.Map;

import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.customexpression.PageNumberCustomExpression;
import ar.com.fdvs.dj.util.customexpression.RecordsInPageCustomExpression;
import ar.com.fdvs.dj.util.customexpression.RecordsInReportCustomExpression;

public class ExpressionHelper {
	private static final BooleanExpression printInFirstPage = new PrintInFirstPage();
	private static final BooleanExpression printNotInFirstPage = new PrintNotInFirstPage();
	private static final CustomExpression pageNumber = new PageNumberCustomExpression();
	private static final CustomExpression recordsInPage = new RecordsInPageCustomExpression();
	private static final CustomExpression recordsInReport = new RecordsInReportCustomExpression();
	
	public static BooleanExpression printInFirstPage() {
		return printInFirstPage;
	}
	
	public static BooleanExpression printNotInFirstPage() {
		return printNotInFirstPage;
	}

	/**
	 * Convenient CustomExpression that returns page number
	 */
	public static CustomExpression getPageNumber() {
		return pageNumber;
	}
	
	/**
	 * Convenient CustomExpression that returns the record number for the current page
	 */
	public static CustomExpression getRecordsInPage() {
		return recordsInPage;
	}
	
	/**
	 * Convenient CustomExpression that returns the record number for whole report
	 */
	public static CustomExpression getRecordsInReport() {
		return recordsInReport;
	}
	
	public static BooleanExpression printWhenGroupHasMoreThanOneRecord(final String groupName) {		
		return new BooleanExpression() {
			private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
			
			public Object evaluate(Map fields, Map variables, Map parameters) {
				return new Boolean(getGroupCount(groupName, variables) > 1);
			}
		};
	}
	
	public static int getPageNumber(Map variables) {
		return ((Integer) variables.get("PAGE_NUMBER")).intValue(); 
	}

	public static int getGroupCount(String groupName, Map variables) {
		return ((Integer) variables.get(groupName + "_COUNT")).intValue(); 
	}
	
	private static class PrintInFirstPage extends BooleanExpression {
		private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
		
		public Object evaluate(Map fields, Map variables, Map parameters) {
			return new Boolean(getPageNumber(variables) == 1);
		}
	}
	
	private static class PrintNotInFirstPage extends BooleanExpression {
		private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
		
		public Object evaluate(Map fields, Map variables, Map parameters) {
			return new Boolean(getPageNumber(variables) > 1);
		}
	}
}
