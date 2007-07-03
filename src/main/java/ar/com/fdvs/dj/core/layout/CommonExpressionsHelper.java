package ar.com.fdvs.dj.core.layout;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;

//TODO: Design not being used
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
	public static void addPageXofY(final JasperDesign design, final DynamicReport report, JRDesignBand band, HorizontalBandAlignment alignment) {

		int detailHeight = report.getOptions().getDetailHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		//TODO: i18n
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression("Page ", " of "));
		pageNumber.setHeight(detailHeight);
		pageNumber.setWidth(80);
		
		JRDesignTextField pageCounter = new JRDesignTextField();
		//TODO: i18n
		pageCounter.setExpression(ExpressionUtils.getPageNumberExpression("", ""));
		pageCounter.setHeight(detailHeight);
		pageCounter.setWidth(20);
		pageCounter.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
		band.addElement(pageCounter);
		//TODO: what if center?
		int pageNumberOffset = (alignment == HorizontalBandAlignment.LEFT) ? 0 : pageCounter.getWidth();
		int pageCounterOffset = (alignment == HorizontalBandAlignment.LEFT) ? pageNumber.getWidth() : 0;
		
		alignment.align(report.getOptions().getPrintableWidth(), pageNumberOffset, band, pageNumber);
		alignment.align(report.getOptions().getPrintableWidth(), pageCounterOffset, band, pageCounter);

		band.setHeight(band.getHeight() + detailHeight);
		
	}
	
	public static void addPageXSlashY(final JasperDesign design, final DynamicReport report, JRDesignBand band, HorizontalBandAlignment alignment) {
		
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

		alignment.align(report.getOptions().getPrintableWidth(), 0, band, pageNumber);
		alignment.align(report.getOptions().getPrintableWidth(), pageNumber.getWidth(), band, pageCounter);
		
		band.setHeight(band.getHeight() + detailHeight);
		
	}
	
	public static void addCreationDate(final JasperDesign design, final DynamicReport report, JRDesignBand band, HorizontalBandAlignment alignment) {
		int detailHeight = report.getOptions().getDetailHeight().intValue();
		
		JRDesignTextField date = new JRDesignTextField();
		date.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		//TODO: i18n
		date.setExpression(ExpressionUtils.getDateExpression("Created on", ""));
		date.setHeight(detailHeight);
		date.setWidth(200);
		alignment.align(report.getOptions().getPrintableWidth(), 0, band, date);
		band.setHeight(band.getHeight() + detailHeight);
		
	}

}
