/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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

package ar.com.fdvs.dj.core.layout;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;

/**
 * Abstract Class used as base for the different Layout Managers.</br>
 * </br>
 * A Layout Manager is always invoked after the entities registration stage.</br>
 * A subclass should be created whenever we want to give the users the chance to </br>
 * easily apply global layout changes to their reports. Example: Ignore groups </br>
 * and styles for an Excel optimized report.
 */
public abstract class AbstractLayoutManager {

	private static final Log log = LogFactory.getLog(AbstractLayoutManager.class);
	protected static final String EXPRESSION_TRUE_WHEN_ODD = "new java.lang.Boolean(((Number)$V{REPORT_COUNT}).doubleValue() % 2 == 0)";

	private JasperDesign design;
	private DynamicReport report;

	public AbstractLayoutManager() {
		super();
	}

	protected JasperDesign getDesign() {
		return design;
	}

	protected void setDesign(JasperDesign design) {
		this.design = design;
	}

	protected DynamicReport getReport() {
		return report;
	}

	protected void setReport(DynamicReport report) {
		this.report = report;
	}

	protected void transformDetailBandTextField(AbstractColumn column, JRDesignTextField textField) {}

	private final void transformDetailBand() {
		log.debug("transforming Detail Band...");
		JRDesignBand detail = (JRDesignBand) design.getDetail();
		detail.setHeight(report.getOptions().getDetailHeight().intValue());

		if (getReport().getOptions().isPrintBackgroundOnOddRows()){
			decorateOddRows(detail);
		}

		for (Iterator iter = report.getColumns().iterator(); iter.hasNext();) {

			AbstractColumn column = (AbstractColumn)iter.next();

			if (column.getConditionalStyles() != null && !column.getConditionalStyles().isEmpty() ){
        		for (Iterator iterator = column.getConditionalStyles().iterator(); iterator.hasNext();) {
					ConditionalStyle condition = (ConditionalStyle) iterator.next();
					JRDesignTextField textField = generateTextFieldFromColumn(column, getReport().getOptions().getDetailHeight().intValue(), null);
					transformDetailBandTextField(column, textField);
					applyStyleToTextElement(condition.getStyle(), textField);
					textField.setPrintWhenExpression(getExpressionForConditionalStyle(condition.getName(), column.getTextForExpression()));
					detail.addElement(textField);
				}
        	} else {
        		JRDesignTextField textField = generateTextFieldFromColumn(column, getReport().getOptions().getDetailHeight().intValue(), null);
        		transformDetailBandTextField(column, textField);
        		detail.addElement(textField);
        	}
        }
	}

	/**
	 * Places a square as DetailBand background for pair rows.
	 * @param JRDesignBand detail
	 */
	private void decorateOddRows(JRDesignBand detail) {
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(Boolean.class);
		expression.setText(EXPRESSION_TRUE_WHEN_ODD);

		JRDesignRectangle rectangle = new JRDesignRectangle();
		rectangle.setPrintWhenExpression(expression);
		DynamicReportOptions options = getReport().getOptions();
		rectangle.setHeight(options.getDetailHeight().intValue());
		rectangle.setWidth(options.getColumnWidth());
		rectangle.setStretchType(JRDesignRectangle.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT);
		JRBaseStyle style = options.getOddRowBackgroundStyle().transform();
		style.setForecolor(options.getOddRowBackgroundStyle().getBackgroundColor());
		rectangle.setStyle(style);
		detail.addElement(rectangle);

	}

	/**
	 * Creates and returns the expression used to apply a conditional style.
	 * @param String paramName
	 * @param String textForExpression
	 * @return JRExpression
	 */
	private JRExpression getExpressionForConditionalStyle(String paramName, String textForExpression) {
		 String text = "(("+CustomExpression.class.getName()+")$P{"+paramName+"})."+CustomExpression.EVAL_METHOD_NAME+"("+textForExpression+")";
		 JRDesignExpression expression = new JRDesignExpression();
		 expression.setValueClass(Boolean.class);
		 expression.setText(text);
		 return expression;
	}

	protected void startLayout() {
		setColumnsFinalWidth();
	}

	protected void endLayout() {
		setBandsFinalHeight();
		EliminateEmptyBands();
	}

	/**
	 * Entry point for applying a given layout.
	 * @param JasperDesign design
	 * @param DynamicReport report
	 * @throws LayoutException
	 */
	public final void applyLayout(JasperDesign design, DynamicReport report) throws LayoutException {
		log.debug("Applying Layout...");
		try {
			setDesign(design);
			setReport(report);
			ensureStyles();
			startLayout();
			transformDetailBand();
			endLayout();
		} catch (RuntimeException e) {
			throw new LayoutException(e.getMessage());
		}
	}

	protected void ensureStyles() {
		Style defaultDetailStyle = getReport().getOptions().getDefaultDetailStyle();
		Style defaultHeaderStyle = getReport().getOptions().getDefaultHeaderStyle();
		Style defaultFooterStyle = getReport().getOptions().getDefaultFooterStyle();
		Style defaultGroupHeaderStyle = getReport().getOptions().getDefaultGroupHeaderStyle();
		Style defaultGroupFooterStyle = getReport().getOptions().getDefaultGroupFooterStyle();
		
		for (Iterator iter = report.getColumns().iterator(); iter.hasNext();) {
			AbstractColumn column = (AbstractColumn) iter.next();
			if (column.getStyle() == null) column.setStyle(defaultDetailStyle);
			if (column.getHeaderStyle() == null) column.setHeaderStyle(defaultHeaderStyle);
		}
		
//		for (Iterator iter = report.getColumnsGroups().iterator(); iter.hasNext();) {
//			ColumnsGroup group = (ColumnsGroup) iter.next();
//			group.getColumnToGroupBy().set
//			
//		}
		
	}

	protected final void generateHeaderBand(JRDesignBand band) {
		log.debug("Generating header band...");
		band.setHeight(report.getOptions().getHeaderHeight().intValue());

		for (Iterator iter = report.getColumns().iterator(); iter.hasNext();) {

			AbstractColumn col = (AbstractColumn) iter.next();
			if (col.getTitle() == null)
				continue;

			JRDesignExpression expression = new JRDesignExpression();
			JRDesignTextField textField = new JRDesignTextField();
			expression.setText("\""+ col.getTitle() + "\"");

			expression.setValueClass(String.class);

			textField.setKey("header_"+col.getTitle());
			textField.setExpression(expression);

			textField.setX(col.getPosX().intValue());
			textField.setY(col.getPosY().intValue());
			textField.setHeight(band.getHeight());
			textField.setWidth(col.getWidth().intValue());

			textField.setPrintWhenDetailOverflows(true);
			textField.setBlankWhenNull(true);

			Style headerStyle = col.getHeaderStyle();
			if (headerStyle == null)
				headerStyle = report.getOptions().getDefaultHeaderStyle();
			
			applyStyleToTextElement(headerStyle, textField);

			band.addElement(textField);
		}
	}

	protected final void applyStyleToTextElement(Style style, JRDesignTextElement textElement) {
		textElement.setStyle(style.transform());
		if (textElement instanceof JRDesignTextElement ) {
			JRDesignTextElement textField = (JRDesignTextElement) textElement;
			textField.setStretchType(style.getStreching().getValue());
			textField.setPositionType(JRTextField.POSITION_TYPE_FLOAT);
		}
		if (textElement instanceof JRDesignTextField ) {
			JRDesignTextField textField = (JRDesignTextField) textElement;
			textField.setStretchWithOverflow(style.isStretchWithOverflow());
		}
	}

	/**
	 * Sets the columns width by reading some report options like the
	 * printableArea and useFullPageWidth.
	 */
	private final void setColumnsFinalWidth() {
		log.debug("Setting columns final width...");
		float factor = 1;
		int printableArea = report.getOptions().getColumnWidth();

		if (report.getOptions().isUseFullPageWidth()) {
			int columnsWidth = 0;
			for (Iterator iterator =  report.getColumns().iterator(); iterator.hasNext();) {
				AbstractColumn col = (AbstractColumn) iterator.next();
				columnsWidth += col.getWidth().intValue();
			}
			factor = (float) printableArea / (float) columnsWidth;
			int acu = 0;
			int colFinalWidth = 0;
			for (Iterator iter = report.getColumns().iterator(); iter.hasNext();) {
				AbstractColumn col = (AbstractColumn) iter.next();
				if (!iter.hasNext()) {
					int acu2 = acu;
					acu += (printableArea - acu);
					col.setWidth(new Integer(printableArea - acu2));
				} else {
					colFinalWidth = (new Float(col.getWidth().intValue() * factor)).intValue();
					acu += colFinalWidth;
					col.setWidth(new Integer(colFinalWidth));
				}
			}
		}

		// If the columns width changed, they must be setted again.
		int posx = 0;
		for (Iterator iterator =  report.getColumns().iterator(); iterator.hasNext();) {
			AbstractColumn col = (AbstractColumn) iterator.next();
			col.setPosX(new Integer(posx));
			posx += col.getWidth().intValue();
		}
	}

	private final void setBandsFinalHeight() {
		log.debug("Setting bands final height...");
		for (Iterator iter = design.getGroupsList().iterator(); iter.hasNext();) {
			JRGroup jrgroup = (JRGroup) iter.next();

			JRDesignBand band = (JRDesignBand) jrgroup.getGroupHeader();
			setBandFinalHeight(band);

			band = (JRDesignBand) jrgroup.getGroupFooter();
			setBandFinalHeight(band);
		}
	}

	/**
	 * Equals a band height to it's tallest child one.
	 * @param JRDesignBand band
	 */
	private final void setBandFinalHeight(JRDesignBand band) {
		int finalHeight = 0;

		for (Iterator iter = band.getChildren().iterator(); iter.hasNext();) {
			JRDesignElement element = (JRDesignElement) iter.next();
			int currentHeight = element.getY() + element.getHeight();

			if (currentHeight > finalHeight)
				finalHeight = currentHeight;
		}

		band.setHeight(finalHeight);
	}

	/**
	 * Sets zero height to all the report bands without any children.
	 */
	private final void EliminateEmptyBands() {
		log.debug("Eliminating empty bands...");
		JRDesignBand header = (JRDesignBand) design.getPageHeader();
		if (header != null && header.getChildren().isEmpty())
			header.setHeight(0);

		JRDesignBand footer = (JRDesignBand) design.getPageFooter();
		if (footer != null && footer.getChildren().isEmpty())
			footer.setHeight(0);

		JRDesignBand columnHeader = (JRDesignBand) design.getColumnHeader();
		if (columnHeader != null && columnHeader.getChildren().isEmpty())
			columnHeader.setHeight(0);

		JRDesignBand columnFooter = (JRDesignBand) design.getColumnFooter();
		if (columnFooter != null && columnFooter.getChildren().isEmpty())
			columnFooter.setHeight(0);

		for (Iterator iter = design.getGroupsList().iterator(); iter.hasNext();) {
			JRGroup jrgroup = (JRGroup) iter.next();

			JRDesignBand band = (JRDesignBand) jrgroup.getGroupHeader();
			if (band.getChildren().isEmpty())
				band.setHeight(0);

			band = (JRDesignBand) jrgroup.getGroupFooter();
			if (band.getChildren().isEmpty())
				band.setHeight(0);
		}
	}

	/**
	 * Creates a JasperReport DesignTextField from a DynamicJasper AbstractColumn.
	 * @param AbstractColumn col
	 * @param int height
	 * @param ColumnsGroup group
	 * @return JRDesignTextField
	 */
	protected final JRDesignTextField generateTextFieldFromColumn(AbstractColumn col, int height, ColumnsGroup group) {
		JRDesignTextField textField = new JRDesignTextField();
		JRDesignExpression exp = new JRDesignExpression();

		if (col.getPattern() != null && "".equals(col.getPattern().trim())) {
			textField.setPattern(col.getPattern());
        }

		exp.setText(col.getTextForExpression());
		exp.setValueClassName(col.getValueClassNameForExpression());
		textField.setExpression(exp);
		textField.setWidth(col.getWidth().intValue());
		textField.setX(col.getPosX().intValue());
		textField.setY(col.getPosY().intValue());
		textField.setHeight(height);
		
		textField.setBlankWhenNull(col.getBlankWhenNull());

		textField.setPattern(col.getPattern());

		textField.setPrintRepeatedValues(col.getPrintRepeatedValues().booleanValue());

        textField.setPrintWhenDetailOverflows(true);

        Style columnStyle = col.getStyle();
        if (columnStyle == null) 
        	columnStyle = report.getOptions().getDefaultDetailStyle();
        
		applyStyleToTextElement(columnStyle, textField);

        if (group != null) {
        	int index = getReport().getColumnsGroups().indexOf(group);
            JRDesignGroup previousGroup = (JRDesignGroup) getDesign().getGroupsList().get(index);
            textField.setPrintWhenGroupChanges(previousGroup);
        }
        return textField;
	}

}
