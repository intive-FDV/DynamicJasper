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

/**
 * Class that defines the report configuration.
 */
public class DynamicReportOptions extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public static final Integer DEFAULT_HEADER_HEIGHT 			= new Integer(30);
	public static final Integer DEFAULT_HEADER_VARIABLES_HEIGHT	= new Integer(20);
	public static final Integer DEFAULT_FOOTER_VARIABLES_HEIGHT	= new Integer(20);
	public static final Integer DEFAULT_DETAIL_HEIGHT 			= new Integer(15);
	
	public static final Integer DEFAULT_MARGIN_TOP 		= new Integer(20);
	public static final Integer DEFAULT_MARGIN_BOTTOM 	= new Integer(10);
	public static final Integer DEFAULT_MARGIN_LEFT 	= new Integer(30);
	public static final Integer DEFAULT_MARGIN_RIGTH 	= new Integer(10);
	
	private Integer headerHeight 			= DEFAULT_HEADER_HEIGHT;
	private Integer headerVariablesHeight 	= DEFAULT_HEADER_HEIGHT;
	private Integer footerVariablesHeight 	= DEFAULT_FOOTER_VARIABLES_HEIGHT;
	private Integer detailHeight 			= DEFAULT_DETAIL_HEIGHT;

	private Integer leftMargin 		= DEFAULT_MARGIN_LEFT;
	private Integer rightMargin 	= DEFAULT_MARGIN_RIGTH;
	private Integer topMargin 		= DEFAULT_MARGIN_TOP;
	private Integer bottomMargin	= DEFAULT_MARGIN_BOTTOM;

	private Integer columnsPerPage = new Integer(1);
	private Integer columnSpace = new Integer(0);

	private boolean useFullPageWidth = false;

	private Page page = Page.Page_A4_Portrait();

	private boolean printBackgroundOnOddRows = false;
	private Style oddRowBackgroundStyle = new StyleBuilder(false,"defaultOddRowStyle")
												.setBackgroundColor(new Color(200,200,200,128))
												.build();
	private Integer titleHeight =  DEFAULT_HEADER_HEIGHT;
	private boolean titleNewPage =  false;
	private boolean summaryNewPage =  false;
	private Integer subtitleHeight = DEFAULT_DETAIL_HEIGHT;
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
	 * Key: Byte (ImageBanner.ALIGN_RIGHT, ImageBanner.ALIGN_LEFT, ImageBanner.ALIGN_CENTER)<br/>
	 * value: ImageBanner
	 */
	private HashMap imageBanners = new HashMap();

    /**
     * Key: Byte (ImageBanner.ALIGN_RIGHT, ImageBanner.ALIGN_LEFT, ImageBanner.ALIGN_CENTER)<br/>
     * value: ImageBanner
     */
    private HashMap footerImageBanners = new HashMap();

	/**
	 * Key: Byte (ImageBanner.ALIGN_RIGHT, ImageBanner.ALIGN_LEFT, ImageBanner.ALIGN_CENTER)<br/>
	 * value: ImageBanner
	 */
	private HashMap firstPageImageBanners = new HashMap();

    /**
     * Key: Byte (ImageBanner.ALIGN_RIGHT, ImageBanner.ALIGN_LEFT, ImageBanner.ALIGN_CENTER)<br/>
     * value: ImageBanner
     */
	private HashMap firstPageFooterImageBanners = new HashMap();

    public HashMap getFirstPageFooterImageBanners() {
        return firstPageFooterImageBanners;
    }

    public HashMap getImageBanners() {
		return imageBanners;
	}

	public Integer getFooterVariablesHeight() {
		return footerVariablesHeight;
	}

	public void setFooterVariablesHeight(Integer footerHeight) {
		if (footerHeight == null)
			footerHeight = DEFAULT_FOOTER_VARIABLES_HEIGHT;
		this.footerVariablesHeight = footerHeight;
	}

	public Integer getHeaderHeight() {
		return headerHeight;
	}

	public void setHeaderHeight(Integer headerHeight) {
		if (headerHeight ==  null)
			headerHeight = DEFAULT_HEADER_HEIGHT;
		this.headerHeight = headerHeight;
	}

	public Integer getDetailHeight() {
		return detailHeight;
	}

	public void setDetailHeight(Integer detailHeight) {
		if (detailHeight == null)
			detailHeight = DEFAULT_DETAIL_HEIGHT;
		this.detailHeight = detailHeight;
	}

	public Integer getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(Integer leftMargin) {
		this.leftMargin = leftMargin;
	}

	public boolean isUseFullPageWidth() {
		return useFullPageWidth;
	}

	public void setUseFullPageWidth(boolean useFullPageWidth) {
		this.useFullPageWidth = useFullPageWidth;
	}

	public Integer getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(Integer bottomMargin) {
		this.bottomMargin = bottomMargin;
	}

	public Integer getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(Integer rightMargin) {
		this.rightMargin = rightMargin;
	}

	public Integer getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(Integer topMargin) {
		this.topMargin = topMargin;
	}

	public Integer getColumnsPerPage() {
		return columnsPerPage;
	}

	public void setColumnsPerPage(Integer columnsPerPage) {
		this.columnsPerPage = columnsPerPage;
	}

	public Integer getColumnSpace() {
		return columnSpace;
	}

	public void setColumnSpace(Integer columnSpace) {
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
		return page.getWidth() - leftMargin.intValue() - rightMargin.intValue();
	}

	/**
	 * Gives the printableWidth between the page margins that also considers the </br>
	 * number of columns per page and the space between them.</br>
	 * @return int
	 */
	public int getColumnWidth(){
		int usableWidth = page.getWidth()-getLeftMargin().intValue()-getRightMargin().intValue()-((getColumnsPerPage().intValue()-1)*getColumnSpace().intValue());
		return usableWidth / getColumnsPerPage().intValue();
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

	public Integer getTitleHeight() {
		return this.titleHeight;
	}

	public Integer getSubtitleHeight() {
		return this.subtitleHeight;
	}

	public void setSubtitleHeight(Integer subtitleHeight) {
		this.subtitleHeight = subtitleHeight;
	}

	public void setTitleHeight(Integer titleHeight) {
		this.titleHeight = titleHeight;
	}

	public HashMap getFirstPageImageBanners() {
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

	public Integer getHeaderVariablesHeight() {
		return headerVariablesHeight;
	}

	public void setHeaderVariablesHeight(Integer headerVariablesHeight) {
		if (headerVariablesHeight==null)
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

    public HashMap getFooterImageBanners() {
        return footerImageBanners;
    }
}
