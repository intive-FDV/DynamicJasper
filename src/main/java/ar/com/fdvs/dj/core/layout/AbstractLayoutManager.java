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

import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJChartOptions;
import ar.com.fdvs.dj.domain.builders.DataSetFactory;
import ar.com.fdvs.dj.domain.chart.*;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.DJColSpan;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.*;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import ar.com.fdvs.dj.util.*;
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.base.JRBaseVariable;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.*;
import net.sf.jasperreports.engine.util.JRExpressionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Abstract Class used as base for the different Layout Managers.</br>
 * </br>
 * A Layout Manager is always invoked after the entities registration stage.</br>
 * A subclass should be created whenever we want to give the users the chance to </br>
 * easily apply global layout changes to their reports. Example: Ignore groups </br>
 * and styles for an Excel optimized report.
 */
public abstract class AbstractLayoutManager implements LayoutManager {

    private static final Log log = LogFactory.getLog(AbstractLayoutManager.class);

    protected static final String EXPRESSION_TRUE_WHEN_ODD = "new java.lang.Boolean(((Number)$V{REPORT_COUNT}).doubleValue() % 2 == 0)";

    protected static final String EXPRESSION_TRUE_WHEN_EVEN = "new java.lang.Boolean(((Number)$V{REPORT_COUNT}).doubleValue() % 2 != 0)";

    JasperDesign design;

    private DynamicReport report;

    protected abstract void transformDetailBandTextField(AbstractColumn column, JRDesignTextField textField);

    private HashMap reportStyles = new HashMap();

    /**
     * Holds the original groups binded to a column.
     * Needed for later reference
     * List<JRDesignGroup>
     */
    protected final List realGroups = new ArrayList();

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
            applyWaterMark();
            transformDetailBand();
            endLayout();
            setWhenNoDataBand();
            setBandsFinalHeight();
            registerRemainingStyles();
        } catch (RuntimeException e) {
            throw new LayoutException(e.getMessage(), e);
        }
    }

    protected void applyWaterMark() {
        DynamicReport dr = getReport();
        JasperDesign jd = getDesign();

        DJWaterMark djWaterMark = dr.getWaterMark();

        if (djWaterMark == null || Utils.isEmpty(djWaterMark.getText()))
            return;

        JRDesignBand backgroundBand = (JRDesignBand) jd.getBackground();

        if (backgroundBand == null) {
            backgroundBand = new JRDesignBand();
            jd.setBackground(backgroundBand);
        }
        int printableHeight = jd.getPageHeight() - jd.getTopMargin() - jd.getBottomMargin();
        int printableWidth = jd.getPageWidth() - jd.getLeftMargin() - jd.getRightMargin();
        backgroundBand.setHeight(printableHeight);

        JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
        JRDesignExpression imageExp = null;

        int multiplier = 2;

        ar.com.fdvs.dj.domain.constants.Font font2 = (ar.com.fdvs.dj.domain.constants.Font) djWaterMark.getFont().clone();
        font2.setFontSize(font2.getFontSize() * multiplier);

        BufferedImage watermark = WaterMarkRenderer.rotateText(djWaterMark.getText(),
                font2.toAwtFont(),
                printableWidth * multiplier,
                printableHeight * multiplier,
                djWaterMark.getAngle(), djWaterMark.getTextColor());
        try {
            File outputFile = File.createTempFile("dynamicJasper", "watermark.png");
            outputFile.deleteOnExit();
            ImageIO.write(watermark, "png", outputFile);
            String absolutePath = outputFile.getAbsolutePath();
            log.debug("Watermark Image: " + absolutePath);
            String escapeTextForExpression = Utils.escapeTextForExpression(absolutePath);
            imageExp = ExpressionUtils.createStringExpression("\"" + escapeTextForExpression + "\"");
        } catch (IOException e) {
            log.error("Could not create watermark image: " + e.getMessage(),e);
        }

        image.setExpression(imageExp);
        image.setHeight(printableHeight);
        image.setWidth(printableWidth);
        image.setScaleImage(ScaleImageEnum.RETAIN_SHAPE);
        image.setOnErrorType(OnErrorTypeEnum.BLANK);
        backgroundBand.addElement(image);
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
        JRDesignExpression expression = ExpressionUtils.createStringExpression("\"" + whenNoDataText + "\"");
        text.setExpression(expression);

        if (style == null) {
            style = getReport().getOptions().getDefaultDetailStyle();
        }

        if (getReport().isWhenNoDataShowTitle()) {
            LayoutUtils.copyBandElements(band, getDesign().getTitle());
            LayoutUtils.copyBandElements(band, getDesign().getPageHeader());
        }
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
     *
     * @throws JRException
     */
    protected void ensureDJStyles() {
        //first of all, register all parent styles if any
        for (Object o : getReport().getStyles().values()) {
            Style style = (Style) o;
            addStyleToDesign(style);
        }

        Style defaultDetailStyle = getReport().getOptions().getDefaultDetailStyle();

        Style defaultHeaderStyle = getReport().getOptions().getDefaultHeaderStyle();
        for (AbstractColumn column : report.getColumns()) {
            if (column.getStyle() == null)
                column.setStyle(defaultDetailStyle);
            if (column.getHeaderStyle() == null)
                column.setHeaderStyle(defaultHeaderStyle);
        }
    }

    /**
     * @param style
     * @throws JRException
     */
    public void addStyleToDesign(Style style) {
        JRDesignStyle jrstyle = style.transform();
        try {
            if (jrstyle.getName() == null) {
                String name = createUniqueStyleName();
                jrstyle.setName(name);
                style.setName(name);
                getReportStyles().put(name, jrstyle);
                design.addStyle(jrstyle);
            }

            JRStyle old = design.getStylesMap().get(jrstyle.getName());
            if (old != null && style.isOverridesExistingStyle()) {
                log.debug("Overriding style with name \"" + style.getName() + "\"");

                design.removeStyle(style.getName());
                design.addStyle(jrstyle);
            } else if (old == null) {
                log.debug("Registering new style with name \"" + style.getName() + "\"");
                design.addStyle(jrstyle);
            } else {
                if (style.getName() != null)
                    log.debug("Using existing style for style with name \"" + style.getName() + "\"");
            }
        } catch (JRException e) {
            log.debug("Duplicated style (it's ok): " + e.getMessage());
        }
    }

    protected String createUniqueStyleName() {
        synchronized (this) {
            int counter = getReportStyles().values().size() + 1;
            String tryName = "dj_style_" + counter + "_"; //FIX for issue 3002761 @SF tracker
            while (design.getStylesMap().get(tryName) != null) {
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

        JRDesignSection detailSection = (JRDesignSection) design.getDetailSection();

        //TODO: With this new way, we can use template content as it comes, and add a new band for DJ on top or bellow it.
        JRDesignBand detail;
        if (detailSection.getBandsList().isEmpty()) {
            detail = new JRDesignBand();
            detailSection.getBandsList().add(detail);
        } else {
            detail = (JRDesignBand) detailSection.getBandsList().iterator().next();
        }

        detail.setHeight(report.getOptions().getDetailHeight());

        for (AbstractColumn column : getVisibleColumns()) {

            /**
             * Barcode column
             */
            if (column instanceof BarCodeColumn) {
                BarCodeColumn barcodeColumn = (BarCodeColumn) column;
                JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
                JRDesignExpression imageExp = new JRDesignExpression();
//				imageExp.setText("ar.com.fdvs.dj.core.BarcodeHelper.getBarcodeImage("+barcodeColumn.getBarcodeType() + ", "+ column.getTextForExpression()+ ", "+ barcodeColumn.isShowText() + ", " + barcodeColumn.isCheckSum() + ", " + barcodeColumn.getApplicationIdentifier() + ","+ column.getWidth() +", "+ report.getOptions().getDetailHeight().intValue() + " )" );

                //Do not pass column height and width mecause barbecue
                //generates the image with wierd dimensions. Pass 0 in both cases
                String applicationIdentifier = barcodeColumn.getApplicationIdentifier();
                if (applicationIdentifier != null && !"".equals(applicationIdentifier.trim())) {
                    applicationIdentifier = "$F{" + applicationIdentifier + "}";
                } else {
                    applicationIdentifier = "\"\"";
                }
                imageExp.setText("ar.com.fdvs.dj.core.BarcodeHelper.getBarcodeImage(" + barcodeColumn.getBarcodeType() + ", " + column.getTextForExpression() + ", " + barcodeColumn.isShowText() + ", " + barcodeColumn.isCheckSum() + ", " + applicationIdentifier + ",0,0 )");


                imageExp.setValueClass(Image.class);
                image.setExpression(imageExp);
                image.setHeight(getReport().getOptions().getDetailHeight());
                image.setWidth(column.getWidth());
                image.setX(column.getPosX());
                image.setScaleImage(ScaleImageEnum.getByValue(barcodeColumn.getScaleMode().getValue()));

                image.setOnErrorType(OnErrorTypeEnum.ICON); //FIXME should we provide control of this to the user?

                if (column.getLink() != null) {
                    String name = "column_" + getReport().getColumns().indexOf(column);
                    HyperLinkUtil.applyHyperLinkToElement((DynamicJasperDesign) getDesign(), column.getLink(), image, name);
                }

                applyStyleToElement(column.getStyle(), image);

                detail.addElement(image);
            }
            /**
             * Image columns
             */
            else if (column instanceof ImageColumn) {
                ImageColumn imageColumn = (ImageColumn) column;
                JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
                JRDesignExpression imageExp = new JRDesignExpression();
                imageExp.setText(column.getTextForExpression());

                imageExp.setValueClassName(imageColumn.getValueClassNameForExpression());
                image.setExpression(imageExp);
                image.setHeight(getReport().getOptions().getDetailHeight());
                image.setWidth(column.getWidth());
                image.setX(column.getPosX());
                image.setScaleImage(ScaleImageEnum.getByValue(imageColumn.getScaleMode().getValue()));

                applyStyleToElement(column.getStyle(), image);

                if (column.getLink() != null) {
                    String name = "column_" + getReport().getColumns().indexOf(column);
                    HyperLinkUtil.applyHyperLinkToElement((DynamicJasperDesign) getDesign(), column.getLink(), image, name);
                }

                detail.addElement(image);
            }
            /**
             * Regular Column
             */
            else {
                if (getReport().getOptions().isShowDetailBand()) {
                    JRDesignTextField textField = generateTextFieldFromColumn(column, getReport().getOptions().getDetailHeight(), null);

                    if (column.getLink() != null) {
                        String name = getDesign().getName() + "_column_" + getReport().getColumns().indexOf(column);
                        HyperLinkUtil.applyHyperLinkToElement((DynamicJasperDesign) getDesign(), column.getLink(), textField, name);
                    }

                    transformDetailBandTextField(column, textField);

                    if (textField.getExpression() != null)
                        detail.addElement(textField);
                }

            }

        }
    }


//	/**
//	 * Creates and returns the expression used to apply a conditional style.
//	 * @param String paramName
//	 * @param String textForExpression
//	 * @return JRExpression
//	 */
    /*
	 * MOVED INSIDE ExpressionUtils
	protected JRDesignExpression getExpressionForConditionalStyle(ConditionalStyle condition, AbstractColumn column) {
		//String text = "(("+CustomExpression.class.getName()+")$P{"+paramName+"})."+CustomExpression.EVAL_METHOD_NAME+"("+textForExpression+")";
		String columExpression = column.getTextForExpression();
		//condition.getCondition().setFieldToEvaluate(exprParams)

		// PeS17 patch, 2008-11-29: put all fields to fields map, including "invisible" i.e. only registered ones

		String fieldsMap = "(("+DJDefaultScriptlet.class.getName() + ")$P{REPORT_SCRIPTLET}).getCurrentFields()";
		String parametersMap = "(("+DJDefaultScriptlet.class.getName() + ")$P{REPORT_SCRIPTLET}).getCurrentParams()";
		String variablesMap = "(("+DJDefaultScriptlet.class.getName() + ")$P{REPORT_SCRIPTLET}).getCurrentVariables()";

		String evalMethodParams =  fieldsMap +", " + variablesMap + ", " + parametersMap + ", " + columExpression;

		String text = "(("+ConditionStyleExpression.class.getName()+")$P{"+condition.getName()+"})."+CustomExpression.EVAL_METHOD_NAME+"("+evalMethodParams+")";
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(Boolean.class);
		expression.setText(text);
		return expression;
	}
	 */

    protected void generateHeaderBand(JRDesignBand band) {
        log.debug("Adding column names in header band.");
        band.setHeight(report.getOptions().getHeaderHeight());

        for (AbstractColumn col : getVisibleColumns()) {

            if (col.getTitle() == null)
                continue;

            Style headerStyle = col.getHeaderStyle();
            if (headerStyle == null)
                headerStyle = report.getOptions().getDefaultHeaderStyle();

            this.generateColspanHeader(col, band);

            JRDesignExpression expression = new JRDesignExpression();
            JRDesignTextField textField = new JRDesignTextField();
            expression.setText("\"" + col.getTitle() + "\"");

            //sets header markup (if any)
            if (col.getHeaderMarkup() != null)
                textField.setMarkup(col.getHeaderMarkup().toLowerCase());

            expression.setValueClass(String.class);

            textField.setKey("header_" + col.getTitle());
            textField.setExpression(expression);

            if (col.hasParentCol()) {
                textField.setY(col.getPosY() + band.getHeight() / 2);
                textField.setHeight(band.getHeight() / 2);

            } else {
                textField.setY(col.getPosY());
                textField.setHeight(band.getHeight());
            }

            textField.setX(col.getPosX());
            textField.setWidth(col.getWidth());

            textField.setPrintWhenDetailOverflows(true);
            textField.setBlankWhenNull(true);

            applyStyleToElement(headerStyle, textField);
            band.addElement(textField);
        }
    }

    private void generateColspanHeader(AbstractColumn col, JRDesignBand band) {

        DJColSpan colSpan = col.getColSpan();
        if (colSpan != null && colSpan.isFirstColum(col)) {
            //Set colspan
            JRDesignTextField spanTitle = new JRDesignTextField();
            JRDesignExpression colspanExpression = new JRDesignExpression();
            colspanExpression.setValueClassName(String.class.getName());
            colspanExpression.setText("\"" + col.getColSpan().getTitle() + "\"");

            spanTitle.setExpression(colspanExpression);
            spanTitle.setKey("colspan-header" + col.getTitle());

            spanTitle.setX(col.getPosX());
            spanTitle.setY(col.getPosY());
            spanTitle.setHeight(band.getHeight() / 2);
            spanTitle.setWidth(colSpan.getWidth());

            Style spanStyle = colSpan.getColspanHeaderStyle();

            if (spanStyle == null) {
                spanStyle = report.getOptions().getDefaultHeaderStyle();
            }

            applyStyleToElement(spanStyle, spanTitle);
            band.addElement(spanTitle);
        }
    }

    /**
     * Given a dj-Style, it is applied to the jasper element.
     * If the style is being used by the first time, it is registered in the jasper-design,
     * if it is the second time, the one created before is used  (cached one)
     *
     * @param style
     * @param designElemen
     */
    public void applyStyleToElement(Style style, JRDesignElement designElemen) {
        if (style == null) {
//			log.warn("NULL style passed to object");
            JRDesignStyle style_ = new JRDesignStyle();
            style_.setName(createUniqueStyleName());
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

        JRDesignStyle jrstyle;
        //Let's allways add a new JR style
        if (existsInDesign && !style.isOverridesExistingStyle()) {
            jrstyle = (JRDesignStyle) design.getStylesMap().get(style.getName());
        } else {
            addStyleToDesign(style); //Order maters. This line fist
            jrstyle = style.transform();
        }

        designElemen.setStyle(jrstyle);
        if (designElemen instanceof JRDesignTextElement) {
            JRDesignTextElement textField = (JRDesignTextElement) designElemen;
            if (style.getStreching() != null)
                textField.setStretchType(StretchTypeEnum.getByValue(style.getStreching().getValue()));
            textField.setPositionType(PositionTypeEnum.FLOAT);

        }
        if (designElemen instanceof JRDesignTextField) {
            JRDesignTextField textField = (JRDesignTextField) designElemen;
            textField.setStretchWithOverflow(style.isStretchWithOverflow());

            if (!textField.isBlankWhenNull() && style.isBlankWhenNull()) //TODO Re check if this condition is ok
                textField.setBlankWhenNull(true);
        }

        if (designElemen instanceof JRDesignGraphicElement) {
            JRDesignGraphicElement graphicElement = (JRDesignGraphicElement) designElemen;
            graphicElement.setStretchType(StretchTypeEnum.getByValue(style.getStreching().getValue()));
            graphicElement.setPositionType(PositionTypeEnum.FLOAT);
        }
    }


    /**
     * Sets the columns width by reading some report options like the
     * printableArea and useFullPageWidth.
     * columns with fixedWidth property set in TRUE will not be modified
     */
    protected void setColumnsFinalWidth() {
        log.debug("Setting columns final width.");
        float factor;
        int printableArea = report.getOptions().getColumnWidth();

        //Create a list with only the visible columns.
        List visibleColums = getVisibleColumns();


        if (report.getOptions().isUseFullPageWidth()) {
            int columnsWidth = 0;
            int notRezisableWidth = 0;

            //Store in a variable the total with of all visible columns
            for (Object visibleColum : visibleColums) {
                AbstractColumn col = (AbstractColumn) visibleColum;
                columnsWidth += col.getWidth();
                if (col.getFixedWidth())
                    notRezisableWidth += col.getWidth();
            }


            factor = (float) (printableArea - notRezisableWidth) / (float) (columnsWidth - notRezisableWidth);

            log.debug("printableArea = " + printableArea
                    + ", columnsWidth = " + columnsWidth
                    + ", columnsWidth = " + columnsWidth
                    + ", notRezisableWidth = " + notRezisableWidth
                    + ", factor = " + factor);

            int acumulated = 0;
            int colFinalWidth;

            //Select the non-resizable columns
            Collection resizableColumns = CollectionUtils.select(visibleColums, new Predicate() {
                public boolean evaluate(Object arg0) {
                    return !((AbstractColumn) arg0).getFixedWidth();
                }

            });

            //Finally, set the new width to the resizable columns
            for (Iterator iter = resizableColumns.iterator(); iter.hasNext(); ) {
                AbstractColumn col = (AbstractColumn) iter.next();

                if (!iter.hasNext()) {
                    col.setWidth(printableArea - notRezisableWidth - acumulated);
                } else {
                    colFinalWidth = (new Float(col.getWidth() * factor)).intValue();
                    acumulated += colFinalWidth;
                    col.setWidth(colFinalWidth);
                }
            }
        }

        // If the columns width changed, the X position must be setted again.
        int posx = 0;
        for (Object visibleColum : visibleColums) {
            AbstractColumn col = (AbstractColumn) visibleColum;
            col.setPosX(posx);
            posx += col.getWidth();
        }
    }

    /**
     * @return A list of visible columns
     */
    protected List<AbstractColumn> getVisibleColumns() {
        return new ArrayList<AbstractColumn>(report.getColumns());
    }

    /**
     * Sets the necessary height for all bands in the report, to hold their children
     */
    protected void setBandsFinalHeight() {
        log.debug("Setting bands final height...");

        List<JRBand> bands = new ArrayList<JRBand>();

        Utils.addNotNull(bands, design.getPageHeader());
        Utils.addNotNull(bands, design.getPageFooter());
        Utils.addNotNull(bands, design.getColumnHeader());
        Utils.addNotNull(bands, design.getColumnFooter());
        Utils.addNotNull(bands, design.getSummary());
        Utils.addNotNull(bands, design.getBackground());
        bands.addAll(((JRDesignSection) design.getDetailSection()).getBandsList());
        Utils.addNotNull(bands, design.getLastPageFooter());
        Utils.addNotNull(bands, design.getTitle());
        Utils.addNotNull(bands, design.getPageFooter());
        Utils.addNotNull(bands, design.getNoData());

        for (JRGroup jrgroup : design.getGroupsList()) {
            DJGroup djGroup = (DJGroup) getReferencesMap().get(jrgroup.getName());
            JRDesignSection headerSection = (JRDesignSection) jrgroup.getGroupHeaderSection();
            JRDesignSection footerSection = (JRDesignSection) jrgroup.getGroupFooterSection();
            if (djGroup != null) {
                for (JRBand headerBand : headerSection.getBandsList()) {
                    setBandFinalHeight((JRDesignBand) headerBand, djGroup.getHeaderHeight(), djGroup.isFitHeaderHeightToContent());

                }
                for (JRBand footerBand : footerSection.getBandsList()) {
                    setBandFinalHeight((JRDesignBand) footerBand, djGroup.getFooterHeight(), djGroup.isFitFooterHeightToContent());

                }
            } else {
                bands.addAll(headerSection.getBandsList());
                bands.addAll(footerSection.getBandsList());
            }
        }

        for (JRBand jrDesignBand : bands) {
            setBandFinalHeight((JRDesignBand) jrDesignBand);
        }
    }

    /**
     * Removes empty space when "fitToContent" is true and real height of object is
     * taller than current bands height, otherwise, it is not modified
     *
     * @param band
     * @param currHeigth
     * @param fitToContent
     */
    private void setBandFinalHeight(JRDesignBand band, int currHeigth, boolean fitToContent) {
        if (band != null) {
            int finalHeight = LayoutUtils.findVerticalOffset(band);
            //noinspection StatementWithEmptyBody
            if (finalHeight < currHeigth && !fitToContent) {
                //nothing
            } else {
                band.setHeight(finalHeight);
            }
        }

    }

    /**
     * Sets the band's height to hold all its children
     *
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
     *
     * @param col
     * @param height
     * @param group
     * @return JRDesignTextField
     */
    protected JRDesignTextField generateTextFieldFromColumn(AbstractColumn col, int height, DJGroup group) {
        JRDesignTextField textField = new JRDesignTextField();
        JRDesignExpression exp = new JRDesignExpression();

        if (col.getPattern() != null && "".equals(col.getPattern().trim())) {
            textField.setPattern(col.getPattern());
        }

        if (col.getTruncateSuffix() != null) {
            textField.getPropertiesMap().setProperty(JRTextElement.PROPERTY_TRUNCATE_SUFFIX, col.getTruncateSuffix());
        }

        List columnsGroups = getReport().getColumnsGroups();
        if (col instanceof PercentageColumn) {
            PercentageColumn pcol = (PercentageColumn) col;

            if (group == null) { //we are in the detail band
                DJGroup innerMostGroup = (DJGroup) columnsGroups.get(columnsGroups.size() - 1);
                exp.setText(pcol.getTextForExpression(innerMostGroup));
            } else {
                exp.setText(pcol.getTextForExpression(group));
            }

            textField.setEvaluationTime(EvaluationTimeEnum.AUTO);
        } else {
            exp.setText(col.getTextForExpression());

        }

        exp.setValueClassName(col.getValueClassNameForExpression());
        textField.setExpression(exp);
        textField.setWidth(col.getWidth());
        textField.setX(col.getPosX());
        textField.setY(col.getPosY());
        textField.setHeight(height);

        textField.setBlankWhenNull(col.getBlankWhenNull());

        textField.setPattern(col.getPattern());

        if (col.getMarkup() != null)
            textField.setMarkup(col.getMarkup().toLowerCase());

        textField.setPrintRepeatedValues(col.getPrintRepeatedValues());

        textField.setPrintWhenDetailOverflows(true);

        Style columnStyle = col.getStyle();
        if (columnStyle == null)
            columnStyle = report.getOptions().getDefaultDetailStyle();

        applyStyleToElement(columnStyle, textField);
        JRDesignStyle jrstyle = (JRDesignStyle) textField.getStyle();

        if (group != null) {
            int index = columnsGroups.indexOf(group);
//            JRDesignGroup previousGroup = (JRDesignGroup) getDesign().getGroupsList().get(index);
            JRDesignGroup previousGroup = getJRGroupFromDJGroup(group);
            textField.setPrintWhenGroupChanges(previousGroup);

            /**
             * Since a group column can share the style with non group columns, if oddRow coloring is enabled,
             * we modified this shared style to have a colored background on odd rows. We don't want that for group
             * columns, that's why we create our own style from the existing one, and remove proper odd-row conditional
             * style if present
             */
            JRDesignStyle groupStyle = Utils.cloneStyle(jrstyle);

            groupStyle.setName(groupStyle.getFontName() + "_for_group_" + index + "_");
            textField.setStyle(groupStyle);
            try {
                design.addStyle(groupStyle);
            } catch (JRException e) { /**e.printStackTrace(); //Already there, nothing to do **/}

        } else {

            JRDesignStyle alternateStyle = Utils.cloneStyle(jrstyle);

            alternateStyle.setName(alternateStyle.getFontName() + "_for_column_" + col.getName() + "_");
            alternateStyle.getConditionalStyleList().clear();
            textField.setStyle(alternateStyle);
            try {
                design.addStyle(alternateStyle);
            } catch (JRException e) { /**e.printStackTrace(); //Already there, nothing to do **/}


            setUpConditionStyles(alternateStyle, col);
        	/*
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
        	}*/
        }
        return textField;
    }

    /**
     * set up properly the final JRStyle of the column element (for detail band) upon condition style and odd-background
     *
     * @param jrstyle
     * @param column
     */
    private void setUpConditionStyles(JRDesignStyle jrstyle, AbstractColumn column) {

        if (getReport().getOptions().isPrintBackgroundOnOddRows() && Utils.isEmpty(column.getConditionalStyles())) {
            JRDesignExpression expression = new JRDesignExpression();
            expression.setValueClass(Boolean.class);
            expression.setText(EXPRESSION_TRUE_WHEN_ODD);

            Style oddRowBackgroundStyle = getReport().getOptions().getOddRowBackgroundStyle();

            JRDesignConditionalStyle condStyle = new JRDesignConditionalStyle();
            condStyle.setBackcolor(oddRowBackgroundStyle.getBackgroundColor());
            condStyle.setMode(ModeEnum.OPAQUE);

            condStyle.setConditionExpression(expression);
            jrstyle.addConditionalStyle(condStyle);

            return;
        }

        if (Utils.isEmpty(column.getConditionalStyles()))
            return;

        for (Object o : column.getConditionalStyles()) {
            ConditionalStyle condition = (ConditionalStyle) o;

            if (getReport().getOptions().isPrintBackgroundOnOddRows()
                    && Transparency.TRANSPARENT == condition.getStyle().getTransparency()) { //condition style + odd row (only if conditional style's background is transparent)

                JRDesignExpression expressionForConditionalStyle = ExpressionUtils.getExpressionForConditionalStyle(condition, column.getTextForExpression());
                String expStr = JRExpressionUtil.getExpressionText(expressionForConditionalStyle);

                //ODD
                JRDesignExpression expressionOdd = new JRDesignExpression();
                expressionOdd.setValueClass(Boolean.class);
                expressionOdd.setText("new java.lang.Boolean(" + EXPRESSION_TRUE_WHEN_ODD + ".booleanValue() && ((java.lang.Boolean)" + expStr + ").booleanValue() )");

                Style oddRowBackgroundStyle = getReport().getOptions().getOddRowBackgroundStyle();

                JRDesignConditionalStyle condStyleOdd = makeConditionalStyle(condition.getStyle());
//				Utils.copyProperties(condStyleOdd, condition.getStyle().transform());
                condStyleOdd.setBackcolor(oddRowBackgroundStyle.getBackgroundColor());
                condStyleOdd.setMode(ModeEnum.OPAQUE);
                condStyleOdd.setConditionExpression(expressionOdd);
                jrstyle.addConditionalStyle(condStyleOdd);

                //EVEN
                JRDesignExpression expressionEven = new JRDesignExpression();
                expressionEven.setValueClass(Boolean.class);
                expressionEven.setText("new java.lang.Boolean(" + EXPRESSION_TRUE_WHEN_EVEN + ".booleanValue() && ((java.lang.Boolean)" + expStr + ").booleanValue() )");

                JRDesignConditionalStyle condStyleEven = makeConditionalStyle(condition.getStyle());
                condStyleEven.setConditionExpression(expressionEven);
                jrstyle.addConditionalStyle(condStyleEven);

            } else { //No odd row, just the conditional style
                JRDesignExpression expression = ExpressionUtils.getExpressionForConditionalStyle(condition, column.getTextForExpression());
                JRDesignConditionalStyle condStyle = makeConditionalStyle(condition.getStyle());
                condStyle.setConditionExpression(expression);
                jrstyle.addConditionalStyle(condStyle);
            }
        }

        //The last condition is the basic one
        //ODD
        if (getReport().getOptions().isPrintBackgroundOnOddRows()) {

            JRDesignExpression expressionOdd = new JRDesignExpression();
            expressionOdd.setValueClass(Boolean.class);
            expressionOdd.setText(EXPRESSION_TRUE_WHEN_ODD);

            Style oddRowBackgroundStyle = getReport().getOptions().getOddRowBackgroundStyle();

            JRDesignConditionalStyle condStyleOdd = new JRDesignConditionalStyle();
            condStyleOdd.setBackcolor(oddRowBackgroundStyle.getBackgroundColor());
            condStyleOdd.setMode(ModeEnum.OPAQUE);
            condStyleOdd.setConditionExpression(expressionOdd);

            jrstyle.addConditionalStyle(condStyleOdd);

            //EVEN
            JRDesignExpression expressionEven = new JRDesignExpression();
            expressionEven.setValueClass(Boolean.class);
            expressionEven.setText(EXPRESSION_TRUE_WHEN_EVEN);

            JRDesignConditionalStyle condStyleEven = new JRDesignConditionalStyle();
            condStyleEven.setBackcolor(jrstyle.getBackcolor());
            condStyleEven.setMode(jrstyle.getModeValue());
            condStyleEven.setConditionExpression(expressionEven);

            jrstyle.addConditionalStyle(condStyleEven);
        }
    }


    protected JRDesignConditionalStyle makeConditionalStyle(Style style) {
        return style.transformAsConditinalStyle();
    }

    /*
     * Takes all the report's charts and inserts them in their corresponding bands
     */
    protected void layoutCharts() {
        //Pre-sort charts by group column
        MultiMap mmap = new MultiHashMap();
        for (Object o1 : getReport().getCharts()) {
            DJChart djChart = (DJChart) o1;
            mmap.put(djChart.getColumnsGroup(), djChart);
        }

        for (Object key : mmap.keySet()) {
            Collection charts = (Collection) mmap.get(key);
            ArrayList l = new ArrayList(charts);
            //Reverse iteration of the charts to meet insertion order
            for (int i = l.size(); i > 0; i--) {
                DJChart djChart = (DJChart) l.get(i - 1);
                JRDesignChart chart = createChart(djChart);

                //Charts has their own band, so they are added in the band at Y=0
                JRDesignBand band = createGroupForChartAndGetBand(djChart);
                band.addElement(chart);
            }
        }

        //Pre-sort charts by group column
        mmap = new MultiHashMap();
        for (Object o : getReport().getNewCharts()) {
            ar.com.fdvs.dj.domain.chart.DJChart djChart = (ar.com.fdvs.dj.domain.chart.DJChart) o;
            mmap.put(djChart.getDataset().getColumnsGroup(), djChart);
        }

        for (Object key : mmap.keySet()) {
            Collection charts = (Collection) mmap.get(key);
            ArrayList l = new ArrayList(charts);
            //Reverse iteration of the charts to meet insertion order
            for (int i = l.size(); i > 0; i--) {
                ar.com.fdvs.dj.domain.chart.DJChart djChart = (ar.com.fdvs.dj.domain.chart.DJChart) l.get(i - 1);
                String name = "chart_" + (i - 1) + new Date().getTime();
                JRDesignChart chart = createChart(djChart, name);

                if (djChart.getLink() != null)
                    HyperLinkUtil.applyHyperLinkToElement((DynamicJasperDesign) getDesign(), djChart.getLink(), chart, name + "_hyperlink");

                //Charts has their own band, so they are added in the band at Y=0
                JRDesignBand band = createGroupForChartAndGetBand(djChart);
                band.addElement(chart);
            }
        }
    }

    protected JRDesignBand createGroupForChartAndGetBand(DJChart djChart) {
        JRDesignGroup jrGroup = getJRGroupFromDJGroup(djChart.getColumnsGroup());
        JRDesignGroup parentGroup = getParent(jrGroup);
        JRDesignGroup jrGroupChart;
        try {
//			jrGroupChart = (JRDesignGroup) BeanUtils.cloneBean(parentGroup);
            jrGroupChart = new JRDesignGroup(); //FIXME nuevo 3.5.2
            jrGroupChart.setExpression(parentGroup.getExpression());
            ((JRDesignSection) jrGroupChart.getGroupFooterSection()).addBand(new JRDesignBand());
            ((JRDesignSection) jrGroupChart.getGroupHeaderSection()).addBand(new JRDesignBand());
            jrGroupChart.setName(jrGroupChart.getName() + "_Chart" + getReport().getCharts().indexOf(djChart));
        } catch (Exception e) {
            throw new DJException("Problem creating band for chart: " + e.getMessage(), e);
        }

        //Charts should be added in its own band (to ensure page break, etc)
        //To achieve that, we create a group and insert it right before to the criteria group.
        //I need to find parent group of the criteria group, clone and insert after.
        //The only precaution is that if parent == child (only one group in the report) the we insert before
        if (jrGroup.equals(parentGroup)) {
            jrGroupChart.setExpression(ExpressionUtils.createStringExpression("\"dummy_for_chart\""));
            getDesign().getGroupsList().add(getDesign().getGroupsList().indexOf(jrGroup), jrGroupChart);
        } else {
            int index = getDesign().getGroupsList().indexOf(parentGroup);
            getDesign().getGroupsList().add(index, jrGroupChart);
        }

        JRDesignBand band = null;
        switch (djChart.getOptions().getPosition()) {
            case DJChartOptions.POSITION_HEADER:
                band = (JRDesignBand) ((JRDesignSection) jrGroupChart.getGroupHeaderSection()).getBandsList().get(0);
                break;
            case DJChartOptions.POSITION_FOOTER:
                band = (JRDesignBand) ((JRDesignSection) jrGroupChart.getGroupFooterSection()).getBandsList().get(0);
        }
        return band;
    }

    /**
     * Creates the JRDesignChart from the DJChart. To do so it also creates needed variables and data-set
     *
     * @param djChart
     * @return
     */
    protected JRDesignChart createChart(DJChart djChart) {
        JRDesignGroup jrGroupChart = getJRGroupFromDJGroup(djChart.getColumnsGroup());

        JRDesignChart chart = new JRDesignChart(new JRDesignStyle().getDefaultStyleProvider(), djChart.getType());
        JRDesignGroup parentGroup = getParent(jrGroupChart);
        List chartVariables = registerChartVariable(djChart);
        JRDesignChartDataset chartDataset = DataSetFactory.getDataset(djChart, jrGroupChart, parentGroup, chartVariables);
        chart.setDataset(chartDataset);
        interpeterOptions(djChart, chart);

        chart.setEvaluationTime(EvaluationTimeEnum.GROUP);
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
        //FIXME no more padding
        //chart.setPadding(10);
        chart.setY(options.getY());

        //options
        chart.setShowLegend(options.isShowLegend());
        chart.setBackcolor(options.getBackColor());

        //FIXME no more border, maybe setLineBox(...) or so
        //chart.setBorder(options.getBorder());

        //colors
        if (options.getColors() != null) {
            int i = 1;
            for (Iterator iter = options.getColors().iterator(); iter.hasNext(); i++) {
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
     *
     * @param chart Chart that needs a variable to be generated
     * @return the generated variables
     */
    protected List registerChartVariable(DJChart chart) {
        //FIXME aca hay que iterar por cada columna. Cambiar DJChart para que tome muchas
        JRDesignGroup group = getJRGroupFromDJGroup(chart.getColumnsGroup());
        List vars = new ArrayList();

        int serieNum = 0;
        for (Object o : chart.getColumns()) {
            AbstractColumn col = (AbstractColumn) o;

            Class clazz;

            JRDesignExpression expression = new JRDesignExpression();
            if (col instanceof ExpressionColumn) {
                try {
                    clazz = Class.forName(((ExpressionColumn) col).getExpression().getClassName());
                } catch (ClassNotFoundException e) {
                    throw new DJException("Exeption creating chart variable: " + e.getMessage(), e);
                }

                ExpressionColumn expCol = (ExpressionColumn) col;
                expression.setText(expCol.getTextForExpression());
                expression.setValueClassName(expCol.getExpression().getClassName());
            } else {
                try {
                    clazz = Class.forName(((PropertyColumn) col).getColumnProperty().getValueClassName());
                } catch (ClassNotFoundException e) {
                    throw new DJException("Exeption creating chart variable: " + e.getMessage(), e);
                }

                expression.setText("$F{" + ((PropertyColumn) col).getColumnProperty().getProperty() + "}");
                expression.setValueClass(clazz);
            }
//			expression.setText("$F{" + ((PropertyColumn) col).getColumnProperty().getProperty()  + "}");
//			expression.setValueClass(clazz);

            JRDesignVariable var = new JRDesignVariable();
            var.setValueClass(clazz);
            var.setExpression(expression);
            var.setCalculation(CalculationEnum.getByValue(chart.getOperation()));
            var.setResetGroup(group);
            var.setResetType(ResetTypeEnum.GROUP);

            //use the index as part of the name just because I may want 2
            //different types of chart from the very same column (with the same operation also) making the variables name to be duplicated
            int chartIndex = getReport().getCharts().indexOf(chart);
            var.setName("CHART_[" + chartIndex + "_s" + serieNum + "+]_" + group.getName() + "_" + col.getTitle() + "_" + chart.getOperation());

            try {
                getDesign().addVariable(var);
                vars.add(var);
            } catch (JRException e) {
                throw new LayoutException(e.getMessage(), e);
            }
            serieNum++;
        }
        return vars;
    }

    protected JRDesignGroup getChartColumnsGroup(ar.com.fdvs.dj.domain.chart.DJChart djChart) {
        PropertyColumn columnsGroup = djChart.getDataset().getColumnsGroup();
        for (Object o : getReport().getColumnsGroups()) {
            DJGroup djGroup = (DJGroup) o;
            if (djGroup.getColumnToGroupBy() == columnsGroup)
                return getJRGroupFromDJGroup(djGroup);
        }
        return null;
    }

    protected JRDesignBand createGroupForChartAndGetBand(ar.com.fdvs.dj.domain.chart.DJChart djChart) {
        JRDesignGroup jrGroup = getChartColumnsGroup(djChart);
        JRDesignGroup parentGroup = getParent(jrGroup);
        JRDesignGroup jrGroupChart;
        try {
            jrGroupChart = new JRDesignGroup(); //FIXME nuevo 3.5.2
            jrGroupChart.setExpression(parentGroup.getExpression());
            ((JRDesignSection) jrGroupChart.getGroupFooterSection()).addBand(new JRDesignBand());
            ((JRDesignSection) jrGroupChart.getGroupHeaderSection()).addBand(new JRDesignBand());
            jrGroupChart.setName(jrGroupChart.getName() + "_Chart" + getReport().getCharts().indexOf(djChart));
        } catch (Exception e) {
            throw new DJException("Problem creating band for chart: " + e.getMessage(), e);
        }

        //Charts should be added in its own band (to ensure page break, etc)
        //To achieve that, we create a group and insert it right before to the criteria group.
        //I need to find parent group of the criteria group, clone and insert after.
        //The only precaution is that if parent == child (only one group in the report) the we insert before
        if (jrGroup.equals(parentGroup)) {
            jrGroupChart.setExpression(ExpressionUtils.createStringExpression("\"dummy_for_chart\""));
            getDesign().getGroupsList().add(getDesign().getGroupsList().indexOf(jrGroup), jrGroupChart);
        } else {
            int index = getDesign().getGroupsList().indexOf(parentGroup);
            getDesign().getGroupsList().add(index, jrGroupChart);
        }

        JRDesignBand band = null;
        switch (djChart.getOptions().getPosition()) {
            case DJChartOptions.POSITION_HEADER:
                band = (JRDesignBand) ((JRDesignSection) jrGroupChart.getGroupHeaderSection()).getBandsList().get(0);
                break;
            case DJChartOptions.POSITION_FOOTER:
                band = (JRDesignBand) ((JRDesignSection) jrGroupChart.getGroupFooterSection()).getBandsList().get(0);
        }
        return band;
    }

    /**
     * Creates the JRDesignChart from the DJChart. To do so it also creates needed variables and data-set
     *
     * @param djChart
     * @return
     */
    protected JRDesignChart createChart(ar.com.fdvs.dj.domain.chart.DJChart djChart, String name) {
        JRDesignGroup jrGroupChart = getChartColumnsGroup(djChart);
        JRDesignGroup parentGroup = getParent(jrGroupChart);
        Map chartVariables = registerChartVariable(djChart);
        return djChart.transform((DynamicJasperDesign) getDesign(), name, jrGroupChart, parentGroup, chartVariables, getReport().getOptions().getPrintableWidth());
    }

    /**
     * Creates and registers a variable to be used by the Chart
     *
     * @param chart Chart that needs a variable to be generated
     * @return the generated variables
     */
    protected Map registerChartVariable(ar.com.fdvs.dj.domain.chart.DJChart chart) {
        //FIXME aca hay que iterar por cada columna. Cambiar DJChart para que tome muchas
        JRDesignGroup group = getChartColumnsGroup(chart);
        Map vars = new HashMap();

        int serieNum = 0;
        for (Object o : chart.getDataset().getColumns()) {
            AbstractColumn col = (AbstractColumn) o;


            Class clazz;
//			try { clazz = Class.forName(col.getValueClassNameForExpression());
//			} catch (ClassNotFoundException e) {
//				throw new DJException("Exeption creating chart variable: " + e.getMessage(),e);
//			}

            JRDesignExpression expression = new JRDesignExpression();
            //FIXME Only PropertyColumn allowed?
            if (col instanceof ExpressionColumn) {
                try {
                    clazz = Class.forName(((ExpressionColumn) col).getExpression().getClassName());
                } catch (ClassNotFoundException e) {
                    throw new DJException("Exeption creating chart variable: " + e.getMessage(), e);
                }

                ExpressionColumn expCol = (ExpressionColumn) col;
                expression.setText(expCol.getTextForExpression());
                expression.setValueClassName(expCol.getExpression().getClassName());
            } else {
                try {
                    clazz = Class.forName(((PropertyColumn) col).getColumnProperty().getValueClassName());
                } catch (ClassNotFoundException e) {
                    throw new DJException("Exeption creating chart variable: " + e.getMessage(), e);
                }

                expression.setText("$F{" + ((PropertyColumn) col).getColumnProperty().getProperty() + "}");
                expression.setValueClass(clazz);
            }

            JRDesignVariable var = new JRDesignVariable();
            var.setValueClass(clazz);
            var.setExpression(expression);
            var.setCalculation(CalculationEnum.getByValue(chart.getOperation()));
            var.setResetGroup(group);
            var.setResetType(ResetTypeEnum.GROUP);

            //use the index as part of the name just because I may want 2
            //different types of chart from the very same column (with the same operation also) making the variables name to be duplicated
            int chartIndex = getReport().getNewCharts().indexOf(chart);
            var.setName("CHART_[" + chartIndex + "_s" + serieNum + "+]_" + group.getName() + "_" + col.getTitle() + "_" + chart.getOperation());

            try {
                getDesign().addVariable(var);
                vars.put(col, var);
            } catch (JRException e) {
                throw new LayoutException(e.getMessage(), e);
            }
            serieNum++;
        }
        return vars;
    }

    /**
     * Finds the parent group of the given one and returns it
     *
     * @param group Group for which the parent is needed
     * @return The parent group of the given one. If the given one is the first one, it returns the same group
     */
    protected JRDesignGroup getParent(JRDesignGroup group) {
        int index = realGroups.indexOf(group);
        return (index > 0) ? (JRDesignGroup) realGroups.get(index - 1) : group;
    }

    /***
     * Finds JRDesignGroup associated to a DJGroup
     *
     * @param group
     * @return
     */
    protected JRDesignGroup getJRGroupFromDJGroup(DJGroup group) {
        int index = getReport().getColumnsGroups().indexOf(group);
        return (JRDesignGroup) realGroups.get(index);
    }


    protected DJGroup getDJGroup(AbstractColumn col) {
        for (Object o : getReport().getColumnsGroups()) {
            DJGroup group = (DJGroup) o;
            if (group.getColumnToGroupBy().equals(col))
                return group;
        }
        return null;
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
