package ar.com.fdvs.dj.domain.chart.plot;

import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.chart.DJChart;
import net.sf.jasperreports.charts.design.JRDesignChartAxis;
import net.sf.jasperreports.charts.design.JRDesignMultiAxisPlot;
import net.sf.jasperreports.charts.type.AxisPositionEnum;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignGroup;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MultiAxisPlot extends AbstractPlot {

    private DJChart primaryChart;
    private List<DJChart> secondaryCharts = new ArrayList<DJChart>();

    public MultiAxisPlot setPrimaryChart(DJChart primaryChart) {
        this.primaryChart = primaryChart;
        return this;
    }

    public MultiAxisPlot addSecondaryChart(DJChart secondaryChart) {
        this.secondaryCharts.add(secondaryChart);
        return this;
    }

    @Override
    public void transform(JRChartPlot plot, DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars, int width) {
        super.transform(plot, design, name, group, parentGroup, vars, width);
        JRDesignChart chart = (JRDesignChart) plot.getChart();

        JRDesignMultiAxisPlot axisPlot = (JRDesignMultiAxisPlot) plot;
        JRDesignChartAxis primaryAxis = new JRDesignChartAxis(chart);

        String subChartName = name + "_sub";
        JRDesignChart subchart = primaryChart.transform(design, subChartName, group, parentGroup, vars, width);
        subchart.getPlot().setSeriesColors(plot.getSeriesColors());

        primaryAxis.setChart(subchart);
        primaryAxis.setPosition(AxisPositionEnum.LEFT_OR_TOP);
        axisPlot.addAxis(primaryAxis);

        int i = 0;
        for (DJChart secondaryChart : secondaryCharts) {
            JRDesignChartAxis secondaryAxis = new JRDesignChartAxis(chart);

            String subSecondaryChartName = name + "_sub_" + i;
            JRDesignChart subSecondaryChart = secondaryChart.transform(design, subSecondaryChartName, group, parentGroup, vars, width);
            setSecondaryChartSeriesColors(subSecondaryChart);

            secondaryAxis.setChart(subSecondaryChart);
            secondaryAxis.setPosition(AxisPositionEnum.RIGHT_OR_BOTTOM);
            axisPlot.addAxis(secondaryAxis);
            i++;
        }
    }

    private void setSecondaryChartSeriesColors(JRDesignChart subSecondaryChart) {
        ArrayList<Color> colors = new ArrayList<Color>(getSeriesColors());
        Collections.reverse(colors);
        subSecondaryChart.getPlot().setSeriesColors(transformColors(colors));
    }
}
