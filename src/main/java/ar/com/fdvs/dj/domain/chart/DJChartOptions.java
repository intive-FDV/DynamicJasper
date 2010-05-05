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

package ar.com.fdvs.dj.domain.chart;

import java.awt.Color;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

public class DJChartOptions extends DJBaseElement {
	
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public static final byte POSITION_FOOTER = 1;
	public static final byte POSITION_HEADER = 2;

	public static final byte EDGE_TOP = JRChart.EDGE_TOP;
	public static final byte EDGE_BOTTOM = JRChart.EDGE_BOTTOM;
	public static final byte EDGE_LEFT = JRChart.EDGE_LEFT;
	public static final byte EDGE_RIGHT = JRChart.EDGE_RIGHT;
	
	/**
	 * Constant useful for specifying solid line style.
	 */
	public static final byte LINE_STYLE_SOLID = JRPen.LINE_STYLE_SOLID;

	/**
	 * Constant useful for specifying dashed line style.
	 */
	public static final byte LINE_STYLE_DASHED = JRPen.LINE_STYLE_DASHED;

	/**
	 * Constant useful for specifying dotted line style.
	 */
	public static final byte LINE_STYLE_DOTTED = JRPen.LINE_STYLE_DOTTED;

	/**
	 * Constant useful for specifying double line style.
	 */
	public static final byte LINE_STYLE_DOUBLE = JRPen.LINE_STYLE_DOUBLE;
	
	private Color backColor;
	private int height;
	private int width;
	private boolean centered;
	private byte position;
	private int y;
	private int x;	
	
	private Boolean showLegend = null;
	private Color titleColor = null;
	private Color subtitleColor = null;
	private Color legendColor = null;
	private Color legendBackgroundColor = null;
	private String theme = null;
	private Font titleFont = null;
	private Font subtitleFont = null;
	private Font legendFont = null;
	private Byte legendPosition = null;
	private Byte titlePosition = null;
	private StringExpression titleExpression = null;
	private StringExpression subtitleExpression = null;
	
	private Byte lineStyle = null;
	private Float lineWidth = null;
	private Color lineColor = null;
	private Integer padding = null;
	
	private String customizerClass = null;
		
	public DJChartOptions() {
		this.showLegend = new Boolean(true);
		this.backColor = Color.WHITE;
		this.height = 200;
		this.width = 200;
		this.centered = true;
		this.position = POSITION_FOOTER;
		this.x = 0;
		this.y = 0;		
		this.padding = new Integer(10);
	}
		
	/**
	 * Returns the background color.
	 *
	 * @return	the background color
	 **/
	public Color getBackColor() {
		return backColor;
	}

	/**
	 * Sets the background color.
	 *
	 * @param backColor the background color
	 **/
	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	/**
	 * Returns the chart height.
	 *
	 * @return	the chart height
	 **/
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the chart height.
	 *
	 * @param height the chart height
	 **/
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns the chart width.
	 *
	 * @return	the chart width
	 **/
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the chart width.
	 *
	 * @param width the chart width
	 **/
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Returns the true if the chart is centered.
	 *
	 * @return	the true if the chart is centered
	 **/
	public boolean isCentered() {
		return centered;
	}

	/**
	 * Sets the centered.
	 *
	 * @param centered the centered
	 **/
	public void setCentered(boolean centered) {
		this.centered = centered;
	}

	/**
	 * Returns the position (DJChartOptions.POSITION_FOOTER or DJChartOptions.POSITION_HEADER).
	 *
	 * @return	the position
	 **/
	public byte getPosition() {
		return position;
	}

	/**
	 * Sets the position (DJChartOptions.POSITION_FOOTER or DJChartOptions.POSITION_HEADER).
	 *
	 * @param position the position
	 **/
	public void setPosition(byte position) {
		this.position = position;
	}

	/**
	 * Returns the y position.
	 *
	 * @return	the y position
	 **/
	public int getY() {
		return y;
	}

	/**
	 * Sets the y position.
	 *
	 * @param y the y position
	 **/
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the x position.
	 *
	 * @return	the x position
	 **/
	public int getX() {
		return x;
	}

	/**
	 * Sets the x position.
	 *
	 * @param y the x position
	 **/
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the legend visibility.
	 *
	 * @return	the legend visibility
	 **/
	public Boolean getShowLegend() {
		return showLegend;
	}

	/**
	 * Sets the legend visibility.
	 *
	 * @param showLegend the legend visibility
	 **/
	public void setShowLegend(Boolean showLegend) {
		this.showLegend = showLegend;
	}

	/**
	 * Returns the title color.
	 *
	 * @return	the title color
	 **/
	public Color getTitleColor() {
		return titleColor;
	}

	/**
	 * Sets the title color.
	 *
	 * @param titleColor the title color
	 **/
	public void setTitleColor(Color titleColor) {
		this.titleColor = titleColor;
	}

	/**
	 * Returns the subtitle color.
	 *
	 * @return	the subtitle color
	 **/
	public Color getSubtitleColor() {
		return subtitleColor;
	}

	/**
	 * Sets the subtitle color.
	 *
	 * @param subtitleColor the subtitle color
	 **/
	public void setSubtitleColor(Color subtitleColor) {
		this.subtitleColor = subtitleColor;
	}

	/**
	 * Returns the legend color.
	 *
	 * @return	the legend color
	 **/
	public Color getLegendColor() {
		return legendColor;
	}

	/**
	 * Sets the legend color.
	 *
	 * @param legendColor the legend color
	 **/
	public void setLegendColor(Color legendColor) {
		this.legendColor = legendColor;
	}

	/**
	 * Returns the legend background color.
	 *
	 * @return	the legend background color
	 **/
	public Color getLegendBackgroundColor() {
		return legendBackgroundColor;
	}

	/**
	 * Sets the legend background color.
	 *
	 * @param legendBackgroundColor the legend background color
	 **/
	public void setLegendBackgroundColor(Color legendBackgroundColor) {
		this.legendBackgroundColor = legendBackgroundColor;
	}

	/**
	 * Returns the theme.
	 * Chart themes support to allow changing the overall appearance of charts generated with the build-in chart element
	 *
	 * @return	the theme
	 **/
	public String getTheme() {
		return theme;
	}

	/**
	 * Sets the theme.
	 * Chart themes support to allow changing the overall appearance of charts generated with the build-in chart element
	 *
	 * @param theme the theme
	 **/
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Returns the title font.
	 *
	 * @return	the title font
	 **/
	public Font getTitleFont() {
		return titleFont;
	}

	/**
	 * Sets the title font.
	 *
	 * @param titleFont the title font
	 **/
	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	/**
	 * Returns the subtitle font.
	 *
	 * @return	the subtitle font
	 **/
	public Font getSubtitleFont() {
		return subtitleFont;
	}

	/**
	 * Sets the subtitle font.
	 *
	 * @param subtitleFont the subtitle font
	 **/
	public void setSubtitleFont(Font subtitleFont) {
		this.subtitleFont = subtitleFont;
	}

	/**
	 * Returns the legend font.
	 *
	 * @return	the legend font
	 **/
	public Font getLegendFont() {
		return legendFont;
	}

	/**
	 * Sets the legend font.
	 *
	 * @param legendFont the legend font
	 **/
	public void setLegendFont(Font legendFont) {
		this.legendFont = legendFont;
	}

	/**
	 * Returns the legend position (EDGE_TOP, EDGE_BOTTOM, EDGE_LEFT, EDGE_RIGHT).
	 *
	 * @return	the legend position
	 **/
	public Byte getLegendPosition() {
		return legendPosition;
	}

	/**
	 * Sets the legend position (EDGE_TOP, EDGE_BOTTOM, EDGE_LEFT, EDGE_RIGHT).
	 *
	 * @param legendPosition the legend position
	 **/
	public void setLegendPosition(byte legendPosition) {
		this.legendPosition = new Byte(legendPosition);
	}

	/**
	 * Returns the title position (EDGE_TOP, EDGE_BOTTOM, EDGE_LEFT, EDGE_RIGHT).
	 *
	 * @return	the title position
	 **/
	public Byte getTitlePosition() {
		return titlePosition;
	}

	/**
	 * Sets the title position (EDGE_TOP, EDGE_BOTTOM, EDGE_LEFT, EDGE_RIGHT).
	 *
	 * @param titlePosition the title position
	 **/
	public void setTitlePosition(byte titlePosition) {
		this.titlePosition = new Byte(titlePosition);
	}

	/**
	 * Returns the title expression.
	 *
	 * @return	the title expression
	 **/
	public StringExpression getTitleExpression() {
		return titleExpression;
	}

	/**
	 * Sets the title expression.
	 *
	 * @param titleExpression the title expression
	 **/
	public void setTitleExpression(StringExpression titleExpression) {
		this.titleExpression = titleExpression;
	}

	/**
	 * Returns the subtitle expression.
	 *
	 * @return	the subtitle expression
	 **/
	public StringExpression getSubtitleExpression() {
		return subtitleExpression;
	}

	/**
	 * Sets the subtitle expression.
	 *
	 * @param subtitleExpression the subtitle expression
	 **/
	public void setSubtitleExpression(StringExpression subtitleExpression) {
		this.subtitleExpression = subtitleExpression;
	}

	/**
	 * Gets the line style (LINE_STYLE_SOLID, LINE_STYLE_DASHED, LINE_STYLE_DOTTED, LINE_STYLE_DOUBLE).
	 * 
	 * @return one of the line style constants in this class
	 */
	public Byte getLineStyle() {
		return lineStyle;
	}

	/**
	 * Sets the line style (LINE_STYLE_SOLID, LINE_STYLE_DASHED, LINE_STYLE_DOTTED, LINE_STYLE_DOUBLE).
	 * 
	 * @param lineStyle one of the line style constants in this class
	 */
	public void setLineStyle(byte lineStyle) {
		this.lineStyle = new Byte(lineStyle);
	}

	/**
	 * Returns the line width.
	 * 
	 * @return the line width
	 */
	public Float getLineWidth() {
		return lineWidth;
	}

	/**
	 * Sets the line width.
	 *
	 * @param lineWidth the line width
	 **/
	public void setLineWidth(Float lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * Returns the line color.
	 *
	 * @return	the line color
	 **/
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Sets the line color.
	 *
	 * @param lineColor the line color
	 **/
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * Returns the padding.
	 *
	 * @return	the padding
	 **/
	public Integer getPadding() {
		return padding;
	}

	/**
	 * Sets the padding.
	 *
	 * @param padding the padding
	 **/
	public void setPadding(Integer padding) {
		this.padding = padding;
	}

	/**
	 * Sets a user specified chart customizer class name.
	 * @see net.sf.jasperreports.engine.JRChartCustomizer
 	 */
	public void setCustomizerClass(String customizerClass) {
		this.customizerClass = customizerClass;
	}

	/**
	 * Returns a user specified chart customizer class name.
	 * @see net.sf.jasperreports.engine.JRChartCustomizer
	 *
	 * @return	user specified chart customizer class name
	 **/
	public String getCustomizerClass() {
		return customizerClass;
	}
	
	public void transform(DynamicJasperDesign design, String name, JRDesignChart chart, int width) {

		// size
		if (centered)
			chart.setWidth(width);
		else
			chart.setWidth(this.width);

		chart.setHeight(height);

		// position
		chart.setX(x);
		chart.setY(y);

		// options
		if (showLegend != null)
			chart.setShowLegend(showLegend);
		if (backColor != null)
			chart.setBackcolor(backColor);
		if (titleColor != null)
			chart.setTitleColor(titleColor);
		if (subtitleColor != null)
			chart.setSubtitleColor(subtitleColor);
		if (legendColor != null)
			chart.setLegendColor(legendColor);
		if (legendBackgroundColor != null)
			chart.setLegendBackgroundColor(legendBackgroundColor);
		if (theme != null)
			chart.setTheme(theme);
		if (titleFont != null)
			chart.setTitleFont(titleFont.transform());
		if (subtitleFont != null)
			chart.setSubtitleFont(subtitleFont.transform());
		if (legendFont != null)
			chart.setLegendFont(legendFont.transform());
		if (legendPosition != null)
			chart.setLegendPosition(legendPosition);
		if (titlePosition != null)
			chart.setTitlePosition(titlePosition);
		
		if (padding != null)
			chart.getLineBox().setPadding(padding);
		
		if (lineStyle != null)
			chart.getLineBox().getPen().setLineStyle(lineStyle);
		if (lineWidth != null)
			chart.getLineBox().getPen().setLineWidth(lineWidth);
		if (lineColor != null)
			chart.getLineBox().getPen().setLineColor(lineColor);		
		
		if (titleExpression != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "title_" + name, titleExpression);
			chart.setTitleExpression(exp);
		}
		
		if (subtitleExpression != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "subtitle_" + name, subtitleExpression);
			chart.setSubtitleExpression(exp);
		}
		
		if (customizerClass != null)
			chart.setCustomizerClass(customizerClass);
	}
}
