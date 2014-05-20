package ar.com.fdvs.dj.test.domain.chart.builder;

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import ar.com.fdvs.dj.domain.chart.builder.DJBarChartBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJLineChartBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJMultiAxisChartBuilder;
import ar.com.fdvs.dj.domain.chart.plot.DJAxisFormat;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

import java.awt.*;
import java.util.ArrayList;

public class MultiAxisChartBuilderTest extends BaseDjReportTest {

    private DynamicReportBuilder drb;
    static ArrayList<Color> colors = new ArrayList<Color>() {{
        add(Color.GREEN);
        add(Color.YELLOW);
    }};


    protected void setUp() throws Exception {
        drb = new DynamicReportBuilder();

        Style headerStyle = new Style();
        headerStyle.setFont(Font.VERDANA_MEDIUM_BOLD);
        headerStyle.setBorderBottom(Border.PEN_2_POINT());
        headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        headerStyle.setBackgroundColor(Color.DARK_GRAY);
        headerStyle.setTextColor(Color.WHITE);
        headerStyle.setTransparency(ar.com.fdvs.dj.domain.constants.Transparency.OPAQUE);
        drb.setDefaultStyles(null, null, headerStyle, null);

        AbstractColumn columnCode = ColumnBuilder.getNew()
                .setColumnProperty("id", Long.class.getName()).setTitle("ID")
                .setWidth(new Integer(40)).build();

        AbstractColumn columnQuantity = ColumnBuilder.getNew()
                .setColumnProperty("quantity", Long.class.getName()).setTitle(
                        "Quantity").setWidth(new Integer(80)).build();

        AbstractColumn columnAmount = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName()).setTitle(
                        "Amount").setWidth(new Integer(90)).build();

        drb.addField(new ColumnProperty("id", Long.class));
        drb.addField(new ColumnProperty("quantity", Long.class));
        drb.addField(new ColumnProperty("amount", Float.class));

        drb.setUseFullPageWidth(true);

        DJAxisFormat categoryAxisFormat = new DJAxisFormat("x");
        categoryAxisFormat.setLabelFont(Font.ARIAL_SMALL);
        categoryAxisFormat.setLabelColor(Color.DARK_GRAY);
        categoryAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
        categoryAxisFormat.setTickLabelColor(Color.DARK_GRAY);
        categoryAxisFormat.setTickLabelMask("#,###.#");
        categoryAxisFormat.setLineColor(Color.DARK_GRAY);

        DJAxisFormat primaryAxisFormat = new DJAxisFormat("primary");
        primaryAxisFormat.setLabelFont(Font.ARIAL_SMALL);
        primaryAxisFormat.setLabelColor(Color.DARK_GRAY);
        primaryAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
        primaryAxisFormat.setTickLabelColor(Color.DARK_GRAY);
        primaryAxisFormat.setTickLabelMask("#,##0.0");
        primaryAxisFormat.setLineColor(Color.DARK_GRAY);

        DJAxisFormat secondaryAxisFormat = new DJAxisFormat("secondary");
        secondaryAxisFormat.setLabelFont(Font.ARIAL_SMALL);
        secondaryAxisFormat.setLabelColor(Color.DARK_GRAY);
        secondaryAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
        secondaryAxisFormat.setTickLabelColor(Color.DARK_GRAY);
        secondaryAxisFormat.setTickLabelMask("#,##0.0");
        secondaryAxisFormat.setLineColor(Color.DARK_GRAY);

        DJChart primaryChart = new DJLineChartBuilder()
                //dataset
                .setCategory((PropertyColumn) columnCode)
                .addSerie(columnQuantity)
                .addSerie(columnAmount)
                        //plot
                .setShowShapes(false)
                .setShowLines(true)
                .setCategoryAxisFormat(categoryAxisFormat)
                .setValueAxisFormat(primaryAxisFormat)
                .build();

        DJChart secondaryChart = new DJBarChartBuilder()
                //chart
                .setCustomizerClass(BarChartCustomizer.class.getName())
                        //dataset
                .setCategory((PropertyColumn) columnCode)
                .addSerie(columnQuantity)
                .addSerie(columnAmount)
                        //plot
                .setShowTickMarks(true)
                .setShowTickLabels(true)
                .setShowLabels(false)
                .setCategoryAxisFormat(categoryAxisFormat)
                .setValueAxisFormat(secondaryAxisFormat)
                .build();


        DJChart multiAxisChart = new DJMultiAxisChartBuilder()
                //chart
                .setX(20)
                .setY(10)
                .setWidth(500)
                .setHeight(250)
                .setCentered(false)
                .setBackColor(Color.LIGHT_GRAY)
                .setShowLegend(true)
                .setPosition(DJChartOptions.POSITION_FOOTER)
                .setTitle("MultiAxis Chart")
                .setTitleColor(Color.DARK_GRAY)
                .setTitleFont(Font.ARIAL_BIG_BOLD)
                .setSubtitle("Line & Bar")
                .setSubtitleColor(Color.DARK_GRAY)
                .setSubtitleFont(Font.COURIER_NEW_BIG_BOLD)
                .setTitlePosition(DJChartOptions.EDGE_TOP)
                .setLineStyle(DJChartOptions.LINE_STYLE_DOTTED)
                .setLineWidth(1)
                .setLineColor(Color.DARK_GRAY)
                .setPadding(5)
                .setSeriesColors(colors)
                .setPrimaryDataset(secondaryChart.getDataset())
                .setPrimaryChart(primaryChart)
                .addSecondaryChart(secondaryChart)
                .build();

        drb.addChart(multiAxisChart);
    }

    public DynamicReport buildReport() throws Exception {
        return drb.build();
    }

    public static void main(String[] args) throws Exception {
        MultiAxisChartBuilderTest test = new MultiAxisChartBuilderTest();
        test.setUp();
        test.testReport();
        JasperViewer.viewReport(test.jp);
    }

    public static class BarChartCustomizer extends JRAbstractChartCustomizer {

        @Override
        public void customize(JFreeChart chart, JRChart jasperChart) {
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            CategoryDataset dataset = categoryPlot.getDataset();

            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();

            //Spaces between bars
            renderer.setItemMargin(0);

            int numSeries = dataset.getRowCount();
            for (int i = 0; i < numSeries; i++) {
                renderer.setSeriesPaint(i, colors.get(i));
                renderer.setSeriesFillPaint(i, colors.get(i));
            }

        }
    }

}
