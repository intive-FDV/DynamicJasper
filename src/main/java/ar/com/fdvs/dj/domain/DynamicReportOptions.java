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

package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.Entity;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines the report configuration.
 */
public class DynamicReportOptions extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public static final int UNSET_VALUE 			= -1;
	public static final int DEFAULT_HEADER_HEIGHT 			= 30;
	public static final int DEFAULT_HEADER_VARIABLES_HEIGHT	= 20;
	public static final int DEFAULT_FOOTER_VARIABLES_HEIGHT	= 20;
	public static final int DEFAULT_DETAIL_HEIGHT 			= 15;
	
	public static final int DEFAULT_MARGIN_TOP 		= 20;
	public static final int DEFAULT_MARGIN_BOTTOM 	= 10;
	public static final int DEFAULT_MARGIN_LEFT 	= 30;
	public static final int DEFAULT_MARGIN_RIGTH 	= 10;
	
	private int headerHeight 			= DEFAULT_HEADER_HEIGHT;
	private int headerVariablesHeight 	= DEFAULT_HEADER_HEIGHT;
	private int footerVariablesHeight 	= DEFAULT_FOOTER_VARIABLES_HEIGHT;
	private int detailHeight 			= DEFAULT_DETAIL_HEIGHT;

	private int leftMargin 		= DEFAULT_MARGIN_LEFT;
	private int rightMargin 	= DEFAULT_MARGIN_RIGTH;
	private int topMargin 		= DEFAULT_MARGIN_TOP;
	private int bottomMargin	= DEFAULT_MARGIN_BOTTOM;

	private int columnsPerPage = 1;
	private int columnSpace = 0;

	private boolean useFullPageWidth = false;

	private Page page = Page.Page_A4_Portrait();

	private boolean printBackgroundOnOddRows = false;
	private Style oddRowBackgroundStyle = new StyleBuilder(false,"defaultOddRowStyle")
												.setBackgroundColor(new Color(200,200,200,128))
												.build();
	private int titleHeight =  DEFAULT_HEADER_HEIGHT;
	private boolean titleNewPage =  false;
	private boolean summaryNewPage =  false;
	private int subtitleHeight = DEFAULT_DETAIL_HEIGHT;
	private boolean showDetailBand = true;

	/**
	 * When false, no main column names (usefull for excel)
	 */
	private boolean printColumnNames =  true;

	/**
	 * When true, no page break at all
	 */
	private boolean ignorePagination =  false;

	private Style defaultHeaderStyle = new Style("defaultHeaderStyle");
	private Style defaultDetailStyle = new Style("defaultDetailStyle");
	private Style defaultFooterStyle = new Style("defaultFooterStyle");
	private Style defaultGroupHeaderStyle = new Style("defaultGroupHeaderStyle");
	private Style defaultGroupFooterStyle = new Style("defaultGroupFooterStyle");

	/**
	 * Key: Byte (ImageBanner.Alignment.Right, ImageBanner.Alignment.Left, ImageBanner.ALIGN_CENTER)<br/>
	 * value: ImageBanner
	 */
	private final Map<ImageBanner.Alignment, ImageBanner> imageBanners = new HashMap<ImageBanner.Alignment, ImageBanner>();

    /**
     * Key: Byte (ImageBanner.Alignment.Right, ImageBanner.Alignment.Left, ImageBanner.ALIGN_CENTER)<br/>
     * value: ImageBanner
     */
    private final Map<ImageBanner.Alignment, ImageBanner> footerImageBanners = new HashMap<ImageBanner.Alignment, ImageBanner>();

	/**
	 * Key: Byte (ImageBanner.Alignment.Right, ImageBanner.Alignment.Left, ImageBanner.ALIGN_CENTER)<br/>
	 * value: ImageBanner
	 */
	private final Map<ImageBanner.Alignment, ImageBanner> firstPageImageBanners = new HashMap<ImageBanner.Alignment, ImageBanner>();

    /**
     * Key: Byte (ImageBanner.Alignment.Right, ImageBanner.Alignment.Left, ImageBanner.ALIGN_CENTER)<br/>
     * value: ImageBanner
     */
	private final Map<ImageBanner.Alignment, ImageBanner> firstPageFooterImageBanners = new HashMap<ImageBanner.Alignment, ImageBanner>();

    public Map<ImageBanner.Alignment, ImageBanner> getFirstPageFooterImageBanners() {
        return firstPageFooterImageBanners;
    }

    public Map<ImageBanner.Alignment, ImageBanner> getImageBanners() {
		return imageBanners;
	}

	public int getFooterVariablesHeight() {
		return footerVariablesHeight;
	}

	public void setFooterVariablesHeight(int footerHeight) {
		if (footerHeight < 0)
			footerHeight = DEFAULT_FOOTER_VARIABLES_HEIGHT;
		this.footerVariablesHeight = footerHeight;
	}

	public int getHeaderHeight() {
		return headerHeight;
	}

	public void setHeaderHeight(int headerHeight) {
		if (headerHeight <  0)
			headerHeight = DEFAULT_HEADER_HEIGHT;
		this.headerHeight = headerHeight;
	}

	public int getDetailHeight() {
		return detailHeight;
	}

	public void setDetailHeight(int detailHeight) {
		if (detailHeight < 0)
			detailHeight = DEFAULT_DETAIL_HEIGHT;
		this.detailHeight = detailHeight;
	}

	public int getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}

	public boolean isUseFullPageWidth() {
		return useFullPageWidth;
	}

	public void setUseFullPageWidth(boolean useFullPageWidth) {
		this.useFullPageWidth = useFullPageWidth;
	}

	public int getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
	}

	public int getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(int rightMargin) {
		this.rightMargin = rightMargin;
	}

	public int getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}

	public int getColumnsPerPage() {
		return columnsPerPage;
	}

	public void setColumnsPerPage(int columnsPerPage) {
		this.columnsPerPage = columnsPerPage;
	}

	public int getColumnSpace() {
		return columnSpace;
	}

	public void setColumnSpace(int columnSpace) {
		this.columnSpace = columnSpace;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * Gives the printableWidth between the page margins. </br>
	 * pageTotalWidth - leftMargin - rightMargin
	 * @return int
	 */
	public int getPrintableWidth(){
		return page.getWidth() - leftMargin - rightMargin;
	}

	/**
	 * Gives the printableWidth between the page margins that also considers the </br>
	 * number of columns per page and the space between them.</br>
	 * @return int
	 */
	public int getColumnWidth(){
		int usableWidth = page.getWidth()-getLeftMargin()-getRightMargin()-((getColumnsPerPage()-1)*getColumnSpace());
		return usableWidth / getColumnsPerPage();
	}

	public Style getOddRowBackgroundStyle() {
		return oddRowBackgroundStyle;
	}

	public void setOddRowBackgroundStyle(Style oddRowBackgroundStyle) {
		this.oddRowBackgroundStyle = oddRowBackgroundStyle;
	}

	public boolean isPrintBackgroundOnOddRows() {
		return printBackgroundOnOddRows;
	}

	public void setPrintBackgroundOnOddRows(boolean printBackgroundOnOddRows) {
		this.printBackgroundOnOddRows = printBackgroundOnOddRows;
	}

	public int getTitleHeight() {
		return this.titleHeight;
	}

	public int getSubtitleHeight() {
		return this.subtitleHeight;
	}

	public void setSubtitleHeight(int subtitleHeight) {
		this.subtitleHeight = subtitleHeight;
	}

	public void setTitleHeight(int titleHeight) {
		this.titleHeight = titleHeight;
	}

	public Map<ImageBanner.Alignment, ImageBanner> getFirstPageImageBanners() {
		return firstPageImageBanners;
	}

	public Style getDefaultDetailStyle() {
		return defaultDetailStyle;
	}

	/**
	 * Should not be null
	 * @param defaultDetailStyle
	 */
	public void setDefaultDetailStyle(Style defaultDetailStyle) {
		this.defaultDetailStyle = defaultDetailStyle;
	}

	public Style getDefaultFooterStyle() {
		return defaultFooterStyle;
	}

	/**
	 * Should not be null
	 * @param defaultFooterStyle
	 */
	public void setDefaultFooterStyle(Style defaultFooterStyle) {
		this.defaultFooterStyle = defaultFooterStyle;
	}

	public Style getDefaultHeaderStyle() {
		return defaultHeaderStyle;
	}

	/***
	 * Should not be null
	 * @param defaultHeaderStyle
	 */
	public void setDefaultHeaderStyle(Style defaultHeaderStyle) {
		this.defaultHeaderStyle = defaultHeaderStyle;
	}

	public Style getDefaultGroupHeaderStyle() {
		return defaultGroupHeaderStyle;
	}

	/**
	 * Should not be null
	 * @param defaultGroupHeaderStyle
	 */
	public void setDefaultGroupHeaderStyle(Style defaultGroupHeaderStyle) {
		this.defaultGroupHeaderStyle = defaultGroupHeaderStyle;
	}

	public Style getDefaultGroupFooterStyle() {
		return defaultGroupFooterStyle;
	}

	/**
	 * Should not be null
	 * @param defaultGroupFooterStyle
	 */
	public void setDefaultGroupFooterStyle(Style defaultGroupFooterStyle) {
		this.defaultGroupFooterStyle = defaultGroupFooterStyle;
	}

	public boolean isTitleNewPage() {
		return titleNewPage;
	}

	public void setTitleNewPage(boolean titleNewPage) {
		this.titleNewPage = titleNewPage;
	}

	public boolean isPrintColumnNames() {
		return printColumnNames;
	}

	public void setPrintColumnNames(boolean printColumnNames) {
		this.printColumnNames = printColumnNames;
	}

	public boolean isIgnorePagination() {
		return ignorePagination;
	}

	public void setIgnorePagination(boolean ignorePagination) {
		this.ignorePagination = ignorePagination;
	}

	public int getHeaderVariablesHeight() {
		return headerVariablesHeight;
	}

	public void setHeaderVariablesHeight(int headerVariablesHeight) {
		if (headerVariablesHeight < 0)
			headerVariablesHeight = DEFAULT_HEADER_VARIABLES_HEIGHT;
		this.headerVariablesHeight = headerVariablesHeight;
	}

	public boolean isShowDetailBand() {
		return showDetailBand;
	}

	public void setShowDetailBand(boolean showDetailBand) {
		this.showDetailBand = showDetailBand;
	}

	/**
	 * When TRUE, the summary section will start in a new page
	 * @return
	 */
	public boolean isSummaryNewPage() {
		return summaryNewPage;
	}

	public void setSummaryNewPage(boolean summaryNewPage) {
		this.summaryNewPage = summaryNewPage;
	}

    public Map<ImageBanner.Alignment, ImageBanner> getFooterImageBanners() {
        return footerImageBanners;
    }
}
