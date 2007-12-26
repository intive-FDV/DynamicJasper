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

import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.base.JRBaseVariable;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.linear.code39.Code39Barcode;
import net.sourceforge.barbecue.output.OutputException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.security.jca.GetInstance.Instance;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJChartOptions;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DataSetFactory;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.BarCodeColumn;
import ar.com.fdvs.dj.domain.entities.columns.ImageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;

/**
 * Abstract Class used as base for the different Layout Managers.</br>
 * </br>
 * A Layout Manager is always invoked after the entities registration stage.</br>
 * A subclass should be created whenever we want to give the users the chance to </br>
 * easily apply global layout changes to their reports. Example: Ignore groups </br>
 * and styles for an Excel optimized report.
 */
public abstract class AbstractLayoutManager implements LayoutManager {

	static final Log log = LogFactory.getLog(AbstractLayoutManager.class);
	protected static final String EXPRESSION_TRUE_WHEN_ODD = "new java.lang.Boolean(((Number)$V{REPORT_COUNT}).doubleValue() % 2 == 0)";

	JasperDesign design;
	private DynamicReport report;

	protected abstract void transformDetailBandTextField(AbstractColumn column, JRDesignTextField textField);
	
	private HashMap reportStyles = new HashMap();

	public HashMap getReportStyles() {
		return reportStyles;
	}

	public void setReportStyles(HashMap reportStyles) {
		this.reportStyles = reportStyles;
	}

	public void applyLayout(JasperDesign design, DynamicReport report) throws LayoutException {
		log.debug("Applying Layout...");
		try {
			setDesign(design);
			setReport(report);
			ensureDJStyles();
			startLayout();
			transformDetailBand();
			endLayout();
			registerRemainingStyles();
		} catch (RuntimeException e) {
			throw new LayoutException(e.getMessage(),e);
		} 
	}
	

	protected void startLayout() {
		setColumnsFinalWidth();
	}

	protected void endLayout() {
		layoutCharts();
		setBandsFinalHeight();
	}
	
	protected void registerRemainingStyles() {
		//TODO: troll all elements in the JRDesing and for elements that has styles with null name 
		//or not registered, register them in the design
		
	}

	/**
	 * Sets a default style for every element that doesn't have one
	 * @throws JRException 
	 */
	protected void ensureDJStyles()  {
		
		//first of all, register all parent styles if any
		for (Iterator iterator = getReport().getStyles().values().iterator(); iterator.hasNext();) {
			Style style = (Style) iterator.next();
			addStyleToDesign(style);			
		}
		
		Style defaultDetailStyle = getReport().getOptions().getDefaultDetailStyle();
			
			Style defaultHeaderStyle = getReport().getOptions().getDefaultHeaderStyle();
	//		Style defaultFooterStyle = getReport().getOptions().getDefaultFooterStyle();
			for (Iterator iter = report.getColumns().iterator(); iter.hasNext();) {
				AbstractColumn column = (AbstractColumn) iter.next();
				if (column.getStyle() == null) 
					column.setStyle(defaultDetailStyle);
				if (column.getHeaderStyle() == null) 
					column.setHeaderStyle(defaultHeaderStyle);
	//			if (column.getFooterStyle() == null) column.setFooterStyle(defaulyFooterStyle);	
				
//				addStyleToDesign(column.getStyle().transform());
//				addStyleToDesign(column.getHeaderStyle().transform());
			}

	}

	/**
	 * @param baseStyle
	 * @throws JRException
	 */
	public void addStyleToDesign(Style style)  {
		JRDesignStyle jrstyle = style.transform();
		try {
			if (jrstyle.getName() == null) {
				String name = "dj_style_" + (getReportStyles().values().size() + 1);
				jrstyle.setName(name);
				style.setName(name);
				getReportStyles().put(name, jrstyle);
				design.addStyle(jrstyle);
			}
			
			JRStyle old = (JRStyle) design.getStylesMap().get(jrstyle.getName());
			if (old != null && style.isOverridesExistingStyle()){
				log.info("Overriding style with name \""+ style.getName() +"\"");
				
				design.removeStyle(style.getName());
				design.addStyle(jrstyle);
			} else if (old == null){
				log.info("Registering new style with name \""+ style.getName() +"\"");
				design.addStyle(jrstyle);
			} else {
				if (style.getName() != null)
					log.info("Using existing style for style with name \""+ style.getName() +"\"");
			}
		} catch (JRException e) {
			log.info("Duplicated style (it's ok): " + e.getMessage());
		}
	}
	
	/**
	 * For each column, puts the elements in the detail band
	 */
	protected void transformDetailBand() {
		log.debug("transforming Detail Band...");
		JRDesignBand detail = (JRDesignBand) design.getDetail();
		detail.setHeight(report.getOptions().getDetailHeight().intValue());

		if (getReport().getOptions().isPrintBackgroundOnOddRows()){
			decorateOddRows(detail);
		}

		for (Iterator iter = getVisibleColumns().iterator(); iter.hasNext();) {

			AbstractColumn column = (AbstractColumn)iter.next();
			
			if (column instanceof BarCodeColumn) {
				BarCodeColumn barcodeColumn = (BarCodeColumn)column;
				JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
				JRDesignExpression imageExp = new JRDesignExpression();
				imageExp.setText("ar.com.fdvs.dj.core.BarcodeHelper.getBarcodeImage("+barcodeColumn.getBarcodeType() + ", "+ column.getTextForExpression()+ ", "+ barcodeColumn.isShowText() + ", " + barcodeColumn.isCheckSum() + ", " + barcodeColumn.getApplicationIdentifier() + ","+ column.getWidth() +", "+ report.getOptions().getDetailHeight().intValue() + " )" );
				
				imageExp.setValueClass(java.awt.Image.class);
				image.setExpression(imageExp);
				image.setHeight(getReport().getOptions().getDetailHeight().intValue());
				image.setWidth(column.getWidth().intValue());
				image.setX(column.getPosX().intValue());
				image.setScaleImage(barcodeColumn.getScaleMode().getValue());
				
				applyStyleToElement(column.getStyle(), image);
				
				detail.addElement(image);
			} else if (column instanceof ImageColumn) {
				ImageColumn imageColumn = (ImageColumn)column;
				JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
				JRDesignExpression imageExp = new JRDesignExpression();
				imageExp.setText(column.getTextForExpression());
				
				imageExp.setValueClass(InputStream.class);
				image.setExpression(imageExp);
				image.setHeight(getReport().getOptions().getDetailHeight().intValue());
				image.setWidth(column.getWidth().intValue());
				image.setX(column.getPosX().intValue());
				image.setScaleImage(imageColumn.getScaleMode().getValue());
				
				applyStyleToElement(column.getStyle(), image);
				
				detail.addElement(image);
			} else {
				if (column.getConditionalStyles() != null && !column.getConditionalStyles().isEmpty() ){
					for (Iterator iterator = column.getConditionalStyles().iterator(); iterator.hasNext();) {
						ConditionalStyle condition = (ConditionalStyle) iterator.next();
						JRDesignTextField textField = generateTextFieldFromColumn(column, getReport().getOptions().getDetailHeight().intValue(), null);
						transformDetailBandTextField(column, textField);
						applyStyleToElement(condition.getStyle(), textField);
						textField.setPrintWhenExpression(getExpressionForConditionalStyle(condition.getName(), column.getTextForExpression()));
						detail.addElement(textField);
					}
				} else {
					JRDesignTextField textField = generateTextFieldFromColumn(column, getReport().getOptions().getDetailHeight().intValue(), null);
					transformDetailBandTextField(column, textField);
					if (textField.getExpression() != null)
						detail.addElement(textField);
				}
			}

        }
	}
	

	/**
	 * Places a square as DetailBand background for odd rows.
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
		Style oddRowBackgroundStyle = options.getOddRowBackgroundStyle();
		
		addStyleToDesign(oddRowBackgroundStyle);//register this style in the jasperDesign
		
		JRDesignStyle style = oddRowBackgroundStyle.transform();
		style.setForecolor(oddRowBackgroundStyle.getBackgroundColor());
		
		applyStyleToElement(oddRowBackgroundStyle, rectangle);
//		rectangle.setStyle(style);
		rectangle.setBackcolor(oddRowBackgroundStyle.getBackgroundColor());
		rectangle.setForecolor(oddRowBackgroundStyle.getBorderColor());
		rectangle.setPen(oddRowBackgroundStyle.getBorder().getValue());
		rectangle.setMode(JRDesignElement.MODE_OPAQUE);
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

	protected final void generateHeaderBand(JRDesignBand band) {
		log.debug("Generating header band...");
		band.setHeight(report.getOptions().getHeaderHeight().intValue());

		for (Iterator iter = getVisibleColumns().iterator(); iter.hasNext();) {

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

			applyStyleToElement(headerStyle, textField);

			band.addElement(textField);
		}
	}
	
	public void applyStyleToElement(Style style, JRDesignElement designElemen) {
		if (style == null){
			log.warn("NULL style passed to object");
			return;
		}
		boolean existsInDesign = style.getName() != null 
		&& design.getStylesMap().get(style.getName()) != null; 
//		&& !style.isOverridesExistingStyle();
	
		JRStyle jrstyle = null;
		if (existsInDesign && !style.isOverridesExistingStyle()){
			jrstyle = (JRStyle) design.getStylesMap().get(style.getName());
		} else {
			addStyleToDesign(style); //Order maters. This line fist
			jrstyle = style.transform();
		}
		
		designElemen.setStyle(jrstyle);
		if (designElemen instanceof JRDesignTextElement ) {
			JRDesignTextElement textField = (JRDesignTextElement) designElemen;
			if (style.getStreching() != null)
				textField.setStretchType(style.getStreching().getValue());
			textField.setPositionType(JRTextField.POSITION_TYPE_FLOAT);
			
		}
		if (designElemen instanceof JRDesignTextField ) {
			JRDesignTextField textField = (JRDesignTextField) designElemen;
			textField.setStretchWithOverflow(style.isStretchWithOverflow());
			
			if (textField.isBlankWhenNull() == false && style.isBlankWhenNull())
				textField.setBlankWhenNull(true);
		}		
	}


	/**
	 * Sets the columns width by reading some report options like the
	 * printableArea and useFullPageWidth.
	 * columns with fixedWidth property set in TRUE will not be modified
	 */
	private void setColumnsFinalWidth() {
		log.debug("Setting columns final width...");
		float factor = 1;
		int printableArea = report.getOptions().getColumnWidth();
		
		//Create a list with only the visible columns.
		List visibleColums = getVisibleColumns();

		log.debug("printableArea = " + printableArea );

		if (report.getOptions().isUseFullPageWidth()) {
			int columnsWidth = 0;
			int notRezisableWidth = 0;
			
			//Store in a variable the total with of all visible columns
			for (Iterator iterator =  visibleColums.iterator(); iterator.hasNext();) {
				AbstractColumn col = (AbstractColumn) iterator.next();
				columnsWidth += col.getWidth().intValue();
				if (col.getFixedWidth().booleanValue())
					notRezisableWidth += col.getWidth().intValue();
			}

			log.debug("columnsWidth = "+ columnsWidth);
			log.debug("notRezisableWidth = "+ notRezisableWidth);

			factor = (float) (printableArea-notRezisableWidth) / (float) (columnsWidth-notRezisableWidth);
			log.debug("factor = "+ factor);
			int acu = 0;
			int colFinalWidth = 0;

			//Select the non-resizable columns
			Collection resizableColumns = CollectionUtils.select( visibleColums,new Predicate() {
				public boolean evaluate(Object arg0) {
					return !((AbstractColumn)arg0).getFixedWidth().booleanValue();
				}

			}) ;

			//Finally, set the new width to the resizable columns
			for (Iterator iter = resizableColumns.iterator(); iter.hasNext();) {
				AbstractColumn col = (AbstractColumn) iter.next();

				if (!iter.hasNext()) {
					col.setWidth(new Integer(printableArea - notRezisableWidth - acu));
				} else {
					colFinalWidth = (new Float(col.getWidth().intValue() * factor)).intValue();
					acu += colFinalWidth;
					col.setWidth(new Integer(colFinalWidth));
				}
			}
		}

		// If the columns width changed, the X position must be setted again.
		int posx = 0;
		for (Iterator iterator =  visibleColums.iterator(); iterator.hasNext();) {
			AbstractColumn col = (AbstractColumn) iterator.next();
			col.setPosX(new Integer(posx));
			posx += col.getWidth().intValue();
		}
	}

	/**
	 * @return
	 */
	protected List getVisibleColumns() {
		List visibleColums = new ArrayList(report.getColumns());
		return visibleColums;
	}

	/**
	 * Sets the necessary height for all bands in the report, to hold their children
	 */
	protected void setBandsFinalHeight() {
		log.debug("Setting bands final height...");
		
		setBandFinalHeight((JRDesignBand) design.getPageHeader());
		setBandFinalHeight((JRDesignBand) design.getPageFooter());
		setBandFinalHeight((JRDesignBand) design.getColumnHeader());
		setBandFinalHeight((JRDesignBand) design.getColumnFooter());
		setBandFinalHeight((JRDesignBand) design.getSummary());
		setBandFinalHeight((JRDesignBand) design.getBackground());
		setBandFinalHeight((JRDesignBand) design.getDetail());
		setBandFinalHeight((JRDesignBand) design.getLastPageFooter());
		setBandFinalHeight((JRDesignBand) design.getTitle());
		setBandFinalHeight((JRDesignBand) design.getPageFooter());
		
		for (Iterator iter = design.getGroupsList().iterator(); iter.hasNext();) {
			JRGroup jrgroup = (JRGroup) iter.next();
			setBandFinalHeight((JRDesignBand) jrgroup.getGroupHeader());
			setBandFinalHeight((JRDesignBand) jrgroup.getGroupFooter());
		}
	}

	/**
	 * Sets the band's height to hold all its children
	 * @param band Band to be resized
	 */
	protected void setBandFinalHeight(JRDesignBand band) {
		if (band != null) {
			int finalHeight = findVerticalOffset(band);
			band.setHeight(finalHeight);
		}
	}

	/**
	 * Finds "Y" corrdinate value in with more elements could be added in the band
	 * @param band
	 * @return
	 */
	protected int findVerticalOffset(JRDesignBand band) {
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

		applyStyleToElement(columnStyle, textField);

        if (group != null) {
        	int index = getReport().getColumnsGroups().indexOf(group);
            JRDesignGroup previousGroup = (JRDesignGroup) getDesign().getGroupsList().get(index);
            textField.setPrintWhenGroupChanges(previousGroup);
        }
        return textField;
	}
	
	/*
	 * Takes all the report's charts and inserts them in their corresponding bands
	 */
	protected void layoutCharts() {
		for (Iterator iter = getReport().getCharts().iterator(); iter.hasNext();) {
			DJChart djChart = (DJChart) iter.next();
			JRDesignChart chart = createChart(djChart);
			JRDesignBand band = getPositionBand(djChart);
			band.addElement(chart);
		}
	}
	
	/**
	 * Calculates and returns the band where the band is to be inserted
	 * @param djChart
	 * @return
	 */
	private JRDesignBand getPositionBand(DJChart djChart) {
		JRDesignGroup jgroup = getGroupFromColumnsGroup(djChart.getColumnsGroup());
		JRDesignGroup parentGroup = getParent(jgroup);
		
		JRDesignBand band = null;
		switch (djChart.getOptions().getPosition()) {
		case DJChartOptions.POSITION_HEADER:
			band = (JRDesignBand) ((parentGroup.equals(jgroup)) ? getDesign().getSummary(): parentGroup.getGroupHeader());
			break;
		case DJChartOptions.POSITION_FOOTER:
			band = (JRDesignBand) ((parentGroup.equals(jgroup)) ? getDesign().getSummary(): parentGroup.getGroupFooter());
		}
		return band;
	}

	private JRDesignChart createChart(DJChart djChart){

			JRDesignGroup jrGroup = getGroupFromColumnsGroup(djChart.getColumnsGroup());
			
			JRDesignChart chart = new JRDesignChart(new JRDesignStyle().getDefaultStyleProvider(), djChart.getType());
			chart.setDataset(DataSetFactory.getDataset(djChart.getType(), jrGroup, getParent(jrGroup), registerChartVariable(djChart)));
			interpeterOptions(djChart, chart);
			
			chart.setEvaluationTime(JRExpression.EVALUATION_TIME_GROUP);
			chart.setEvaluationGroup(jrGroup);
			return chart;
	}
	
	private void interpeterOptions(DJChart djChart, JRDesignChart chart) {
		DJChartOptions options = djChart.getOptions();
	
		//size
		if (options.isCentered()) chart.setWidth(getReport().getOptions().getPrintableWidth());
		else chart.setWidth(options.getWidth());
		chart.setHeight(options.getHeight());
		
		//position
		chart.setX(options.getX());
		chart.setPadding(10);
		chart.setY(options.getY());
		arrangeBand(djChart, chart);
		
		//options
		chart.setShowLegend(options.isShowLegend());
		chart.setBackcolor(options.getBackColor());
		chart.setBorder(options.getBorder());
		
		//colors
		if (options.getColors() != null){
			int i = 1;
			for (Iterator iter = options.getColors().iterator(); iter.hasNext();i++) {
				Color color = (Color) iter.next();
				chart.getPlot().getSeriesColors().add(new JRBaseChartPlot.JRBaseSeriesColor(i, color));
			}
		}
		//Chart-dependant options
		if (djChart.getType() == DJChart.BAR_CHART) ((JRDesignBarPlot) chart.getPlot()).setShowTickLabels(options.isShowLabels());
	}

	//TODO: 5's must be replaced by a constant or a configurable number
	private void arrangeBand(DJChart djChart, JRDesignChart chart) {
		int index = getReport().getColumnsGroups().indexOf(djChart.getColumnsGroup());
		
		if (djChart.getOptions().getPosition() == DJChartOptions.POSITION_HEADER){
			JRDesignBand band = (JRDesignBand) getParent(((JRDesignGroup)getDesign().getGroupsList().get(index))).getGroupHeader();
			
			for (int i = 0; i < band.getElements().length; i++) {
				JRDesignElement element = (JRDesignElement) band.getElements()[i];
				element.setY(element.getY() + chart.getY() + chart.getHeight() + 5);
			}
		}
		else {
			JRDesignBand band = (JRDesignBand) getParent(((JRDesignGroup)getDesign().getGroupsList().get(index))).getGroupFooter();
			int max = 0;
			for (int i = 0; i < band.getElements().length; i++) {
				JRDesignElement element = (JRDesignElement) band.getElements()[i];
				if ( (element.getHeight() + element.getY()) > max)
					max = element.getHeight() + element.getY();
			}
			chart.setY(max +5 );
		}	
	}

	/**
	 * Creates and registers a variable to be used by the Chart
	 * @param chart Chart that needs a variable to be generated
	 * @return the generated variable
	 */
	private JRDesignVariable registerChartVariable(DJChart chart) {
		
		JRDesignGroup group = getGroupFromColumnsGroup(chart.getColumnsGroup());

		Class clazz = null;
		try { clazz = Class.forName(chart.getColumn().getValueClassNameForExpression());
		} catch (ClassNotFoundException e) { /*Should never happen*/ }
		
		JRDesignExpression expression = new JRDesignExpression();
//		expression.setText("$F{" + chart.getColumn().getTitle().toLowerCase() + "}");
		expression.setText("$F{" + ((PropertyColumn) chart.getColumn()).getColumnProperty().getProperty()  + "}");
		expression.setValueClass(clazz);
		
		JRDesignVariable var = new JRDesignVariable();
		var.setValueClass(clazz);
		var.setExpression(expression);
		var.setCalculation(chart.getOperation());
		var.setResetGroup(group);
		var.setResetType(JRBaseVariable.RESET_TYPE_GROUP);
		var.setName("CHART_" + group.getName() + "_" + chart.getColumn().getTitle() + "_" + chart.getOperation());
//		JRDesignExpression initExp = new JRDesignExpression();
//		initExp.setText("new Float(0)");
//		initExp.setValueClass(clazz);
//		var.setInitialValueExpression(initExp);
		
		try {
			getDesign().addVariable(var);
		} catch (JRException e) {
			throw new LayoutException(e.getMessage());
		}
		return var;
	}
	
	
	
	
	
	//TODO: Maybe a helper could calculate the following
	/**
	 * Finds the parent group of the given one and returns it
	 * @param group Group for which the parent is needed
	 * @return The parent group of the given one. If the given one is the first one, it returns the same group
	 */
	private JRDesignGroup getParent(JRDesignGroup group){
		int index = getDesign().getGroupsList().indexOf(group);
		JRDesignGroup parentGroup = (index > 0) ? (JRDesignGroup) getDesign().getGroupsList().get(index-1): group;
		return parentGroup;
	}
	
	protected JRDesignGroup getGroupFromColumnsGroup(ColumnsGroup group){
		int index = getReport().getColumnsGroups().indexOf(group);
		return (JRDesignGroup) getDesign().getGroupsList().get(index);
	}
	
	//TODO: Maybe a helper could calculate this (up to here)
	
	
	
	
	
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

}
