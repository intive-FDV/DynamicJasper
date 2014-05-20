package ar.com.fdvs.dj.domain.chart.dataset;

import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;

import java.util.List;
import java.util.Map;

public class MultiAxisDataset extends AbstractDataset {

    private AbstractDataset primaryDataset;

    public void setPrimaryDataset(AbstractDataset primaryDataset) {
        this.primaryDataset = primaryDataset;
    }

    @Override
    public PropertyColumn getColumnsGroup() {
        return primaryDataset == null ? null : primaryDataset.getColumnsGroup();
    }

    @Override
    public List getColumns() {
        return primaryDataset == null ? null : primaryDataset.getColumns();
    }

    @Override
    public JRDesignChartDataset transform(DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars) {
        return primaryDataset.transform(design, name, group, parentGroup, vars);
    }

    @Override
    public void addSerie(AbstractColumn column) {
        primaryDataset.addSerie(column);
    }
}
