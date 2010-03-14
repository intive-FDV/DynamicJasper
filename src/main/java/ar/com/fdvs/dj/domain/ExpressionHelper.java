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
