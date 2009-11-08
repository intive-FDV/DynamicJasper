package ar.com.fdvs.dj.domain;

import java.util.Map;

public class ExpressionHelper {
	private static final BooleanExpression printInFirstPage = new PrintInFirstPage();
	private static final BooleanExpression printNotInFirstPage = new PrintNotInFirstPage();	
	
	public static BooleanExpression printInFirstPage() {
		return printInFirstPage;
	}
	
	public static BooleanExpression printNotInFirstPage() {
		return printNotInFirstPage;
	}

	public static BooleanExpression printWhenGroupHasMoreThanOneRecord(final String groupName) {		
		return new BooleanExpression() {
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
		public Object evaluate(Map fields, Map variables, Map parameters) {
			return new Boolean(getPageNumber(variables) == 1);
		}
	}
	
	private static class PrintNotInFirstPage extends BooleanExpression {		
		public Object evaluate(Map fields, Map variables, Map parameters) {
			return new Boolean(getPageNumber(variables) > 1);
		}
	}
}
