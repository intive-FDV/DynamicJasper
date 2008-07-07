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

package ar.com.fdvs.dj.core.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignBreak;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JRDesignTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.CoreException;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.FontHelper;
import ar.com.fdvs.dj.core.registration.ColumnsGroupVariablesRegistrationManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.ColumnsGroupVariable;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.GlobalGroupColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.ExpressionUtils;
import ar.com.fdvs.dj.util.LayoutUtils;

/**
 * Main Layout Manager recommended for most cases.</br>
 * </br>
 * It provides DJ full features (styles, groups, conditional styles, </br>
 * expressions, group and total variables, etc)
 */
public class ClassicLayoutManager extends AbstractLayoutManager {

	private static final Log log = LogFactory.getLog(ClassicLayoutManager.class);

	protected static final String EXPRESSION_TRUE_WHEN_NOT_FIRST_PAGE = "new java.lang.Boolean(((Number)$V{PAGE_NUMBER}).doubleValue() != 1)";
	protected static final String EXPRESSION_TRUE_WHEN_FIRST_PAGE = "new java.lang.Boolean(((Number)$V{PAGE_NUMBER}).doubleValue() == 1)";

//	private Random subReportRandom = new Random();

	protected void startLayout() {
		super.startLayout();
		generateTitleBand();
		generateHeaderBand();
		applyHeaderAutotexts();

		if (getReport().getColumnsGroups() != null)
			layoutGroups();
	}

	/**
	 *
	 */
	protected void applyHeaderAutotexts() {
		if (getReport().getAutoTexts() == null)
			return;
		/**
		 * Apply the autotext in footer if any
		 */
		JRDesignBand headerband = (JRDesignBand) getDesign().getPageHeader();
		if (headerband == null ) {
			headerband = new JRDesignBand();
			getDesign().setPageHeader(headerband);
		}

		ArrayList positions = new ArrayList();
		positions.add(HorizontalBandAlignment.LEFT);
		positions.add(HorizontalBandAlignment.CENTER);
		positions.add(HorizontalBandAlignment.RIGHT);

		ArrayList autotexts = new ArrayList(getReport().getAutoTexts());
		Collections.reverse(autotexts);

		int totalYoffset = findTotalOffset(positions,autotexts, AutoText.POSITION_HEADER);
		LayoutUtils.moveBandsElemnts(totalYoffset, headerband);

		for (Iterator iterator = positions.iterator(); iterator.hasNext();) {
			HorizontalBandAlignment currentAlignment = (HorizontalBandAlignment) iterator.next();
			int yOffset = 0;

			for (Iterator iter = getReport().getAutoTexts().iterator(); iter.hasNext();) {
				AutoText text = (AutoText) iter.next();
				if (text.getPosition() == AutoText.POSITION_HEADER && text.getAlignment().equals(currentAlignment)) {
					CommonExpressionsHelper.add(yOffset,getDesign(), getReport(), headerband, text);
					yOffset += text.getHeight().intValue();
				}
			}
		}

		/** END */
	}

	/**
	 * Finds the highest sum of height for each possible alignment (left, center, right)
	 * @param aligments
	 * @param autotexts
	 * @return
	 */
	private int findTotalOffset(ArrayList aligments, ArrayList autotexts, byte position) {
		int total = 0;
		for (Iterator iterator = aligments.iterator(); iterator.hasNext();) {
			HorizontalBandAlignment currentAlignment = (HorizontalBandAlignment) iterator.next();
			int aux = 0;
			for (Iterator iter = getReport().getAutoTexts().iterator(); iter.hasNext();) {
				AutoText text = (AutoText) iter.next();
				if (text.getPosition() == position && currentAlignment.equals(text.getAlignment())) {
					aux += text.getHeight().intValue();
				}
			}
			if (aux > total)
				total = aux;
		}
		return total;
	}

	protected void endLayout() {
		super.endLayout();
		applyBanners();
		applyFooterAutotexts();
		setBandsFinalHeight();
	}

	protected void applyFooterAutotexts() {
		if (getReport().getAutoTexts() == null)
			return;

		JRDesignBand footerband = (JRDesignBand) getDesign().getPageFooter();
		if (footerband == null ) {
			footerband = new JRDesignBand();
			getDesign().setPageFooter(footerband);
		}

		ArrayList positions = new ArrayList();
		positions.add(HorizontalBandAlignment.LEFT);
		positions.add(HorizontalBandAlignment.CENTER);
		positions.add(HorizontalBandAlignment.RIGHT);

		for (Iterator iterator = positions.iterator(); iterator.hasNext();) {
			HorizontalBandAlignment currentAlignment = (HorizontalBandAlignment) iterator.next();
			int yOffset = 0;
			/**
			 * Apply the autotext in footer if any
			 */
			for (Iterator iter = getReport().getAutoTexts().iterator(); iter.hasNext();) {
				AutoText text = (AutoText) iter.next();
				if (text.getPosition() == AutoText.POSITION_FOOTER && text.getAlignment().equals(currentAlignment) ) {
					CommonExpressionsHelper.add(yOffset,getDesign(), getReport(), footerband, text);
					yOffset += text.getHeight().intValue();
				}
			}

		}
	}

	/**
	 * Returns a list with the columns that are visible.
	 * Invisible column are the one whose group is configured with hideColumn = true (in the GroupLayout)
	 * @return
	 */
	protected List getVisibleColumns() {
		List visibleColums = new ArrayList(getReport().getColumns());
		for (Iterator iterator = getReport().getColumnsGroups().iterator(); iterator.hasNext();) {
			ColumnsGroup group = (ColumnsGroup) iterator.next();
			if (group.getLayout().isHideColumn()){
				visibleColums.remove(group.getColumnToGroupBy());
			}
		}
		return visibleColums;
	}

	/**
	 * Create the image elements for the banners tha goes into the
	 * title and header bands depending on the case
	 *
	 */
	protected void applyBanners() {
		/**
		 * First create image banners for the first page only
		 */
		JRDesignBand title = (JRDesignBand) getDesign().getTitle();
		//if there is no title band, but there are banner images for the first page, we create a title band
		if (title == null && !getReport().getOptions().getFirstPageImageBanners().isEmpty()){
			title = new JRDesignBand();
			getDesign().setTitle(title);
		}
		applyImageBannersToBand(title,getReport().getOptions().getFirstPageImageBanners().values(),null);

		/**
		 * Now create image banner for the rest of the pages
		 */
		JRDesignBand pageHeader = (JRDesignBand) getDesign().getPageHeader();
		//if there is no title band, but there are banner images for the first page, we create a title band
		if (pageHeader == null && !getReport().getOptions().getImageBanners().isEmpty()){
			pageHeader = new JRDesignBand();
			getDesign().setPageHeader(pageHeader);
		}
		JRDesignExpression printWhenExpression = null;
		if (!getReport().getOptions().getFirstPageImageBanners().isEmpty()){
			printWhenExpression = new JRDesignExpression();
			printWhenExpression.setValueClass(Boolean.class);
			printWhenExpression.setText(EXPRESSION_TRUE_WHEN_NOT_FIRST_PAGE);
		}
		applyImageBannersToBand(pageHeader,getReport().getOptions().getImageBanners().values(),printWhenExpression);


	}
	/**
	 * Create the image elements for the banners tha goes into the
	 * title band
	 * @param printWhenExpression
	 *
	 */
	protected void applyImageBannersToBand(JRDesignBand band, Collection imageBanners, JRDesignExpression printWhenExpression ) {
		int maxHeight = 0;
		for (Iterator iter = imageBanners.iterator(); iter.hasNext();) {
			ImageBanner imageBanner = (ImageBanner) iter.next();
			if (imageBanner.getHeight() > maxHeight)
				maxHeight = imageBanner.getHeight();
		}

		if (band != null){
			//move everything down
			for (Iterator iter =band.getChildren().iterator(); iter.hasNext();) {
				JRDesignElement element = (JRDesignElement) iter.next();
				element.setY(element.getY() + maxHeight);
			}

			for (Iterator iter = imageBanners.iterator(); iter.hasNext();) {
				ImageBanner imageBanner = (ImageBanner) iter.next();
				String path = "\"" + imageBanner.getImagePath().replaceAll("\\\\", "/") + "\"";
				JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
				JRDesignExpression imageExp = new JRDesignExpression();
				imageExp.setText(path);

				imageExp.setValueClass(String.class);
				image.setExpression(imageExp);
				image.setHeight(imageBanner.getHeight());
				image.setWidth(imageBanner.getWidth());
				image.setPrintWhenExpression(printWhenExpression);
				image.setRemoveLineWhenBlank(true);

				if (imageBanner.getAlign() == ImageBanner.ALIGN_LEFT)
					image.setX(0);
				else if (imageBanner.getAlign() == ImageBanner.ALIGN_RIGHT)
					image.setX(getReport().getOptions().getPage().getWidth() -  getReport().getOptions().getLeftMargin().intValue() - getReport().getOptions().getRightMargin().intValue() - imageBanner.getWidth());
				else if (imageBanner.getAlign() == ImageBanner.ALIGN_CENTER){
					int x = (getReport().getOptions().getPage().getWidth() -
							getReport().getOptions().getRightMargin().intValue() -
							getReport().getOptions().getLeftMargin().intValue() - imageBanner.getWidth()) / 2;
					image.setX(getReport().getOptions().getLeftMargin().intValue() + x);
				}

				image.setY(0);
				band.addElement(image);

			}
			band.setHeight(band.getHeight() + maxHeight);
		}
	}

	/**
	 * Adds title and subtitle to the TitleBand when it applies.
	 * If title is not present then subtitle will be ignored
	 */
	protected void generateTitleBand() {
		log.debug("Generating title band...");
		JRDesignBand band = (JRDesignBand) getDesign().getPageHeader();
		int yOffset = 0;

		//If title is not present then subtitle will be ignored
		if (getReport().getTitle() == null)
			return;

		if (band != null && !getDesign().isTitleNewPage()){
			//Title and subtitle comes afer the page header
			yOffset = band.getHeight();

		} else {
			band = (JRDesignBand) getDesign().getTitle();
			if (band == null){
				band = new JRDesignBand();
				getDesign().setTitle(band);
			}
		}

		JRDesignExpression printWhenExpression = new JRDesignExpression();
		printWhenExpression.setValueClass(Boolean.class);
		printWhenExpression.setText(EXPRESSION_TRUE_WHEN_FIRST_PAGE);

		JRDesignTextField title = new JRDesignTextField();
		JRDesignExpression exp = new JRDesignExpression();
		exp.setText("\"" + getReport().getTitle() + "\"");
		exp.setValueClass(String.class);
		title.setExpression(exp);
		title.setWidth(getReport().getOptions().getPrintableWidth());
		title.setHeight(getReport().getOptions().getTitleHeight().intValue());
		title.setY(yOffset);
		title.setPrintWhenExpression(printWhenExpression);
		title.setRemoveLineWhenBlank(true);
		applyStyleToElement(getReport().getTitleStyle(), title);
		band.addElement(title);

		JRDesignTextField subtitle = new JRDesignTextField();
		if (getReport().getSubtitle() != null) {
			JRDesignExpression exp2 = new JRDesignExpression();
			exp2.setText("\"" + getReport().getSubtitle() + "\"");
			exp2.setValueClass(String.class);
			subtitle.setExpression(exp2);
			subtitle.setWidth(getReport().getOptions().getPrintableWidth());
			subtitle.setHeight(getReport().getOptions().getSubtitleHeight().intValue());
			subtitle.setY(title.getY() + title.getHeight());
			subtitle.setPrintWhenExpression(printWhenExpression);
			subtitle.setRemoveLineWhenBlank(true);
			applyStyleToElement(getReport().getSubtitleStyle(), subtitle);
			band.addElement(subtitle);
		}

	}

	/**
	 * Layout columns in groups by reading the corresponding report options.
	 * @throws LayoutException
	 */
	private void layoutGroups() {
		log.debug("Starting groups layout...");
		int i = 0;
		for (Iterator iter = getReport().getColumnsGroups().iterator(); iter.hasNext();) {
			ColumnsGroup columnsGroup = (ColumnsGroup) iter.next();
			JRDesignGroup jgroup = (JRDesignGroup) getDesign().getGroupsList().get(i++);

			jgroup.setStartNewPage(columnsGroup.getStartInNewPage().booleanValue());
			jgroup.setStartNewColumn(columnsGroup.getStartInNewColumn().booleanValue());

			JRDesignBand header = (JRDesignBand) jgroup.getGroupHeader();
			JRDesignBand footer = (JRDesignBand) jgroup.getGroupFooter();
			header.setHeight(columnsGroup.getHeaderHeight().intValue());
			footer.setHeight(columnsGroup.getFooterHeight().intValue());

			if (columnsGroup.getLayout().isPrintHeaders()) {
				for (Iterator iterator =  getVisibleColumns().iterator(); iterator.hasNext();) {
					AbstractColumn col = (AbstractColumn) iterator.next();

					JRDesignTextField designTextField = createColumnNameTextField(columnsGroup, col);
					designTextField.setPositionType(JRDesignElement.POSITION_TYPE_FLOAT); //XXX changeg to see what happens  (must come from the column position property)
					designTextField.setStretchType(JRDesignElement.STRETCH_TYPE_NO_STRETCH); //XXX changeg to see what happens (must come from the column property)
					header.addElement(designTextField);
				}
			}
			layoutGroupVariables(columnsGroup, jgroup);
			layoutGroupSubreports(columnsGroup, jgroup);
			layoutGroupCrosstabs(columnsGroup, jgroup);
		}
	}

	/**
	 * Looks for crosstabs in the groups, if any, it does the layout
	 *
	 * @param columnsGroup
	 * @param jgroup
	 */
	protected void layoutGroupCrosstabs(ColumnsGroup columnsGroup,	JRDesignGroup jgroup) {
		for (Iterator iterator = columnsGroup.getHeaderCrosstabs().iterator(); iterator.hasNext();) {
			DJCrosstab djcross = (DJCrosstab) iterator.next();

			Dj2JrCrosstabBuilder djcb = new Dj2JrCrosstabBuilder();

			JRDesignCrosstab crosst = djcb.createCrosstab(djcross,this);
			JRDesignBand band = (JRDesignBand) jgroup.getGroupHeader();
			if (djcross.getBottomSpace() != 0){
				JRDesignRectangle rect = createBlankRectableCrosstab(djcross.getBottomSpace(), 0);
				LayoutUtils.moveBandsElemnts(rect.getHeight(), band);
				band.addElement(rect);
			}
			LayoutUtils.moveBandsElemnts(crosst.getHeight(), band);
			band.addElement(crosst);
			if (djcross.getTopSpace() != 0){
				LayoutUtils.moveBandsElemnts(djcross.getTopSpace(), band);
				JRDesignRectangle rect = createBlankRectableCrosstab(djcross.getBottomSpace(), 0);
				band.addElement(rect);
			}

		}

		for (Iterator iterator = columnsGroup.getFooterCrosstabs().iterator(); iterator.hasNext();) {
			DJCrosstab djcross = (DJCrosstab) iterator.next();

			Dj2JrCrosstabBuilder djcb = new Dj2JrCrosstabBuilder();

			JRDesignCrosstab crosst = djcb.createCrosstab(djcross,this);
			JRDesignBand band = (JRDesignBand) jgroup.getGroupFooter();
			int yOffset = LayoutUtils.findVerticalOffset(band);
			if (djcross.getTopSpace() != 0){
//				moveBandsElemnts(djcross.getTopSpace(), band);
				JRDesignRectangle rect = createBlankRectableCrosstab(djcross.getBottomSpace(), yOffset);
				rect.setPositionType(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);
				band.addElement(rect);
				crosst.setY(rect.getY() + rect.getHeight());
			}

			band.addElement(crosst);


			if (djcross.getBottomSpace() != 0){
				JRDesignRectangle rect = createBlankRectableCrosstab(djcross.getBottomSpace(), crosst.getY() + crosst.getHeight());
				band.addElement(rect);
			}
		}


	}

	/**
	 * @param djcross
	 * @param crosst
	 * @return
	 */
	private JRDesignRectangle createBlankRectableCrosstab(int amount,int yOffset) {
		JRDesignRectangle rect = new JRDesignRectangle();
		rect.setPen(Border.NO_BORDER.getValue());
		rect.setMode(Transparency.TRANSPARENT.getValue());
//		rect.setMode(Transparency.OPAQUE.getValue());
//		rect.setBackcolor(Color.RED);
		rect.setWidth(getReport().getOptions().getPrintableWidth());
		rect.setHeight(amount);
		rect.setY(yOffset);
		rect.setPositionType(JRDesignElement.POSITION_TYPE_FLOAT);
		return rect;
	}

	/**
	 * @param columnsGroup
	 * @param col
	 * @return
	 */
	private JRDesignTextField createColumnNameTextField(ColumnsGroup columnsGroup, AbstractColumn col) {
		JRDesignTextField designStaticText = new JRDesignTextField();
		JRDesignExpression exp = new JRDesignExpression();
		exp.setText("\"" + col.getTitle() + "\"");
		exp.setValueClass(String.class);
		designStaticText.setExpression(exp);
		designStaticText.setHeight(columnsGroup.getHeaderHeight().intValue());
		designStaticText.setWidth(col.getWidth().intValue());
		designStaticText.setX(col.getPosX().intValue());
		designStaticText.setY(col.getPosY().intValue());

		applyStyleToElement(col.getHeaderStyle(), designStaticText);
		return designStaticText;
	}

	/**
	 * If there is a SubReport on a Group, we do the layout here
	 * @param columnsGroup
	 * @param jgroup
	 */
	private void layoutGroupSubreports(ColumnsGroup columnsGroup, JRDesignGroup jgroup) {
		log.debug("Starting subreport layout...");
		JRDesignBand footerBand = (JRDesignBand) jgroup.getGroupFooter();
		JRDesignBand headerBand = (JRDesignBand) jgroup.getGroupHeader();

		layOutSubReportInBand(columnsGroup, headerBand, DJConstants.HEADER);
		layOutSubReportInBand(columnsGroup, footerBand, DJConstants.FOOTER);

	}

	/**
	 * @param columnsGroup
	 * @param band
	 * @param position
	 */
	protected void layOutSubReportInBand(ColumnsGroup columnsGroup, JRDesignBand band, String position) {

		List footerSubreportsList = DJConstants.FOOTER.equals(position)
				? columnsGroup.getFooterSubreports()
				: columnsGroup.getHeaderSubreports();

		for (Iterator iterator = footerSubreportsList.iterator(); iterator.hasNext();) {
			Subreport sr = (Subreport) iterator.next();
			JRDesignSubreport subreport = new JRDesignSubreport(new JRDesignStyle().getDefaultStyleProvider());

			//The data source
			int dataSourceOrigin = sr.getDatasource().getDataSourceOrigin();
			if (DJConstants.DATA_SOURCE_ORIGIN_USE_REPORT_CONNECTION == dataSourceOrigin){
				JRDesignExpression connectionExpression = ExpressionUtils.getReportConnectionExpression();
				subreport.setConnectionExpression(connectionExpression);
			} else if (DJConstants.DATA_SOURCE_TYPE_SQL_CONNECTION == sr.getDatasource().getDataSourceType()) {
				JRDesignExpression connectionExpression = ExpressionUtils.getConnectionExpression(sr.getDatasource());
				subreport.setConnectionExpression(connectionExpression);				
			} else {
				JRDesignExpression dataSourceExpression = ExpressionUtils.getDataSourceExpression(sr.getDatasource());
				subreport.setDataSourceExpression(dataSourceExpression);
			}

//			int random_ = subReportRandom.nextInt();
			//the subreport design
			String paramname = sr.getReport().toString(); //TODO ensure this name is unique among all possible subreports
			((DynamicJasperDesign)getDesign()).getParametersWithValues().put(paramname, sr.getReport());
			String expText = "("+JasperReport.class.getName()+")$P{REPORT_PARAMETERS_MAP}.get( \""+ paramname +"\" )";
			JRDesignExpression srExpression = ExpressionUtils.createExpression(expText, JasperReport.class);
			subreport.setExpression(srExpression );


			//set the parameters
			subreport.setParametersMapExpression(ExpressionUtils.getParameterExpression(sr));
			for (Iterator subreportParamsIter = sr.getParameters().iterator(); subreportParamsIter.hasNext();) {
				SubreportParameter srparam = (SubreportParameter) subreportParamsIter.next();
				JRDesignSubreportParameter subreportParameter = new JRDesignSubreportParameter();
				subreportParameter.setName(srparam.getName());
				JRExpression expression2 = ExpressionUtils.createExpression(srparam);
				subreportParameter.setExpression(expression2);
				try {
					subreport.addParameter(subreportParameter );
				} catch (JRException e) {
					log.error("Error registering parameter for subreport, there must be another parameter with the same name");
					throw new CoreException(e.getMessage(),e);
				}
			}

			//some other options (cosmetic)
			//subreport.setStretchType(JRDesignElement.STRETCH_TYPE_NO_STRETCH);
			int offset = LayoutUtils.findVerticalOffset(band);
			subreport.setY(offset);
			subreport.setX(-getReport().getOptions().getLeftMargin().intValue());
			subreport.setWidth(getReport().getOptions().getPage().getWidth());
			subreport.setHeight(100);
			subreport.setPositionType(JRElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);
			subreport.setStretchType(JRElement.STRETCH_TYPE_NO_STRETCH);

			if (sr.getStyle() != null)
				applyStyleToElement(sr.getStyle(), subreport);

			//adding to the band
			if (sr.isStartInNewPage()) {
				JRDesignBreak pageBreak = new JRDesignBreak(new JRDesignStyle().getDefaultStyleProvider()); 
				band.addElement(pageBreak); 
			}
			band.addElement(subreport);
		}
	}

	/**
	 * If variables are present for a given group, they are placed in it's
	 * header/footer band.
	 * @param ColumnsGroup group
	 * @param JRDesignGroup jgroup
	 * @throws LayoutException
	 */
	protected void layoutGroupVariables(ColumnsGroup group, JRDesignGroup jgroup) {
		log.debug("Starting groups variables layout...");
		JRDesignBand headerBand = (JRDesignBand) jgroup.getGroupHeader();
		JRDesignBand footerBand = (JRDesignBand) jgroup.getGroupFooter();

//		int headerOffset = changeHeaderBandHeightForVariables(headerBand, group);
		int headerOffset = 0;

		//Show the current value above the column name
		int yOffset = 0;
		GroupLayout layout = group.getLayout();
		//Only the value in heaeder
		PropertyColumn column = group.getColumnToGroupBy();

		//VALUE_IN_HEADER,
		//VALUE_IN_HEADER_WITH_HEADERS,
		//VALUE_IN_HEADER_AND_FOR_EACH,
		//VALUE_IN_HEADER_AND_FOR_EACH_WITH_HEADERS
		if (layout.isShowValueInHeader() && layout.isHideColumn() && !layout.isShowColumnName()){
			//textvield for the current value
			JRDesignTextField currentValue = generateTextFieldFromColumn(column, getReport().getOptions().getDetailHeight().intValue(), group);

			//The width will be all the page
			currentValue.setWidth(getReport().getOptions().getPrintableWidth());

			//fix the height depending on the font size
			currentValue.setHeight(FontHelper.getHeightFor(column.getStyle().getFont()));
			yOffset += currentValue.getHeight();

			//Move down exisiting elements in the band.
			LayoutUtils.moveBandsElemnts(yOffset-1, headerBand); //Dont know why, but without the "-1" it wont show the headers

			headerBand.addElement(currentValue);
		}
		//DEFAULT and DEFAULT_WITH_HEADER
		else if (layout.isShowValueInHeader() && !layout.isHideColumn() && !layout.isShowColumnName()){
			headerOffset = changeHeaderBandHeightForVariables(headerBand, group);
			insertValueInHeader(headerBand, group, headerOffset);
		}
		//VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME
		else if (layout.isShowValueInHeader() && layout.isHideColumn() && layout.isShowColumnName()){
			//Create the element for the column name
			JRDesignTextField columnNameTf = createColumnNameTextField(group, column);
			columnNameTf.setY(columnNameTf.getY() + headerOffset);

			//textvield for the current value
			JRDesignTextField currentValue = generateTextFieldFromColumn(column, getReport().getOptions().getDetailHeight().intValue(), group);

			//The width will be (width of the page) - (column name width)
			currentValue.setWidth(getReport().getOptions().getPrintableWidth() - columnNameTf.getWidth());
			//The x position for the current value is right next to the column name
			currentValue.setX(columnNameTf.getWidth());

			//fix the height depending on the font size
			currentValue.setHeight(FontHelper.getHeightFor(column.getStyle().getFont()));
			columnNameTf.setHeight(currentValue.getHeight());

			yOffset += currentValue.getHeight();

			//Move down exisiting elements in the band.
			LayoutUtils.moveBandsElemnts(yOffset, headerBand);

			headerBand.addElement(columnNameTf);
			headerBand.addElement(currentValue);
		}

		placeVariableInBand(group.getHeaderVariables(), group, jgroup, DJConstants.HEADER, headerBand, headerOffset);
		placeVariableInBand(group.getFooterVariables(), group, jgroup, DJConstants.FOOTER, footerBand, 0);
	}

	private void placeVariableInBand(List variables, ColumnsGroup columnsGroup, JRDesignGroup jgroup, String type, JRDesignBand band, int yOffset) {
		log.debug("Placing variables in "+type+" band...");
		if ((variables != null)&&(variables.size()>0)) {
			Iterator it = variables.iterator();
			while (it.hasNext()) {
				ColumnsGroupVariable var = (ColumnsGroupVariable) it.next();
				AbstractColumn col = var.getColumnToApplyOperation();

				String variableName = col.getGroupVariableName(type, columnsGroup.getColumnToGroupBy().getColumnProperty().getProperty());

				JRDesignExpression expression = new JRDesignExpression();
				JRDesignTextField textField = new JRDesignTextField();
				expression.setText("$V{" + variableName + "}");
				expression.setValueClassName(col.getVariableClassName(var.getOperation()));
				if (var.getOperation() != ColumnsGroupVariableOperation.COUNT)
					textField.setPattern(col.getPattern());

				textField.setKey(variableName);
				textField.setExpression(expression);

				if (DJConstants.FOOTER.equals(type)){
					textField.setPositionType(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);
				}

				textField.setX(col.getPosX().intValue());
				if (yOffset!=0)
					textField.setY(yOffset);

//				textField.setHeight(columnsGroup.getHeaderHeight().intValue());
				textField.setHeight(2 + getReport().getOptions().getDetailHeight().intValue()); //XXX be carefull with the "2+ ..."
				textField.setWidth(col.getWidth().intValue());

				textField.setEvaluationTime(JRExpression.EVALUATION_TIME_GROUP);

				textField.setEvaluationGroup(jgroup);
				//Assign the style to the element.
				//First we look for the specific element style, then the default style for the group variables
				//and finally the column style.
				Style defStyle = DJConstants.HEADER.equals(type)?columnsGroup.getDefaulHeaderStyle():columnsGroup.getDefaulFooterStyle();

				if (var.getStyle() != null)
					applyStyleToElement(var.getStyle(), textField);
				else if (defStyle != null)
					applyStyleToElement(defStyle, textField);
				else
					applyStyleToElement(col.getStyle(), textField);


				band.addElement(textField);

			}

			if (columnsGroup.getColumnToGroupBy() instanceof GlobalGroupColumn) {
				int totalWidth = 0;

				ColumnsGroupVariable leftmostColumn = findLeftMostColumn(variables);
				totalWidth = leftmostColumn.getColumnToApplyOperation().getPosX().intValue();

				GlobalGroupColumn globalCol = (GlobalGroupColumn) columnsGroup.getColumnToGroupBy();

				JRDesignTextField globalTextField = new JRDesignTextField();
				JRDesignExpression globalExp = new JRDesignExpression();
				globalExp.setText(globalCol.getTextForExpression());
				globalExp.setValueClassName(globalCol.getValueClassNameForExpression());
				globalTextField.setExpression(globalExp);

//				globalTextField.setHeight(band.getHeight()); //XXX Changed, see if its ok
				globalTextField.setHeight(2 + getReport().getOptions().getDetailHeight().intValue()); //XXX be carefull with the "2+ ..."
				globalTextField.setWidth(totalWidth);
//				globalTextField.setX(((AbstractColumn)getReport().getColumns().get(0)).getPosX().intValue());
				globalTextField.setX(0);
				if (type.equals(ColumnsGroupVariablesRegistrationManager.HEADER))
					globalTextField.setY(yOffset);
				globalTextField.setKey("global_legend_"+type);

				applyStyleToElement(globalCol.getStyle(), globalTextField);

				band.addElement(globalTextField);
			}
		}
	}

	private ColumnsGroupVariable findLeftMostColumn(List variables) {
		int mostLeftX = Integer.MAX_VALUE;
		ColumnsGroupVariable mostLeftColumn =  null;
		for (Iterator iterator = variables.iterator(); iterator.hasNext();) {
			ColumnsGroupVariable currentCol = (ColumnsGroupVariable) iterator.next();
			if (currentCol.getColumnToApplyOperation().getPosX().intValue() <= mostLeftX) {
				mostLeftColumn = currentCol;
                mostLeftX = mostLeftColumn.getColumnToApplyOperation().getPosX().intValue();
            }
        }
		return mostLeftColumn;
	}

	private void insertValueInHeader(JRDesignBand headerBand, ColumnsGroup columnsGroup, int headerOffset) {
//		JRDesignTextField textField = generateTextFieldFromColumn(columnsGroup.getColumnToGroupBy(), columnsGroup.getHeaderHeight().intValue(), columnsGroup);
		JRDesignTextField textField = generateTextFieldFromColumn(columnsGroup.getColumnToGroupBy(), getReport().getOptions().getDetailHeight().intValue(), columnsGroup);
		textField.setHorizontalAlignment(columnsGroup.getColumnToGroupBy().getStyle().getHorizontalAlign().getValue());
		textField.setStretchType(JRDesignElement.STRETCH_TYPE_NO_STRETCH); //XXX this is a patch for subreports, ensure it works well.
		textField.setY(textField.getY() + headerOffset);
		headerBand.addElement(textField);
	}

	private int changeHeaderBandHeightForVariables(JRDesignBand headerBand, ColumnsGroup columnsGroup) {
		int result = 0;
		if (!headerBand.getChildren().isEmpty()) {
			int acu = headerBand.getHeight();
			headerBand.setHeight(headerBand.getHeight() + columnsGroup.getHeaderHeight().intValue());
			result = acu;
		} else
			headerBand.setHeight(getReport().getOptions().getFooterHeight().intValue());
		return result;
	}

	private void generateHeaderBand() {
		log.debug("generating header band...");
		JRDesignBand header = (JRDesignBand) getDesign().getColumnHeader();
		if (header == null) {
			header = new JRDesignBand();
			getDesign().setColumnHeader(header);
		}


		/**
		 * Note: Te column names, when in header, are printed at the begining of every page.
		 * You may dont want this option if you have groups that prints column names.
		 */
		if (getReport().getOptions().isPrintColumnNames()){
			generateHeaderBand(header);			
		}

//		if (!DynamicJasperHelper.existsGroupWithColumnNames(getReport().getColumnsGroups()))
//			generateHeaderBand(header);
	}

	protected void transformDetailBandTextField(AbstractColumn column, JRDesignTextField textField) {
		//TODO: Set default characters when null values are found.
		log.debug("transforming detail band text field...");
		ColumnsGroup group = DynamicJasperHelper.getColumnGroup(column, getReport().getColumnsGroups());
		if (group!=null&&!group.getLayout().isShowValueForEachRow()) {
			textField.setExpression(null); //this way, the textfield is not added to the band
		}
	}

}
