package ar.com.fdvs.dj.domain.chart.builder;

import ar.com.fdvs.dj.domain.DJHyperLink;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.dataset.AbstractDataset;
import ar.com.fdvs.dj.domain.chart.dataset.MultiAxisDataset;
import ar.com.fdvs.dj.domain.chart.plot.MultiAxisPlot;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.hyperlink.LiteralExpression;

import java.awt.*;
import java.util.List;

public class DJMultiAxisChartBuilder extends AbstractChartBuilder<DJMultiAxisChartBuilder> {

    @Override
    public DJMultiAxisChartBuilder setOperation(byte operation) {
        this.chart.setOperation(operation);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setLink(DJHyperLink link) {
        this.chart.setLink(link);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setBackColor(Color backColor) {
        this.chart.getOptions().setBackColor(backColor);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setHeight(int height) {
        this.chart.getOptions().setHeight(height);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setWidth(int width) {
        this.chart.getOptions().setWidth(width);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setCentered(boolean centered) {
        this.chart.getOptions().setCentered(centered);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setPosition(byte position) {
        this.chart.getOptions().setPosition(position);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setY(int y) {
        this.chart.getOptions().setY(y);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setX(int x) {
        this.chart.getOptions().setX(x);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setShowLegend(boolean showLegend) {
        this.chart.getOptions().setShowLegend(showLegend);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setTitleColor(Color titleColor) {
        this.chart.getOptions().setTitleColor(titleColor);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setSubtitleColor(Color subtitleColor) {
        this.chart.getOptions().setSubtitleColor(subtitleColor);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setLegendColor(Color legendColor) {
        this.chart.getOptions().setLegendColor(legendColor);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setLegendBackgroundColor(Color legendBackgroundColor) {
        this.chart.getOptions().setLegendBackgroundColor(legendBackgroundColor);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setTheme(String theme) {
        this.chart.getOptions().setTheme(theme);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setTitleFont(Font titleFont) {
        this.chart.getOptions().setTitleFont(titleFont);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setSubtitleFont(Font subtitleFont) {
        this.chart.getOptions().setSubtitleFont(subtitleFont);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setLegendFont(Font legendFont) {
        this.chart.getOptions().setLegendFont(legendFont);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setLegendPosition(byte legendPosition) {
        this.chart.getOptions().setLegendPosition(legendPosition);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setTitlePosition(byte titlePosition) {
        this.chart.getOptions().setTitlePosition(titlePosition);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setTitle(String title) {
        this.chart.getOptions().setTitleExpression(new LiteralExpression(title));
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setTitle(StringExpression titleExpression) {
        this.chart.getOptions().setTitleExpression(titleExpression);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setSubtitle(String subtitle) {
        this.chart.getOptions().setSubtitleExpression(new LiteralExpression(subtitle));
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setSubtitle(StringExpression subtitleExpression) {
        this.chart.getOptions().setSubtitleExpression(subtitleExpression);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setLineStyle(byte lineStyle) {
        this.chart.getOptions().setLineStyle(lineStyle);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setLineWidth(float lineWidth) {
        this.chart.getOptions().setLineWidth(lineWidth);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setLineColor(Color lineColor) {
        this.chart.getOptions().setLineColor(lineColor);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setPadding(int padding) {
        this.chart.getOptions().setPadding(padding);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setCustomizerClass(String customizerClass) {
        this.chart.getOptions().setCustomizerClass(customizerClass);
        return this;
    }

    @Override
    protected DJMultiAxisChartBuilder setCategory(PropertyColumn column) {
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder addSerie(AbstractColumn column) {
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder addSeriesColor(Color color) {
        this.chart.getPlot().addSeriesColor(color);
        return this;
    }

    @Override
    public DJMultiAxisChartBuilder setSeriesColors(List seriesColors) {
        this.chart.getPlot().setSeriesColors(seriesColors);
        return this;
    }

    @Override
    protected MultiAxisDataset getDataset() {
        return (MultiAxisDataset) chart.getDataset();
    }

    public DJMultiAxisChartBuilder setPrimaryDataset(AbstractDataset primaryDataset) {
        MultiAxisDataset dataset = (MultiAxisDataset) this.chart.getDataset();
        dataset.setPrimaryDataset(primaryDataset);
        return this;
    }

    public DJMultiAxisChartBuilder setPrimaryChart(DJChart primaryChart) {
        MultiAxisPlot plot = (MultiAxisPlot) this.chart.getPlot();
        plot.setPrimaryChart(primaryChart);
        return this;
    }

    public DJMultiAxisChartBuilder addSecondaryChart(DJChart secondaryChart) {
        MultiAxisPlot plot = (MultiAxisPlot) this.chart.getPlot();
        plot.addSecondaryChart(secondaryChart);
        return this;
    }

    @Override
    protected MultiAxisPlot getPlot() {
        return (MultiAxisPlot) chart.getPlot();
    }

    @Override
    protected byte getChartType() {
        return DJChart.MULTI_AXIS_CHART;
    }

}
