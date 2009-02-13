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

package ar.com.fdvs.dj.core.layout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.base.JRBaseVariable;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignPropertyExpression;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.DJDefaultScriptlet;
import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJChartOptions;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DataSetFactory;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.BarCodeColumn;
import ar.com.fdvs.dj.domain.entities.columns.ImageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionStyleExpression;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import ar.com.fdvs.dj.util.ExpressionUtils;
import ar.com.fdvs.dj.util.LayoutUtils;

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

	/**
	 * Holds the original groups binded to a column.
	 * Needed for later reference
	 */
	protected List realGroups = new ArrayList();

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
			setWhenNoDataBand();
			endLayout();
			registerRemainingStyles();
		} catch (RuntimeException e) {
			throw new LayoutException(e.getMessage(),e);
		}
	}


	/**
	 * Creates the graphic element to be shown when the datasource is empty
	 */
	protected void setWhenNoDataBand() {
		log.debug("setting up WHEN NO DATA band");
		String whenNoDataText = getReport().getWhenNoDataText();
		Style style = getReport().getWhenNoDataStyle();
		if (whenNoDataText == null || "".equals(whenNoDataText))
			return;
		JRDesignBand band = new JRDesignBand();
		getDesign().setNoData(band);

		JRDesignTextField text = new JRDesignTextField();
		JRDesignExpression expression = ExpressionUtils.createStringExpression("\""+whenNoDataText+"\"");
		text.setExpression(expression);

		if (style == null){
			style = getReport().getOptions().getDefaultDetailStyle();
		}

		if (getReport().isWhenNoDataShowTitle())
			LayoutUtils.copyBandElements(band, getDesign().getPageHeader());
		if (getReport().isWhenNoDataShowColumnHeader())
			LayoutUtils.copyBandElements(band, getDesign().getColumnHeader());

		int offset = LayoutUtils.findVerticalOffset(band);
		text.setY(offset);
		applyStyleToElement(style, text);
		text.setWidth(getReport().getOptions().getPrintableWidth());
		text.setHeight(50);
		band.addElement(text);
		log.debug("OK setting up WHEN NO DATA band");

	}

	protected void startLayout() {
		setColumnsFinalWidth();
		realGroups.addAll(getDesign().getGroupsList()); //Hold the original groups
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
			for (Iterator iter = report.getColumns().iterator(); iter.hasNext();) {
				AbstractColumn column = (AbstractColumn) iter.next();
				if (column.getStyle() == null)
					column.setStyle(defaultDetailStyle);
				if (column.getHeaderStyle() == null)
					column.setHeaderStyle(defaultHeaderStyle);
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
				String name = createUniqueStyleName();
				jrstyle.setName(name);
				style.setName(name);
				getReportStyles().put(name, jrstyle);
				design.addStyle(jrstyle);
			}

			JRStyle old = (JRStyle) design.getStylesMap().get(jrstyle.getName());
			if (old != null && style.isOverridesExistingStyle()){
				log.debug("Overriding style with name \""+ style.getName() +"\"");

				design.removeStyle(style.getName());
				design.addStyle(jrstyle);
			} else if (old == null){
				log.debug("Registering new style with name \""+ style.getName() +"\"");
				design.addStyle(jrstyle);
			} else {
				if (style.getName() != null)
					log.debug("Using existing style for style with name \""+ style.getName() +"\"");
			}
		} catch (JRException e) {
			log.debug("Duplicated style (it's ok): " + e.getMessage());
		}
	}

	protected String createUniqueStyleName() {
		synchronized (this) {
			int counter = getReportStyles().values().size() + 1;
			String tryName = "dj_style_" + counter;
			while (design.getStylesMap().get(tryName) != null){
				counter++;
				tryName = "dj_style_" + counter;
			}
			return tryName;
		}
	}

	/**
	 * For each column, puts the elements in the detail band
	 */
	protected void transformDetailBand() {
		log.debug("transforming Detail Band...");
		JRDesignBand detail = (JRDesignBand) design.getDetail();
		detail.setHeight(report.getOptions().getDetailHeight().intValue());

		for (Iterator iter = getVisibleColumns().iterator(); iter.hasNext();) {

			AbstractColumn column = (AbstractColumn)iter.next();

			/**
			 * Barcode column
			 */
			if (column instanceof BarCodeColumn) {
				BarCodeColumn barcodeColumn = (BarCodeColumn)column;
				JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
				JRDesignExpression imageExp = new JRDesignExpression();
//				imageExp.setText("ar.com.fdvs.dj.core.BarcodeHelper.getBarcodeImage("+barcodeColumn.getBarcodeType() + ", "+ column.getTextForExpression()+ ", "+ barcodeColumn.isShowText() + ", " + barcodeColumn.isCheckSum() + ", " + barcodeColumn.getApplicationIdentifier() + ","+ column.getWidth() +", "+ report.getOptions().getDetailHeight().intValue() + " )" );

				//Do not pass column height and width mecause barbecue
				//generates the image with wierd dimensions. Pass 0 in both cases
				String applicationIdentifier = barcodeColumn.getApplicationIdentifier();
				if (applicationIdentifier != null && !"".equals(applicationIdentifier.trim()) ){
					applicationIdentifier = "$F{" + applicationIdentifier + "}";
				} else {
					applicationIdentifier = "\"\"";
				}
				imageExp.setText("ar.com.fdvs.dj.core.BarcodeHelper.getBarcodeImage("+barcodeColumn.getBarcodeType() + ", "+ column.getTextForExpression()+ ", "+ barcodeColumn.isShowText() + ", " + barcodeColumn.isCheckSum() + ", " + applicationIdentifier + ",0,0 )" );


				imageExp.setValueClass(java.awt.Image.class);
				image.setExpression(imageExp);
				image.setHeight(getReport().getOptions().getDetailHeight().intValue());
				image.setWidth(column.getWidth().intValue());
				image.setX(column.getPosX().intValue());
				image.setScaleImage(barcodeColumn.getScaleMode().getValue());

				image.setOnErrorType(JRDesignImage.ON_ERROR_TYPE_ICON); //FIXME should we provide control of this to the user?

				applyStyleToElement(column.getStyle(), image);

				detail.addElement(image);
			}
			/**
			 * Image columns
			 */
			else if (column instanceof ImageColumn) {
				ImageColumn imageColumn = (ImageColumn)column;
				JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
				JRDesignExpression imageExp = new JRDesignExpression();
				imageExp.setText(column.getTextForExpression());

				imageExp.setValueClassName(imageColumn.getColumnProperty().getValueClassName());
				image.setExpression(imageExp);
				image.setHeight(getReport().getOptions().getDetailHeight().intValue());
				image.setWidth(column.getWidth().intValue());
				image.setX(column.getPosX().intValue());
				image.setScaleImage(imageColumn.getScaleMode().getValue());

				applyStyleToElement(column.getStyle(), image);

				detail.addElement(image);
			}
			/**
			 * Regular Column
			 */
			else {
				if (column.getConditionalStyles() != null && !column.getConditionalStyles().isEmpty() ){
					for (Iterator iterator = column.getConditionalStyles().iterator(); iterator.hasNext();) {
						ConditionalStyle condition = (ConditionalStyle) iterator.next();
						JRDesignTextField textField = generateTextFieldFromColumn(column, getReport().getOptions().getDetailHeight().intValue(), null);
						transformDetailBandTextField(column, textField);
						applyStyleToElement(condition.getStyle(), textField);
						textField.setPrintWhenExpression(getExpressionForConditionalStyle(condition, column));
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
	 * Creates and returns the expression used to apply a conditional style.
	 * @param String paramName
	 * @param String textForExpression
	 * @return JRExpression
	 */
	private JRExpression getExpressionForConditionalStyle(ConditionalStyle condition, AbstractColumn column) {
		//String text = "(("+CustomExpression.class.getName()+")$P{"+paramName+"})."+CustomExpression.EVAL_METHOD_NAME+"("+textForExpression+")";
		String columExpression = column.getTextForExpression();
		//condition.getCondition().setFieldToEvaluate(exprParams)

		// PeS17 patch, 2008-11-29: put all fields to fields map, including "invisible" i.e. only registered ones
		
		String fieldsMap = DJDefaultScriptlet.class.getName() + ".getCurrentFiels()";
		String parametersMap = DJDefaultScriptlet.class.getName() + ".getCurrentParams()";
		String variablesMap = DJDefaultScriptlet.class.getName() + ".getCurrentVariables()";		

		String evalMethodParams =  fieldsMap +", " + variablesMap + ", " + parametersMap + ", " + columExpression;

		String text = "(("+ConditionStyleExpression.class.getName()+")$P{"+condition.getName()+"})."+CustomExpression.EVAL_METHOD_NAME+"("+evalMethodParams+")";
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(Boolean.class);
		expression.setText(text);
		return expression;
	}

	protected void generateHeaderBand(JRDesignBand band) {
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

	/**
	 * Given a dj-Style, it is applied to the jasper element.
	 * If the style is being used by the first time, it is registered in the jasper-design,
	 * if it is the second time, the one created before is used  (cached one)
	 *
	 *
	 * @param style
	 * @param designElemen
	 */
	public void applyStyleToElement(Style style, JRDesignElement designElemen) {
		if (style == null){
//			log.warn("NULL style passed to object");
			JRDesignStyle style_ = new JRDesignStyle();
			style_.setName( createUniqueStyleName());
			designElemen.setStyle(style_);
			try {
				getDesign().addStyle(style_);
			} catch (JRException e) {
				//duplicated style, its ok
			}
//			return null;
			return;
		}
		boolean existsInDesign = style.getName() != null
								&& design.getStylesMap().get(style.getName()) != null;
						//		&& !style.isOverridesExistingStyle();

		JRDesignStyle jrstyle = null;
		if (existsInDesign && !style.isOverridesExistingStyle()){
			jrstyle = (JRDesignStyle) design.getStylesMap().get(style.getName());
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

			if (textField.isBlankWhenNull() == false && style.isBlankWhenNull()) //TODO Re check if this condition is ok 
				textField.setBlankWhenNull(true);
		}
//		return jrstyle;
		return;
	}


	/**
	 * Sets the columns width by reading some report options like the
	 * printableArea and useFullPageWidth.
	 * columns with fixedWidth property set in TRUE will not be modified
	 */
	protected void setColumnsFinalWidth() {
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
		setBandFinalHeight((JRDesignBand) design.getNoData());

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
			int finalHeight = LayoutUtils.findVerticalOffset(band);
			band.setHeight(finalHeight);
		}
	}

	/**
	 * Creates a JasperReport DesignTextField from a DynamicJasper AbstractColumn.
	 * @param AbstractColumn col
	 * @param int height
	 * @param DJGroup group
	 * @return JRDesignTextField
	 */
	protected JRDesignTextField generateTextFieldFromColumn(AbstractColumn col, int height, DJGroup group) {
		JRDesignTextField textField = new JRDesignTextField();
		JRDesignExpression exp = new JRDesignExpression();

		if (col.getPattern() != null && "".equals(col.getPattern().trim())) {
			textField.setPattern(col.getPattern());
        }
		
		if (col.getTruncateSuffix() != null){
			textField.getPropertiesMap().setProperty(JRTextElement.PROPERTY_TRUNCATE_SUFFIX, col.getTruncateSuffix());
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
        JRDesignStyle jrstyle = (JRDesignStyle) textField.getStyle();

        if (group != null) {
        	int index = getReport().getColumnsGroups().indexOf(group);
//            JRDesignGroup previousGroup = (JRDesignGroup) getDesign().getGroupsList().get(index);
            JRDesignGroup previousGroup = getJRGroupFromDJGroup(group);
            textField.setPrintWhenGroupChanges(previousGroup);

            /**
             * Since a group column can share the style with non group columns, if oddRow coloring is enabled,
             * we modified this shared style to have a colored background on odd rows. We dont want that for group
             * columns, that's why we create our own style from the existing one, and remove proper odd-row conditional
             * style if present
             */
            JRDesignStyle groupStyle = new JRDesignStyle();
            try {
            	if (jrstyle != null)
            		BeanUtils.copyProperties(groupStyle, jrstyle);
			} catch (Exception e) {
				throw new DJException("Could not copy properties for shared group style: " + e.getMessage(),e);
			}

			groupStyle.setName(groupStyle.getFontName() +"_for_group_"+index);
			textField.setStyle(groupStyle);
			try {
				design.addStyle(groupStyle);
			} catch (JRException e) { /**e.printStackTrace(); //Already there, nothing to do **/}

        } else {
        	if (getReport().getOptions().isPrintBackgroundOnOddRows() &&
        			(jrstyle.getConditionalStyles() == null || jrstyle.getConditionalStyles().length == 0)) {
	        	// No group column so this is a detail text field
	    		JRDesignExpression expression = new JRDesignExpression();
	    		expression.setValueClass(Boolean.class);
	    		expression.setText(EXPRESSION_TRUE_WHEN_ODD);

	    		Style oddRowBackgroundStyle = getReport().getOptions().getOddRowBackgroundStyle();

	    		JRDesignConditionalStyle condStyle = new JRDesignConditionalStyle();
	    		condStyle.setBackcolor(oddRowBackgroundStyle.getBackgroundColor());
	    		condStyle.setMode(JRDesignElement.MODE_OPAQUE);

	    		condStyle.setConditionExpression(expression);
	    		jrstyle.addConditionalStyle(condStyle);
        	}
        }
        return textField;
	}

	/*
	 * Takes all the report's charts and inserts them in their corresponding bands
	 */
	protected void layoutCharts() {
		//Pre-sort charts by group column
		MultiMap mmap = new MultiHashMap();
		for (Iterator iter = getReport().getCharts().iterator(); iter.hasNext();) {
			DJChart djChart = (DJChart) iter.next();
			mmap.put(djChart.getColumnsGroup(), djChart);
		}

		for (Iterator iterator = mmap.keySet().iterator(); iterator.hasNext();) {
			Object key =  iterator.next();
			Collection charts = (Collection) mmap.get(key);
			ArrayList l = new ArrayList(charts);
			//Reverse iteration of the charts to meet insertion order
			for (int i = l.size(); i > 0; i--) {
				DJChart djChart = (DJChart) l.get(i-1);
				JRDesignChart chart = createChart(djChart);

				//Charts has their own band, so they are added in the band at Y=0
				JRDesignBand band = createGroupForChartAndGetBand(djChart);
				band.addElement(chart);
			}
		}
	}

	protected JRDesignBand createGroupForChartAndGetBand(DJChart djChart) {
		JRDesignGroup jrGroup = getJRGroupFromDJGroup(djChart.getColumnsGroup());
		JRDesignGroup parentGroup = getParent(jrGroup);
		JRDesignGroup jrGroupChart = null;
		try {
			jrGroupChart = (JRDesignGroup) BeanUtils.cloneBean(parentGroup);
			jrGroupChart.setGroupFooter( new JRDesignBand());
			jrGroupChart.setGroupHeader( new JRDesignBand());
			jrGroupChart.setName(jrGroupChart.getName()+"_Chart" + getReport().getCharts().indexOf(djChart));
		} catch (Exception e) {
			throw new DJException("Problem creating band for chart: " + e.getMessage(),e);
		}

		//Charts should be added in its own band (to ensure page break, etc)
		//To achieve that, we create a group and insert it right before to the criteria group.
		//I need to find parent group of the criteria group, clone and insert after.
		//The only precaution is that if parent == child (only one group in the report) the we insert before
		if (jrGroup.equals(parentGroup)){
			jrGroupChart.setExpression(ExpressionUtils.createStringExpression("\"dummy_for_chart\""));
			getDesign().getGroupsList().add( getDesign().getGroupsList().indexOf(jrGroup) , jrGroupChart);
		} else {
			int index = getDesign().getGroupsList().indexOf(parentGroup);
			getDesign().getGroupsList().add(index, jrGroupChart);
		}

		JRDesignBand band = null;
		switch (djChart.getOptions().getPosition()) {
		case DJChartOptions.POSITION_HEADER:
			band = (JRDesignBand) jrGroupChart.getGroupHeader();
			break;
		case DJChartOptions.POSITION_FOOTER:
			band = (JRDesignBand) jrGroupChart.getGroupFooter();
		}
		return band;
	}

	/**
	 * Creates the JRDesignChart from the DJChart. To do so it also creates needed variables and data-set
	 * @param djChart
	 * @return
	 */
	protected JRDesignChart createChart(DJChart djChart){
			JRDesignGroup jrGroupChart = getJRGroupFromDJGroup(djChart.getColumnsGroup());

			JRDesignChart chart = new JRDesignChart(new JRDesignStyle().getDefaultStyleProvider(), djChart.getType());
			JRDesignGroup parentGroup = getParent(jrGroupChart);
			List chartVariables = registerChartVariable(djChart);
			JRDesignChartDataset chartDataset = DataSetFactory.getDataset(djChart, jrGroupChart, parentGroup, chartVariables);
			chart.setDataset(chartDataset);
			interpeterOptions(djChart, chart);

			chart.setEvaluationTime(JRExpression.EVALUATION_TIME_GROUP);
			chart.setEvaluationGroup(jrGroupChart);
			return chart;
	}

	protected void interpeterOptions(DJChart djChart, JRDesignChart chart) {
		DJChartOptions options = djChart.getOptions();

		//size
		if (options.isCentered())
			chart.setWidth(getReport().getOptions().getPrintableWidth());
		else
			chart.setWidth(options.getWidth());

		chart.setHeight(options.getHeight());

		//position
		chart.setX(options.getX());
		chart.setPadding(10);
		chart.setY(options.getY());

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
		//Chart-dependent options
		if (djChart.getType() == DJChart.BAR_CHART)
			((JRDesignBarPlot) chart.getPlot()).setShowTickLabels(options.isShowLabels());
	}


	/**
	 * Creates and registers a variable to be used by the Chart
	 * @param chart Chart that needs a variable to be generated
	 * @return the generated variables
	 */
	protected List registerChartVariable(DJChart chart) {
		//FIXME aca hay que iterar por cada columna. Cambiar DJChart para que tome muchas
		JRDesignGroup group = getJRGroupFromDJGroup(chart.getColumnsGroup());
		List vars = new ArrayList();

		int serieNum = 0;
		for (Iterator iterator = chart.getColumns().iterator(); iterator.hasNext();) {
			AbstractColumn col = (AbstractColumn) iterator.next();


			Class clazz = null;
			try { clazz = Class.forName(col.getValueClassNameForExpression());
			} catch (ClassNotFoundException e) {
				throw new DJException("Exeption creating chart variable: " + e.getMessage(),e);
			}

			JRDesignExpression expression = new JRDesignExpression();
			//FIXME Only PropertyColumn allowed?
			expression.setText("$F{" + ((PropertyColumn) col).getColumnProperty().getProperty()  + "}");
			expression.setValueClass(clazz);

			JRDesignVariable var = new JRDesignVariable();
			var.setValueClass(clazz);
			var.setExpression(expression);
			var.setCalculation(chart.getOperation());
			var.setResetGroup(group);
			var.setResetType(JRBaseVariable.RESET_TYPE_GROUP);

			//use the index as part of the name just because I may want 2
			//different types of chart from the very same column (with the same operation also) making the variables name to be duplicated
			int chartIndex = getReport().getCharts().indexOf(chart);
			var.setName("CHART_[" + chartIndex +"_s" +serieNum + "+]_" + group.getName() + "_" + col.getTitle() + "_" + chart.getOperation());

			try {
				getDesign().addVariable(var);
				vars.add(var);
			} catch (JRException e) {
				throw new LayoutException(e.getMessage());
			}
			serieNum++;
		}
		return vars;
	}


	/**
	 * Finds the parent group of the given one and returns it
	 * @param group Group for which the parent is needed
	 * @return The parent group of the given one. If the given one is the first one, it returns the same group
	 */
	protected JRDesignGroup getParent(JRDesignGroup group){
		int index = realGroups.indexOf(group);
		JRDesignGroup parentGroup = (index > 0) ? (JRDesignGroup) realGroups.get(index-1): group;
		return parentGroup;
	}

	/***
	 * Finds JRDesignGroup associated to a DJGroup
	 * @param group
	 * @return
	 */
	protected JRDesignGroup getJRGroupFromDJGroup(DJGroup group){
		int index = getReport().getColumnsGroups().indexOf(group);
		return (JRDesignGroup) realGroups.get(index);
	}

	
	protected DJGroup getDJGroup(AbstractColumn col) {
		Iterator it = getReport().getColumnsGroups().iterator();
		while (it.hasNext()) {
			DJGroup group = (DJGroup) it.next();
			if (group.getColumnToGroupBy().equals(col))
				return group;
		}
		return null;
	}		
	
	
	/**
	 * Returns true if at least one group is configured to show the column name in its header
	 * @param groups
	 * @return
	 */
	protected boolean existsGroupWithColumnNames() {
		Iterator it = getReport().getColumnsGroups().iterator();
		while (it.hasNext()) {
			DJGroup group = (DJGroup) it.next();
			if (group.getLayout().isShowColumnName())
				return true;
		}
		return false;
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

}
