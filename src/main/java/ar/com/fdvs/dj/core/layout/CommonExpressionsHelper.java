package ar.com.fdvs.dj.core.layout;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;

/**
 * @author msimone
 *
 */
public class CommonExpressionsHelper {

	/**
	 * @param report 
	 * @param design 
	 * @param footerband
	 */
	public static void addPageXofY(final JasperDesign design, final DynamicReport report, JRDesignBand band) {

		int detailHeight = report.getOptions().getDetailHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		//TODO: i18n
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression("Pagina ", " de "));
		pageNumber.setHeight(detailHeight);
		pageNumber.setWidth(80);
		
		JRDesignTextField pageCounter = new JRDesignTextField();
		//TODO: i18n
		pageCounter.setExpression(ExpressionUtils.getPageNumberExpression("", ""));
		pageCounter.setHeight(detailHeight);
		pageCounter.setWidth(20);
		pageCounter.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
		band.addElement(pageCounter);

		addRightAligned(report, band, pageNumber, pageCounter.getWidth());
		addRightAligned(report, band, pageCounter, 0);
		band.setHeight(band.getHeight() + detailHeight);
		
	}
	
	public static void addPageXSlashY(final JasperDesign design, final DynamicReport report, JRDesignBand band) {
		
		int detailHeight = report.getOptions().getDetailHeight().intValue();
		
		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression("", ""));
		pageNumber.setHeight(detailHeight);
		pageNumber.setWidth(20);
		
		JRDesignTextField pageCounter = new JRDesignTextField();
		pageCounter.setExpression(ExpressionUtils.getPageNumberExpression("/", ""));
		pageCounter.setHeight(detailHeight);
		pageCounter.setWidth(20);
		pageCounter.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
		pageCounter.setX(pageNumber.getX() + pageNumber.getWidth());

		addLeftAligned(report, band, pageNumber, pageCounter.getWidth());
		addLeftAligned(report, band, pageCounter, 0);
		
		band.setHeight(band.getHeight() + detailHeight);
		
	}
	
	public static void addCreationDate(final JasperDesign design, final DynamicReport report, JRDesignBand band) {
		
		int detailHeight = report.getOptions().getDetailHeight().intValue();
		
		JRDesignTextField date = new JRDesignTextField();
		date.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		//TODO: i18n
		date.setExpression(ExpressionUtils.getDateExpression("Created on", ""));
		date.setHeight(detailHeight);
		date.setWidth(200);
		addRightAligned(report, band, date, 0);
		band.setHeight(band.getHeight() + detailHeight);
		
	}
	
	private static void addLeftAligned(final DynamicReport report, JRDesignBand band, JRDesignTextField textField, int xOffset) {
		textField.setX(xOffset);
		band.addElement(textField);
	}
	
	private static void addRightAligned(final DynamicReport report, JRDesignBand band, JRDesignTextField textField, int xOffset) {
		int width = report.getOptions().getPrintableWidth();
		width -= textField.getWidth();
		width -= xOffset;
	
		textField.setX(width);
		band.addElement(textField);
	}
	
//	private static void addCentered(final DynamicReport report, JRDesignBand band, JRDesignTextField textField) {
//	}
	

}
