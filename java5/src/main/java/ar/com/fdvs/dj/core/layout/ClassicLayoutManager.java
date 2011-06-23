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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignBreak;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JRDesignTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.CoreException;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.FontHelper;
import ar.com.fdvs.dj.core.registration.ColumnsGroupVariablesRegistrationManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DJLabel;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.GlobalGroupColumn;
import ar.com.fdvs.dj.domain.entities.columns.PercentageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.ExpressionUtils;
import ar.com.fdvs.dj.util.LayoutUtils;
import ar.com.fdvs.dj.util.Utils;

/**
 * Main Layout Manager recommended for most cases.</br>
 * </br>
 * It provides DJ full features (styles, groups, conditional styles, </br>
 * expressions, group and total variables, etc)
 */
public class ClassicLayoutManager extends AbstractLayoutManager {

	protected static final String PAGE_BREAK_FOR_ = "pageBreak_for_";

	protected static final int SUBREPORT_DEFAULT_HEIGHT = 30;

	private static final Log log = LogFactory.getLog(ClassicLayoutManager.class);

	protected static final String EXPRESSION_TRUE_WHEN_NOT_FIRST_PAGE = "new java.lang.Boolean(((Number)$V{PAGE_NUMBER}).doubleValue() != 1)";
	protected static final String EXPRESSION_TRUE_WHEN_FIRST_PAGE = "new java.lang.Boolean(((Number)$V{PAGE_NUMBER}).doubleValue() == 1)";

	protected Map referencesMap = new HashMap();

	public Map getReferencesMap() {
		return referencesMap;
	}

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
					CommonExpressionsHelper.add(yOffset,(DynamicJasperDesign) getDesign(), this, headerband, text);
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
	protected int findTotalOffset(ArrayList aligments, ArrayList autotexts, byte position) {
		int total = 0;
		for (Iterator iterator = aligments.iterator(); iterator.hasNext();) {
			HorizontalBandAlignment currentAlignment = (HorizontalBandAlignment) iterator.next();
			int aux = 0;
			for (Iterator iter = getReport().getAutoTexts().iterator(); iter.hasNext();) {
				AutoText autotext = (AutoText) iter.next();
				if (autotext.getPosition() == position && currentAlignment.equals(autotext.getAlignment())) {
					aux += autotext.getHeight().intValue();
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
				AutoText autotext = (AutoText) iter.next();
				if (autotext.getPosition() == AutoText.POSITION_FOOTER && autotext.getAlignment().equals(currentAlignment) ) {
					CommonExpressionsHelper.add(yOffset,(DynamicJasperDesign) getDesign(), this, footerband, autotext);
					yOffset += autotext.getHeight().intValue();
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
			DJGroup group = (DJGroup) iterator.next();
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
				image.setScaleImage(imageBanner.getScaleMode().getValue());

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
		if (getReport().isTitleIsJrExpression()){
			exp.setText(getReport().getTitle());
		}else {
			exp.setText("\"" + Utils.escapeTextForExpression( getReport().getTitle()) + "\"");
		}
		exp.setValueClass(String.class);
		title.setExpression(exp);
		title.setWidth(getReport().getOptions().getPrintableWidth());
		title.setHeight(getReport().getOptions().getTitleHeight().intValue());
		title.setY(yOffset);
		title.setPrintWhenExpression(printWhenExpression);
		title.setRemoveLineWhenBlank(true);
		applyStyleToElement(getReport().getTitleStyle(), title);
		title.setStretchType(JRGraphicElement.STRETCH_TYPE_NO_STRETCH);
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
			title.setStretchType(JRGraphicElement.STRETCH_TYPE_NO_STRETCH);
			band.addElement(subtitle);
		}

	}

	/**
	 * Layout columns in groups by reading the corresponding report options.
	 * @throws LayoutException
	 */
	protected void layoutGroups() {
		log.debug("Starting groups layout...");
		for (Iterator iter = getReport().getColumnsGroups().iterator(); iter.hasNext();) {
			DJGroup columnsGroup = (DJGroup) iter.next();
			JRDesignGroup jgroup = getJRGroupFromDJGroup(columnsGroup);

			jgroup.setStartNewPage(columnsGroup.getStartInNewPage().booleanValue());
			jgroup.setStartNewColumn(columnsGroup.getStartInNewColumn().booleanValue());
			jgroup.setReprintHeaderOnEachPage(columnsGroup.getReprintHeaderOnEachPage().booleanValue());

			JRDesignSection headerSection = (JRDesignSection) jgroup.getGroupHeaderSection();
			JRDesignSection footerSection = (JRDesignSection) jgroup.getGroupFooterSection();

			JRDesignBand header = LayoutUtils.getBandFromSection(headerSection);
			JRDesignBand footer = LayoutUtils.getBandFromSection(footerSection);

			//double check to prevent NPE
			if (header == null){
				header = new JRDesignBand();
				headerSection.addBand(header);
			}
			if (footer == null){
				footer = new JRDesignBand();
				footerSection.addBand(footer);
			}

			header.setHeight(columnsGroup.getHeaderHeight().intValue());
//			footer.setHeight( getFooterVariableHeight(columnsGroup));
			footer.setHeight( columnsGroup.getFooterHeight().intValue());

			header.setSplitAllowed(columnsGroup.isAllowHeaderSplit()); //FIXME deprecado
			footer.setSplitAllowed(columnsGroup.isAllowFooterSplit());

			if (columnsGroup.getLayout().isPrintHeaders()) {
				boolean found = false;
				boolean skipPreviousGroupHeaders = false;
				int groupIdx = getReport().getColumnsGroups().indexOf(columnsGroup);
				if (groupIdx>0){
					DJGroup prevG =  (DJGroup) getReport().getColumnsGroups().get(groupIdx-1);
                    if(!(prevG.getColumnToGroupBy() instanceof GlobalGroupColumn))
                        skipPreviousGroupHeaders = !prevG.getLayout().isShowValueForEachRow();
				}
				for (Iterator iterator =  getVisibleColumns().iterator(); iterator.hasNext();) {
					AbstractColumn col = (AbstractColumn) iterator.next();

					//If in a nested group, header for column prior to this groups column
					//depends on configuration

					if (col.equals(columnsGroup.getColumnToGroupBy())) {
						found = true;
					}

					if (!found && skipPreviousGroupHeaders){
						continue;
					}

					JRDesignTextField designTextField = createColumnNameTextField(columnsGroup, col);
					designTextField.setPositionType(JRDesignElement.POSITION_TYPE_FLOAT); //XXX changed to see what happens  (must come from the column position property)
					designTextField.setStretchType(JRDesignElement.STRETCH_TYPE_NO_STRETCH); //XXX changed to see what happens (must come from the column property)
					header.addElement(designTextField);
				}
			}

			DJGroupLabel label = columnsGroup.getFooterLabel();
			if (label != null /*&& !footerVariables.isEmpty()*/) {
				List footerVariables = columnsGroup.getFooterVariables();
				PropertyColumn col = columnsGroup.getColumnToGroupBy();
//				JRDesignBand band = (JRDesignBand)jgroup.getGroupFooter();
				JRDesignBand band = footer;
				int x = 0, y = 0;
				//max width
				int width = getDesign().getPageWidth() - getDesign().getLeftMargin() - getDesign().getRightMargin();
				int height = label.getHeight();
				int yOffset = 0;
				if (label.getLabelPosition() == LabelPosition.LEFT) {
					DJGroupVariable lmvar = findLeftMostColumn(footerVariables);

					x = col.getPosX().intValue(); //label starts in the column-to-group-by x position
					y = findYOffsetForGroupLabel(band);
					if (lmvar != null) {
						AbstractColumn lmColumn = lmvar.getColumnToApplyOperation();
						width = lmColumn.getPosX().intValue() - x;
					}
					else
						width -= x;
					height = getFooterVariableHeight(columnsGroup);
				}
				else if (label.getLabelPosition() == LabelPosition.RIGHT) {
					DJGroupVariable rmvar = findRightMostColumn(footerVariables);

					if (rmvar != null) {
						AbstractColumn rmColumn = rmvar.getColumnToApplyOperation();
						x = rmColumn.getPosX().intValue() + rmColumn.getWidth().intValue();
					}
					else
						x = col.getPosX().intValue(); //label starts in the column-to-group-by x position
					y = findYOffsetForGroupLabel(band);
					width -= x;
					height = getFooterVariableHeight(columnsGroup);
				}
				else if (label.getLabelPosition() == LabelPosition.TOP) {
					x = col.getPosX().intValue(); //label starts in the column-to-group-by x position
					width -= x;
					yOffset = height;
				}
				else if (label.getLabelPosition() == LabelPosition.BOTTOM) {
					x = col.getPosX().intValue(); //label starts in the column-to-group-by x position
					y = getFooterVariableHeight(columnsGroup);
					width -= x;
				}
				layoutGroupFooterLabels(columnsGroup, jgroup, x, y, width, height);
				layoutGroupVariables(columnsGroup, jgroup, yOffset);
			}
			else {
				layoutGroupVariables(columnsGroup, jgroup, 0);
			}

			layoutGroupSubreports(columnsGroup, jgroup);
			layoutGroupCrosstabs(columnsGroup, jgroup);
		}
	}

	/**
	 * Creates needed textfields for general label in footer groups.
	 * @param djgroup
	 * @param jgroup
	 */
	protected void layoutGroupFooterLabels(DJGroup djgroup, JRDesignGroup jgroup, int x, int y, int width, int height) {
		//List footerVariables = djgroup.getFooterVariables();
		DJGroupLabel label = djgroup.getFooterLabel();

		//if (label == null || footerVariables.isEmpty())
			//return;

		//PropertyColumn col = djgroup.getColumnToGroupBy();
		JRDesignBand band =  LayoutUtils.getBandFromSection((JRDesignSection) jgroup.getGroupFooterSection());

//		log.debug("Adding footer group label for group " + djgroup);

		/*DJGroupVariable lmvar = findLeftMostColumn(footerVariables);
		AbstractColumn lmColumn = lmvar.getColumnToApplyOperation();
		int width = lmColumn.getPosX().intValue()  - col.getPosX().intValue();

		int yOffset = findYOffsetForGroupLabel(band);*/

		JRDesignExpression labelExp;
		if (label.isJasperExpression()) //a text with things like "$F{myField}"
			labelExp = ExpressionUtils.createStringExpression(label.getText());
		else if (label.getLabelExpression() != null){
			labelExp = ExpressionUtils.createExpression(jgroup.getName() + "_labelExpression", label.getLabelExpression());
		} else //a simple text
			//labelExp = ExpressionUtils.createStringExpression("\""+ Utils.escapeTextForExpression(label.getText())+ "\"");
			labelExp = ExpressionUtils.createStringExpression("\""+ label.getText() + "\"");
		JRDesignTextField labelTf = new JRDesignTextField();
		labelTf.setExpression(labelExp);
		labelTf.setWidth(width);
		labelTf.setHeight(height);
		labelTf.setX(x);
		labelTf.setY(y);
		//int yOffsetGlabel = labelTf.getHeight();
		labelTf.setPositionType(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);
		applyStyleToElement(label.getStyle(), labelTf);
		band.addElement(labelTf);
	}

	/**
	 * Used to ensure that the general footer label will be at the same Y position as the variables in the band.
	 * @param band
	 * @return
	 */
	private int findYOffsetForGroupLabel(JRDesignBand band) {
		int offset = 0;
		for (Iterator iterator = band.getChildren().iterator(); iterator.hasNext();) {
			JRDesignElement elem = (JRDesignElement) iterator.next();
			if (elem.getKey() != null && elem.getKey().startsWith("variable_for_column_")){
				offset = elem.getY();
				break;
			}
		}
		return offset;
	}

	/**
	 * Looks for crosstabs in the groups, if any, it does the layout
	 *
	 * @param columnsGroup
	 * @param jgroup
	 */
	protected void layoutGroupCrosstabs(DJGroup columnsGroup,	JRDesignGroup jgroup) {
		for (Iterator iterator = columnsGroup.getHeaderCrosstabs().iterator(); iterator.hasNext();) {
			DJCrosstab djcross = (DJCrosstab) iterator.next();

			Dj2JrCrosstabBuilder djcb = new Dj2JrCrosstabBuilder();

			JRDesignCrosstab crosst = djcb.createCrosstab(djcross,this);
			JRDesignBand band = LayoutUtils.getBandFromSection((JRDesignSection) jgroup.getGroupHeaderSection());
			if (djcross.getBottomSpace() != 0){
				JRDesignRectangle rect = createBlankRectableCrosstab(djcross.getBottomSpace(), 0);
				LayoutUtils.moveBandsElemnts(rect.getHeight(), band);
				band.addElement(rect);
			}

			LayoutUtils.moveBandsElemnts(crosst.getHeight(), band);
			band.addElement(crosst);
			DJLabel caption = djcross.getCaption();
			if (caption!=null){
				JRDesignExpression captExp = null;
				if (caption.isJasperExpression()) //a text with things like "$F{myField}"
					captExp = ExpressionUtils.createStringExpression(caption.getText());
				else if (caption.getLabelExpression() != null){
					String name = "expression_for_label_at_header_of_group[" + getReport().getColumnsGroups().indexOf(columnsGroup)+"]_crosstab["+columnsGroup.getHeaderCrosstabs().indexOf(djcross)+"]";
					LayoutUtils.registerCustomExpressionParameter((DynamicJasperDesign) getDesign(), name , caption.getLabelExpression());
					String invocationText = ExpressionUtils.createCustomExpressionInvocationText(caption.getLabelExpression(),name);
					captExp =   ExpressionUtils.createExpression(invocationText, caption.getLabelExpression().getClassName()) ;
					log.debug(invocationText);
				} else //a simple text
					captExp = ExpressionUtils.createStringExpression("\""+ Utils.escapeTextForExpression(caption.getText())+ "\"");

				JRDesignTextField captTf = new JRDesignTextField();
				captTf.setExpression(captExp );
				captTf.setHeight(caption.getHeight());
				captTf.setWidth(getReport().getOptions().getPrintableWidth());
				LayoutUtils.moveBandsElemnts(caption.getHeight(), band);
				band.addElement(captTf);
			}

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
			JRDesignBand band = LayoutUtils.getBandFromSection((JRDesignSection) jgroup.getGroupFooterSection());
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
	protected JRDesignRectangle createBlankRectableCrosstab(int amount,int yOffset) {
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
	protected JRDesignTextField createColumnNameTextField(DJGroup columnsGroup, AbstractColumn col) {
		JRDesignTextField designStaticText = new JRDesignTextField();
		JRDesignExpression exp = new JRDesignExpression();
		exp.setText("\"" + col.getTitle() + "\"");
		exp.setValueClass(String.class);
		designStaticText.setExpression(exp);
		designStaticText.setHeight(columnsGroup.getHeaderHeight().intValue());
		designStaticText.setWidth(col.getWidth().intValue());
		designStaticText.setX(col.getPosX().intValue());
		designStaticText.setY(col.getPosY().intValue());

		Style headerStyle = columnsGroup.getColumnHeaderStyle(col);
		if (headerStyle == null)
			headerStyle = columnsGroup.getDefaultColumnHeaederStyle();
		if (headerStyle == null)
			headerStyle = col.getHeaderStyle();

		applyStyleToElement(headerStyle, designStaticText);
		return designStaticText;
	}

	/**
	 * If there is a SubReport on a Group, we do the layout here
	 * @param columnsGroup
	 * @param jgroup
	 */
	protected void layoutGroupSubreports(DJGroup columnsGroup, JRDesignGroup jgroup) {
		log.debug("Starting subreport layout...");
		JRDesignBand footerBand = (JRDesignBand) ((JRDesignSection)jgroup.getGroupFooterSection()).getBandsList().get(0);
		JRDesignBand headerBand = (JRDesignBand) ((JRDesignSection)jgroup.getGroupHeaderSection()).getBandsList().get(0);

		layOutSubReportInBand(columnsGroup, headerBand, DJConstants.HEADER);
		layOutSubReportInBand(columnsGroup, footerBand, DJConstants.FOOTER);

	}

	/**
	 * @param columnsGroup
	 * @param band
	 * @param position
	 */
	protected void layOutSubReportInBand(DJGroup columnsGroup, JRDesignBand band, String position) {

		List subreportsList = DJConstants.FOOTER.equals(position)
				? columnsGroup.getFooterSubreports()
				: columnsGroup.getHeaderSubreports();

		for (Iterator iterator = subreportsList.iterator(); iterator.hasNext();) {
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
//			String paramname = sr.getReport().toString(); //TODO ensure this name is unique among all possible subreports
			String paramname = sr.getName(); //TODO ensure this name is unique among all possible subreports
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
				JRExpression expression2 = ExpressionUtils.createExpression(getDesign(), srparam);
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
			subreport.setHeight(SUBREPORT_DEFAULT_HEIGHT);
			subreport.setPositionType(JRElement.POSITION_TYPE_FLOAT);
			subreport.setStretchType(JRElement.STRETCH_TYPE_NO_STRETCH);
			subreport.setRemoveLineWhenBlank(true); //No subreport, no reserved space

			band.setHeight(offset + subreport.getHeight());

			if (sr.getStyle() != null)
				applyStyleToElement(sr.getStyle(), subreport);

			//adding to the band
			if (sr.isStartInNewPage()) {
				JRDesignGroup jrgroup = getJRGroupFromDJGroup(columnsGroup);
				JRDesignBand targetBand = null;
				int idx = getDesign().getGroupsList().indexOf(jrgroup);
				if (DJConstants.HEADER.equals(position)) {
//					if (idx == 0){
//						if (getDesign().getColumnHeader() != null)
//							targetBand = (JRDesignBand) getDesign().getColumnHeader();
//						else if (getDesign().getPageHeader() != null)
//							targetBand = (JRDesignBand) getDesign().getPageHeader();
//						else
//							targetBand = band;
//					}
//					else
//						targetBand = (JRDesignBand) ((JRDesignGroup) getDesign().getGroupsList().get(idx-1)).getGroupHeader();
				}
				else { //footer subreport (and concatenated report)
					if (idx+1 <  getDesign().getGroupsList().size())
						idx++;
					JRDesignGroup jrGroup = (JRDesignGroup) getDesign().getGroupsList().get(idx);
					targetBand = LayoutUtils.getBandFromSection((JRDesignSection) jrGroup.getGroupFooterSection());
				}

				/**
				 * There is no meaning in adding a page-break in header sub reports since
				 * they will be placed right after the group header
				 */
				if (DJConstants.FOOTER.equals(position)){
					JRDesignBreak pageBreak = new JRDesignBreak(new JRDesignStyle().getDefaultStyleProvider());
					pageBreak.setKey(PAGE_BREAK_FOR_ + jrgroup.toString()); //set up a name to recognize the item later
					pageBreak.setY(0);
					pageBreak.setPositionType(JRDesignElement.POSITION_TYPE_FLOAT);
					targetBand.addElement(pageBreak);
				}

			}
			band.addElement(subreport);

			sendPageBreakToBottom(band);

			/**
			 * A subreport is placed in a group header or footer. This option configures the group's
			 * header/footer band to allow it contents to be split. I'm not sure splitting logic works
			 * inside complex object such as sub-reports since it has it's own bands inside
			 */
			band.setSplitAllowed(sr.isSplitAllowed());
		}
	}

	/**
	 * page breaks should be near the bottom of the band, this method used while adding subreports
	 * which has the "start on new page" option.
	 * @param band
	 */
	protected void sendPageBreakToBottom(JRDesignBand band) {
		JRElement[] elems = band.getElements();
		JRElement aux = null;
		for (int i = 0; i < elems.length; i++) {
			if ((""+elems[i].getKey()).startsWith(PAGE_BREAK_FOR_)){
				aux = elems[i];
				break;
			}
		}
		if (aux != null)
			((JRDesignElement)aux).setY(band.getHeight());
	}

	/**
	 * If variables are present for a given group, they are placed in it's
	 * header/footer band.
	 * @param DJGroup group
	 * @param JRDesignGroup jgroup
	 * @param int labelOffset
	 * @throws LayoutException
	 */
	protected void layoutGroupVariables(DJGroup group, JRDesignGroup jgroup, int labelOffset) {
		log.debug("Starting groups variables layout...");

//		JRDesignBand headerBand = (JRDesignBand) jgroup.getGroupHeader();
		JRDesignSection headerSection = (JRDesignSection) jgroup.getGroupHeaderSection();
		JRDesignBand headerBand = (JRDesignBand) headerSection.getBandsList().get(0);
		if (headerBand == null){
			headerBand = new JRDesignBand();
			headerSection.addBand(headerBand);
//			jgroup.setGroupHeader(headerBand);
		}

		JRDesignSection footerSection = (JRDesignSection) jgroup.getGroupFooterSection();
		JRDesignBand footerBand = (JRDesignBand) footerSection.getBandsList().get(0);
		if (footerBand == null){
			footerBand = new JRDesignBand();
			footerSection.addBand(footerBand);
//			jgroup.setGroupFooter(footerBand);
		}

		int headerOffset = 0;

		//Show the current value above the column name
		int yOffset = 0;
		GroupLayout layout = group.getLayout();
		//Only the value in header
		PropertyColumn column = group.getColumnToGroupBy();

		Integer height = group.getHeaderVariablesHeight()!=null
		? group.getHeaderVariablesHeight()
				:getReport().getOptions().getDetailHeight();

		//VALUE_IN_HEADER,
		//VALUE_IN_HEADER_WITH_HEADERS,
		//VALUE_IN_HEADER_AND_FOR_EACH,
		//VALUE_IN_HEADER_AND_FOR_EACH_WITH_HEADERS
		if (layout.isShowValueInHeader() && layout.isHideColumn() && !layout.isShowColumnName()){
			//textfield for the current value
			JRDesignTextField currentValue = generateTextFieldFromColumn(column, height.intValue(), group);
			currentValue.setPositionType(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);

			//The width will be all the page, except for the width of the header variables
            int headerVariablesWidth = getReport().getOptions().getPrintableWidth();

            if (!group.getHeaderVariables().isEmpty()){
            	DJGroupVariable leftmostcol = findLeftMostColumn(group.getHeaderVariables());
            	headerVariablesWidth = leftmostcol.getColumnToApplyOperation().getPosX().intValue();
            	if (groupLabelsPresent(group.getHeaderVariables())){
            		currentValue.setY(height.intValue());
            		currentValue.setHeight(getHeaderVariablesHeight(group));
            	}
            }
            currentValue.setWidth(headerVariablesWidth);

			//fix the height depending on the font size
//			currentValue.setHeight(FontHelper.getHeightFor(column.getStyle().getFont())); //XXX CAREFULL
			yOffset += currentValue.getHeight();

			//Move down existing elements in the band.
			LayoutUtils.moveBandsElemnts(yOffset-1, headerBand); //Don't know why, but without the "-1" it wont show the headers

			if (group.getLayout().isPrintHeaders()){
				headerOffset += group.getHeaderHeight().intValue() + getReport().getOptions().getDetailHeight().intValue();
			}

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

			//textfield for the current value
			JRDesignTextField currentValue = generateTextFieldFromColumn(column, height.intValue(), group);

			//The width will be (width of the page) - (column name width)
			currentValue.setWidth(getReport().getOptions().getPrintableWidth() - columnNameTf.getWidth());
			//The x position for the current value is right next to the column name
			currentValue.setX(columnNameTf.getWidth());

			//fix the height depending on the font size
			//fixes issue 2817859
			//currentValue.setHeight(FontHelper.getHeightFor(column.getStyle().getFont()));
			if (column.getStyle() != null && column.getStyle().getFont() != null)
				currentValue.setHeight(FontHelper.getHeightFor(column.getStyle().getFont()));
			columnNameTf.setHeight(currentValue.getHeight());

			yOffset += currentValue.getHeight();

			//Move down existing elements in the band.
			LayoutUtils.moveBandsElemnts(yOffset, headerBand);

			headerBand.addElement(columnNameTf);
			headerBand.addElement(currentValue);
		}

		placeVariableInBand(group.getHeaderVariables(), group, jgroup, DJConstants.HEADER, headerBand, headerOffset);
		placeVariableInBand(group.getFooterVariables(), group, jgroup, DJConstants.FOOTER, footerBand, labelOffset);
	}


	/**
	 *
	 * @param groupVariables
	 * @return
	 */
	protected boolean groupLabelsPresent(List groupVariables) {
		for (Iterator iterator = groupVariables.iterator(); iterator.hasNext();) {
			DJGroupVariable var = (DJGroupVariable) iterator.next();
			if (var.getLabel() != null)
				return true;

		}
		return false;
	}

	/**
	 *
	 * @param variables
	 * @param djGroup
	 * @param jgroup
	 * @param type (header or footer)
	 * @param band
	 * @param yOffset
	 */
	protected void placeVariableInBand(List variables, DJGroup djGroup, JRDesignGroup jgroup, String type, JRDesignBand band, int yOffset) {
		if ((variables == null) || (variables.isEmpty())) {
			return;
		}

		boolean inFooter = DJConstants.FOOTER.equals(type);

		log.debug("Placing variables in "+type+" band for group " + djGroup.getColumnToGroupBy().getTextForExpression());

		int height = 0;
		if (inFooter)
			height = getFooterVariableHeight(djGroup);
		else
			height = getHeaderVariablesHeight(djGroup);

		Iterator it = variables.iterator();
		int yOffsetGlabel = 0;
		while (it.hasNext()) {
			DJGroupVariable var = (DJGroupVariable) it.next();
			AbstractColumn col = var.getColumnToApplyOperation();

			//Build the expression for the variable
			String variableName = col.getGroupVariableName(type, djGroup.getColumnToGroupBy().getColumnProperty().getProperty());

			//Add the group label
			DJGroupLabel label = var.getLabel();
			JRDesignTextField labelTf = null;
			if (label != null){
				JRDesignExpression labelExp;
				if (label.isJasperExpression()) //a text with things like "$F{myField}"
					labelExp = ExpressionUtils.createStringExpression(label.getText());
				else if (label.getLabelExpression() != null){
					labelExp = ExpressionUtils.createExpression(variableName + "_labelExpression", label.getLabelExpression());
				} else //a simple text
					//labelExp = ExpressionUtils.createStringExpression("\""+ Utils.escapeTextForExpression(label.getText())+ "\"");
					labelExp = ExpressionUtils.createStringExpression("\""+ label.getText() + "\"");
				labelTf = new JRDesignTextField();
				labelTf.setExpression(labelExp);
				labelTf.setWidth(col.getWidth().intValue());
				labelTf.setHeight(label.getHeight());
				labelTf.setX(col.getPosX().intValue());
				labelTf.setY(yOffset);
				yOffsetGlabel = labelTf.getHeight();
				if (inFooter){
					labelTf.setPositionType(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);
				}
				applyStyleToElement(label.getStyle(), labelTf);
				band.addElement(labelTf);

			}

			JRDesignExpression expression = new JRDesignExpression();
			JRDesignTextField textField = new JRDesignTextField();

			if (inFooter)
				textField.setEvaluationTime(JRExpression.EVALUATION_TIME_NOW); //This will enable textfield streching
			else
				textField.setEvaluationTime(JRExpression.EVALUATION_TIME_GROUP);

			if (var.getValueExpression() != null) {
				expression = ExpressionUtils.createExpression(variableName + "_valueExpression", var.getValueExpression());
			}
			else
				setTextAndClassToExpression(expression,var,col,variableName);

			if (var.getOperation() != DJCalculation.COUNT && var.getOperation() != DJCalculation.DISTINCT_COUNT )
				textField.setPattern(col.getPattern());

			if (col instanceof PercentageColumn) {
				PercentageColumn pcol = (PercentageColumn) col;
				expression.setText(pcol.getTextForExpression(djGroup, djGroup ,type));
				expression.setValueClassName(pcol.getValueClassNameForExpression());
				textField.setEvaluationTime(JRExpression.EVALUATION_TIME_AUTO);
			} else {
				textField.setEvaluationGroup(jgroup);
			}

			textField.setKey(variableName);
			textField.setExpression(expression);


			if (inFooter){
				textField.setPositionType(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);
			}

			textField.setX(col.getPosX().intValue());

			//if (yOffset!=0)
			textField.setY(yOffset + yOffsetGlabel);

			textField.setHeight(0 + height ); //XXX be carefull with the "2+ ..."

			textField.setWidth(col.getWidth().intValue());


			textField.setKey("variable_for_column_"+ getVisibleColumns().indexOf(col) + "_in_group_" + getDesign().getGroupsList().indexOf(jgroup));


			//Assign the style to the element.
			//First we look for the specific element style, then the default style for the group variables
			//and finally the column style.
			Style defStyle = DJConstants.HEADER.equals(type)
						? djGroup.getDefaulHeaderVariableStyle()
						: djGroup.getDefaulFooterVariableStyle();

			if (var.getStyle() != null)
				applyStyleToElement(var.getStyle(), textField);
			else if (col.getStyle() != null) {
				//Last resource is to use the column style, but a copy of it because
				//the one in the internal cache can get modified by the layout manager (like in the odd row case)
				Style style = col.getStyle();
				try {
					style = (Style) style.clone();
					style.setName(null); //set to null to make applyStyleToElement(...) assign a name
				} catch (Exception e) {	}
				applyStyleToElement(style, textField);
			}
			else if (defStyle != null)
				applyStyleToElement(defStyle, textField);

			if (var.getPrintWhenExpression() != null) {
				JRDesignExpression exp = ExpressionUtils.createExpression(variableName + "_printWhenExpression", var.getPrintWhenExpression());
				textField.setPrintWhenExpression(exp);
				if (labelTf != null)
					labelTf.setPrintWhenExpression(exp);
			}

			band.addElement(textField);

		}

		if (djGroup.getColumnToGroupBy() instanceof GlobalGroupColumn) {
			int totalWidth = 0;

			DJGroupVariable leftmostColumn = findLeftMostColumn(variables);
			totalWidth = leftmostColumn.getColumnToApplyOperation().getPosX().intValue();

			GlobalGroupColumn globalCol = (GlobalGroupColumn) djGroup.getColumnToGroupBy();

			JRDesignTextField globalTextField = new JRDesignTextField();
			JRDesignExpression globalExp = new JRDesignExpression();
			globalExp.setText(globalCol.getTextForExpression());
			globalExp.setValueClassName(globalCol.getValueClassNameForExpression());
			globalTextField.setExpression(globalExp);

			globalTextField.setHeight(0 + height ); //XXX be carefull with the "2+ ..."
			globalTextField.setWidth(totalWidth);

			globalTextField.setX(0);
			if (type.equals(ColumnsGroupVariablesRegistrationManager.HEADER))
				globalTextField.setY(yOffset);
			globalTextField.setKey("global_legend_"+type);

			applyStyleToElement(globalCol.getStyle(), globalTextField);

			band.addElement(globalTextField);
		}
	}

	protected int getHeaderVariablesHeight(DJGroup columnsGroup) {
		Integer height;
		height = columnsGroup.getHeaderVariablesHeight()!=null
					? columnsGroup.getHeaderVariablesHeight()
					: DynamicReportOptions.DEFAULT_HEADER_VARIABLES_HEIGHT;
		return height.intValue();
	}

	protected int getFooterVariableHeight(DJGroup columnsGroup) {
		Integer height;
		height = columnsGroup.getFooterVariablesHeight()!=null
					? columnsGroup.getFooterVariablesHeight()
					: DynamicReportOptions.DEFAULT_FOOTER_VARIABLES_HEIGHT;
		return height.intValue();
	}

	/**
	 * If a variable has a DJValueFormatter, we must use it in the expression, otherwise, use plain $V{...}
	 * @param expression
	 * @param var
	 * @param col
	 * @param variableName
	 */
	protected void setTextAndClassToExpression(JRDesignExpression expression, DJGroupVariable var, AbstractColumn col, String variableName) {

		if (var.getValueFormatter() != null){
			expression.setText(var.getTextForValueFormatterExpression(variableName));
			expression.setValueClassName(var.getValueFormatter().getClassName());
		}
		else if (col.getTextFormatter() != null) {

			expression.setText("$V{" + variableName + "}");
			expression.setValueClassName(col.getVariableClassName(var.getOperation()));
		}
		else {
			expression.setText("$V{" + variableName + "}");
			expression.setValueClassName(col.getVariableClassName(var.getOperation()));
		}
	}

	protected DJGroupVariable findLeftMostColumn(List variables) {
		int mostLeftX = Integer.MAX_VALUE;
		DJGroupVariable mostLeftColumn =  null;
		for (Iterator iterator = variables.iterator(); iterator.hasNext();) {
			DJGroupVariable currentCol = (DJGroupVariable) iterator.next();
			if (currentCol.getColumnToApplyOperation().getPosX().intValue() <= mostLeftX) {
				mostLeftColumn = currentCol;
                mostLeftX = mostLeftColumn.getColumnToApplyOperation().getPosX().intValue();
            }
        }
		return mostLeftColumn;
	}

	protected DJGroupVariable findRightMostColumn(List variables) {
		int mostRightX = Integer.MIN_VALUE;
		DJGroupVariable mostRightColumn =  null;
		for (Iterator iterator = variables.iterator(); iterator.hasNext();) {
			DJGroupVariable currentCol = (DJGroupVariable) iterator.next();
			if (currentCol.getColumnToApplyOperation().getPosX().intValue() >= mostRightX) {
				mostRightColumn = currentCol;
                mostRightX = mostRightColumn.getColumnToApplyOperation().getPosX().intValue();
            }
        }
		return mostRightColumn;
	}

	protected void insertValueInHeader(JRDesignBand headerBand, DJGroup djgroup, int headerOffset) {
//		JRDesignTextField textField = generateTextFieldFromColumn(columnsGroup.getColumnToGroupBy(), columnsGroup.getHeaderHeight().intValue(), columnsGroup);
		int height = getReport().getOptions().getDetailHeight().intValue();

		if (!djgroup.getHeaderVariables().isEmpty())
			height = djgroup.getHeaderVariablesHeight()!=null
						? djgroup.getHeaderVariablesHeight().intValue()
						:getReport().getOptions().getDetailHeight().intValue();

		JRDesignTextField textField = generateTextFieldFromColumn(djgroup.getColumnToGroupBy(), height, djgroup);
		//fixes issue 2817859, textField has columnToGroupBy style
		//textField.setHorizontalAlignment(djgroup.getColumnToGroupBy().getStyle().getHorizontalAlign().getValue());
		textField.setStretchType(JRDesignElement.STRETCH_TYPE_NO_STRETCH); //XXX this is a patch for subreports, ensure it works well.
		textField.setY(textField.getY() + headerOffset);
		headerBand.addElement(textField);
	}

	protected int changeHeaderBandHeightForVariables(JRDesignBand headerBand, DJGroup columnsGroup) {
		int result = 0;
		if (!headerBand.getChildren().isEmpty()) {
			int acu = headerBand.getHeight();
			headerBand.setHeight(headerBand.getHeight() + columnsGroup.getHeaderHeight().intValue());
			result = acu;
		} else
			headerBand.setHeight(getReport().getOptions().getHeaderVariablesHeight().intValue());
		return result;
	}

	protected void generateHeaderBand() {
		log.debug("Generating main report header band.");
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
//		log.debug("transforming detail band text field...");
		DJGroup group = getDJGroup(column);
		if (group!=null&&!group.getLayout().isShowValueForEachRow()) {
			textField.setExpression(null); //this way, the textfield is not added to the band
		}
	}



}
